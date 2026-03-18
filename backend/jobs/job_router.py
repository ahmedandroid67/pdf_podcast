from pathlib import Path

from fastapi import APIRouter, Depends, File, Form, HTTPException, UploadFile, Request
from fastapi.responses import FileResponse
from sqlalchemy.orm import Session
import httpx

from auth.auth_dependencies import get_current_user
from database.database import get_db
from jobs.job_service import (
    create_job,
    get_job_for_user,
    get_jobs_for_user,
    update_job_completed,
    update_job_failed,
)
from middleware.quota_limit import check_user_quota, increment_usage
from schemas.job_schema import JobRead
from services.audio_service import store_audio_file
from services.pdf_service import run_full_pipeline
from utils.file_validation import PdfValidationError, validate_pdf_bytes
from middleware.rate_limit import limiter

router = APIRouter(prefix="/api", tags=["jobs"])

STORAGE_ROOT = Path(__file__).resolve().parent.parent / "storage"


def _job_storage_dir(user_id: str, job_id: str) -> Path:
    return STORAGE_ROOT / str(user_id) / str(job_id)


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


@router.post("/process/upload")
@limiter.limit("10/minute")
async def process_upload(
    request: Request,
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

    pdf_bytes = await file.read()
    try:
        validate_pdf_bytes(pdf_bytes, max_size_mb=20, max_pages=100, require_text=True)
    except PdfValidationError as exc:
        raise HTTPException(status_code=422, detail=str(exc)) from exc

    job = create_job(
        db,
        str(current_user.id),
        file.filename or "document.pdf",
        len(pdf_bytes),
        mode,
    )

    storage_dir = _job_storage_dir(current_user.id, job.id)
    storage_dir.mkdir(parents=True, exist_ok=True)
    input_path = storage_dir / "input.pdf"
    input_path.write_bytes(pdf_bytes)

    try:
        text, summary, script, audio_path, _, _ = await run_full_pipeline(
            pdf_bytes, lang, mode
        )
        stored_audio = store_audio_file(audio_path, storage_dir)
        job = update_job_completed(db, job, stored_audio)
        increment_usage(current_user, db)
    except Exception as exc:
        update_job_failed(db, job)
        raise HTTPException(status_code=500, detail=str(exc)) from exc

    return {
        "text": text,
        "summary": summary,
        "script": script,
        "audio_url": f"/api/audio/{job.id}",
        "job_id": str(job.id),
    }


@router.post("/process/url")
@limiter.limit("10/minute")
async def process_url(
    request: Request,
    body: dict,
    current_user=Depends(check_user_quota),
    db: Session = Depends(get_db),
):
    url = body.get("url")
    lang = body.get("lang", "en")
    mode = body.get("mode", "podcast")
    if not url:
        raise HTTPException(status_code=400, detail="Missing URL.")

    async with httpx.AsyncClient(follow_redirects=True, timeout=30) as client:
        response = await client.get(url)
        response.raise_for_status()

    content_type = response.headers.get("content-type", "")
    if "pdf" not in content_type and not url.lower().endswith(".pdf"):
        raise HTTPException(status_code=400, detail="URL does not point to a PDF.")

    pdf_bytes = response.content
    try:
        validate_pdf_bytes(pdf_bytes, max_size_mb=20, max_pages=100, require_text=True)
    except PdfValidationError as exc:
        raise HTTPException(status_code=422, detail=str(exc)) from exc

    job = create_job(
        db,
        str(current_user.id),
        "document.pdf",
        len(pdf_bytes),
        mode,
    )

    storage_dir = _job_storage_dir(current_user.id, job.id)
    storage_dir.mkdir(parents=True, exist_ok=True)
    input_path = storage_dir / "input.pdf"
    input_path.write_bytes(pdf_bytes)

    try:
        text, summary, script, audio_path, _, _ = await run_full_pipeline(
            pdf_bytes, lang, mode
        )
        stored_audio = store_audio_file(audio_path, storage_dir)
        job = update_job_completed(db, job, stored_audio)
        increment_usage(current_user, db)
    except Exception as exc:
        update_job_failed(db, job)
        raise HTTPException(status_code=500, detail=str(exc)) from exc

    return {
        "text": text,
        "summary": summary,
        "script": script,
        "audio_url": f"/api/audio/{job.id}",
        "job_id": str(job.id),
    }


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
    return FileResponse(audio_path, media_type="audio/mpeg", filename="audio.mp3")
