from pathlib import Path
from uuid import UUID

import httpx
from fastapi import APIRouter, BackgroundTasks, Depends, File, Form, HTTPException, UploadFile, Request
from fastapi.responses import FileResponse
from pydantic import BaseModel
from sqlalchemy.orm import Session

from auth.auth_dependencies import get_current_user
from database.database import get_db, SessionLocal
from database.models import PodcastJob, User
from jobs.job_service import (
    create_job,
    get_job_for_user,
    get_jobs_for_user,
    update_job_completed,
    update_job_failed,
)
from jobs.progress_store import set_step, get_step, set_result, get_result, set_error, clear
from middleware.quota_limit import check_user_quota, increment_usage
from schemas.job_schema import JobRead
from services.audio_service import store_audio_file
from services.pdf_service import run_full_pipeline
from utils.file_validation import PdfValidationError, validate_pdf_bytes
from utils.plan_utils import (
    can_generate_podcast,
    can_store_more_jobs,
    get_user_plan_limits,
)
from utils.safe_fetch import UnsafeUrlError, fetch_url_safely
from middleware.rate_limit import limiter


class ProcessUrlRequest(BaseModel):
    url: str
    lang: str = "en"
    mode: str = "podcast"

router = APIRouter(prefix="/api", tags=["jobs"])

STORAGE_ROOT = Path(__file__).resolve().parent.parent / "storage"


def _job_storage_dir(user_id: str, job_id: str) -> Path:
    return STORAGE_ROOT / str(user_id) / str(job_id)


# ---------------------------------------------------------------------------
# Background pipeline task
# ---------------------------------------------------------------------------

async def _run_pipeline_bg(
    job_id: str,
    user_id: str,
    pdf_bytes: bytes,
    lang: str,
    mode: str,
):
    db = SessionLocal()
    try:
        job = db.query(PodcastJob).filter(PodcastJob.id == job_id).first()
        if not job:
            set_error(job_id, "Job not found.")
            return

        storage_dir = _job_storage_dir(user_id, job_id)
        storage_dir.mkdir(parents=True, exist_ok=True)
        input_path = storage_dir / "input.pdf"
        input_path.write_bytes(pdf_bytes)

        def on_step(step: str):
            set_step(job_id, step)

        try:
            result = await run_full_pipeline(pdf_bytes, lang, mode, on_step=on_step)

            stored_audio = store_audio_file(result.audio_path, storage_dir)
            job = update_job_completed(db, job, stored_audio)
            job.tokens_used = result.tokens_used
            db.add(job)

            user = db.query(User).filter(User.id == user_id).first()
            if user:
                increment_usage(user, db, result.tokens_used)

            set_result(job_id, {
                "text": result.text,
                "summary": result.summary,
                "script": result.script,
                "audio_url": f"/api/audio/{job_id}",
                "job_id": job_id,
            })

        except Exception as exc:
            update_job_failed(db, job)
            set_error(job_id, str(exc))

    finally:
        db.close()


# ---------------------------------------------------------------------------
# Routes
# ---------------------------------------------------------------------------

@router.get("/jobs", response_model=list[JobRead])
def list_jobs(
    current_user=Depends(get_current_user),
    db: Session = Depends(get_db),
):
    return get_jobs_for_user(db, str(current_user.id))


@router.get("/jobs/{job_id}", response_model=JobRead)
def get_job(
    job_id: UUID,
    current_user=Depends(get_current_user),
    db: Session = Depends(get_db),
):
    job = get_job_for_user(db, str(current_user.id), str(job_id))
    if not job:
        raise HTTPException(status_code=404, detail="Job not found.")
    return job


@router.get("/jobs/{job_id}/progress")
def get_job_progress(
    job_id: UUID,
    current_user=Depends(get_current_user),
    db: Session = Depends(get_db),
):
    """Returns the current pipeline step for a running job."""
    job_id = str(job_id)
    job = get_job_for_user(db, str(current_user.id), job_id)
    if not job:
        raise HTTPException(status_code=404, detail="Job not found.")
    step = get_step(job_id)
    return {"job_id": job_id, "step": step, "status": job.status}


@router.get("/jobs/{job_id}/result")
def get_job_result(
    job_id: UUID,
    current_user=Depends(get_current_user),
    db: Session = Depends(get_db),
):
    """Returns text/summary/script/audio_url once the job is completed."""
    job_id = str(job_id)
    job = get_job_for_user(db, str(current_user.id), job_id)
    if not job:
        raise HTTPException(status_code=404, detail="Job not found.")
    if job.status == "processing":
        raise HTTPException(status_code=202, detail="Still processing.")
    if job.status == "failed":
        result = get_result(job_id)
        error = result.get("error", "Processing failed.") if result else "Processing failed."
        # 422: the job failed for a reason the client can act on/display,
        # not a 500 (which would imply an unhandled server bug).
        raise HTTPException(status_code=422, detail=error)
    result = get_result(job_id)
    if not result:
        raise HTTPException(status_code=404, detail="Result not available. It may have expired — reload the page.")
    return result


