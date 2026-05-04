import asyncio
from pathlib import Path

from fastapi import APIRouter, BackgroundTasks, Depends, File, Form, HTTPException, UploadFile, Request
from fastapi.responses import FileResponse
from sqlalchemy.orm import Session
import httpx

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
    can_upload_pdf,
    can_generate_podcast,
    can_store_more_jobs,
    get_user_plan_limits,
)
from middleware.rate_limit import limiter

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
            text, summary, script, audio_path, tokens_used_summarizer, tokens_used_podcast = \
                await run_full_pipeline(pdf_bytes, lang, mode, on_step=on_step)

            total_tokens = (tokens_used_summarizer or 0) + (tokens_used_podcast or 0)
            stored_audio = store_audio_file(audio_path, storage_dir)
            job = update_job_completed(db, job, stored_audio)
            job.tokens_used = total_tokens
            db.add(job)

            from middleware.quota_limit import increment_usage
            user = db.query(User).filter(User.id == user_id).first()
            if user:
                increment_usage(user, db, total_tokens)

            set_result(job_id, {
                "text": text,
                "summary": summary,
                "script": script,
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
    job_id: str,
    current_user=Depends(get_current_user),
    db: Session = Depends(get_db),
):
    job = get_job_for_user(db, str(current_user.id), job_id)
    if not job:
        raise HTTPException(status_code=404, detail="Job not found.")
    return job


@router.get("/jobs/{job_id}/progress")
def get_job_progress(
    job_id: str,
    current_user=Depends(get_current_user),
    db: Session = Depends(get_db),
):
    """Returns the current pipeline step for a running job."""
    job = get_job_for_user(db, str(current_user.id), job_id)
    if not job:
        raise HTTPException(status_code=404, detail="Job not found.")
    step = get_step(job_id)
    return {"job_id": job_id, "step": step, "status": job.status}


@router.get("/jobs/{job_id}/result")
def get_job_result(
    job_id: str,
    current_user=Depends(get_current_user),
    db: Session = Depends(get_db),
):
    """Returns text/summary/script/audio_url once the job is completed."""
    job = get_job_for_user(db, str(current_user.id), job_id)
    if not job:
        raise HTTPException(status_code=404, detail="Job not found.")
    if job.status == "processing":
        raise HTTPException(status_code=202, detail="Still processing.")
    if job.status == "failed":
        result = get_result(job_id)
        error = result.get("error", "Processing failed.") if result else "Processing failed."
        raise HTTPException(status_code=500, detail=error)
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
    body: dict,
    current_user=Depends(check_user_quota),
    db: Session = Depends(get_db),
):
    url = body.get("url")
    lang = body.get("lang", "en")
    mode = body.get("mode", "podcast")
    if not url:
        raise HTTPException(status_code=400, detail="Missing URL.")

    if mode in ("podcast", "dialogue"):
        ok, err = can_generate_podcast(current_user)
        if not ok:
            raise HTTPException(status_code=403, detail=err)

    ok, err = can_store_more_jobs(current_user, db)
    if not ok:
        raise HTTPException(status_code=403, detail=err)

    async with httpx.AsyncClient(follow_redirects=True, timeout=30) as client:
        response = await client.get(url)
        response.raise_for_status()

    content_type = response.headers.get("content-type", "")
    if "pdf" not in content_type and not url.lower().endswith(".pdf"):
        raise HTTPException(status_code=400, detail="URL does not point to a PDF.")

    pdf_bytes = response.content

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
    job_id: str,
    current_user=Depends(get_current_user),
    db: Session = Depends(get_db),
):
    job = get_job_for_user(db, str(current_user.id), job_id)
    if not job or not job.audio_path:
        raise HTTPException(status_code=404, detail="Audio not found.")
    audio_path = Path(job.audio_path)
    if not audio_path.exists():
        raise HTTPException(status_code=404, detail="Audio file missing.")
    return FileResponse(audio_path, media_type="audio/mpeg", filename="podcast.mp3")