@router.post("/process/upload")
@limiter.limit("10/minute")
async def process_upload(
    request: Request,
    background_tasks: BackgroundTasks,
    file: UploadFile = File(...),
    lang: str = Form("en"),
    mode: str = Form("podcast"),
    current_user=Depends(check_user_quota),
    db: Session = Depends(get_db),
):
    if file.content_type not in ("application/pdf", "application/octet-stream") and not (
        file.filename or ""
    ).endswith(".pdf"):
        raise HTTPException(status_code=400, detail="Only PDF files are supported.")

    if mode in ("podcast", "dialogue"):
        ok, err = can_generate_podcast(current_user)
        if not ok:
            raise HTTPException(status_code=403, detail=err)

    ok, err = can_store_more_jobs(current_user, db)
    if not ok:
        raise HTTPException(status_code=403, detail=err)

    pdf_bytes = await file.read()

    limits = get_user_plan_limits(current_user)
    try:
        validate_pdf_bytes(
            pdf_bytes,
            max_size_mb=limits.max_size_mb,
            max_pages=limits.max_pages,
            require_text=True,
        )
    except PdfValidationError as exc:
        err_msg = str(exc)
        if "exceeds" in err_msg.lower():
            err_msg += " Upgrade to a higher plan for larger files/more pages."
        raise HTTPException(status_code=422, detail=err_msg) from exc

    job = create_job(
        db,
        str(current_user.id),
        file.filename or "document.pdf",
        len(pdf_bytes),
        mode,
    )
    job_id = str(job.id)
    set_step(job_id, "queued")

    background_tasks.add_task(
        _run_pipeline_bg,
        job_id,
        str(current_user.id),
        pdf_bytes,
        lang,
        mode,
    )

    return {"job_id": job_id, "status": "processing"}


@router.post("/process/url")
@limiter.limit("10/minute")
async def process_url(
    request: Request,
    background_tasks: BackgroundTasks,
    body: ProcessUrlRequest,
    current_user=Depends(check_user_quota),
    db: Session = Depends(get_db),
):
    url = body.url
    lang = body.lang
    mode = body.mode

    if mode in ("podcast", "dialogue"):
        ok, err = can_generate_podcast(current_user)
        if not ok:
            raise HTTPException(status_code=403, detail=err)

    ok, err = can_store_more_jobs(current_user, db)
    if not ok:
        raise HTTPException(status_code=403, detail=err)

    limits = get_user_plan_limits(current_user)
    max_bytes = limits.max_size_mb * 1024 * 1024

    try:
        response = await fetch_url_safely(url, max_bytes=max_bytes)
    except UnsafeUrlError as exc:
        raise HTTPException(status_code=400, detail=str(exc)) from exc
    except httpx.HTTPError as exc:
        raise HTTPException(status_code=422, detail=f"Could not fetch URL: {exc}") from exc

    content_type = response.headers.get("content-type", "")
    if "pdf" not in content_type and not url.lower().endswith(".pdf"):
        raise HTTPException(status_code=400, detail="URL does not point to a PDF.")

    pdf_bytes = response.content

    try:
        validate_pdf_bytes(
            pdf_bytes,
            max_size_mb=limits.max_size_mb,
            max_pages=limits.max_pages,
            require_text=True,
        )
    except PdfValidationError as exc:
        err_msg = str(exc)
        if "exceeds" in err_msg.lower():
            err_msg += " Upgrade to a higher plan for larger files/more pages."
        raise HTTPException(status_code=422, detail=err_msg) from exc

    job = create_job(
        db,
        str(current_user.id),
        "document.pdf",
        len(pdf_bytes),
        mode,
    )
    job_id = str(job.id)
    set_step(job_id, "queued")

    background_tasks.add_task(
        _run_pipeline_bg,
        job_id,
        str(current_user.id),
        pdf_bytes,
        lang,
        mode,
    )

    return {"job_id": job_id, "status": "processing"}


@router.get("/audio/{job_id}")
def get_audio(
    job_id: UUID,
    current_user=Depends(get_current_user),
    db: Session = Depends(get_db),
):
    job = get_job_for_user(db, str(current_user.id), str(job_id))
    if not job or not job.audio_path:
        raise HTTPException(status_code=404, detail="Audio not found.")
    audio_path = Path(job.audio_path)
    if not audio_path.exists():
        raise HTTPException(status_code=404, detail="Audio file missing.")
    return FileResponse(audio_path, media_type="audio/mpeg", filename="podcast.mp3")
