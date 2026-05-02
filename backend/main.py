"""
main.py
FastAPI application entry point for the PDF Podcast Summarizer.
"""

import os
from dotenv import load_dotenv
load_dotenv()

import logging
from pathlib import Path

from fastapi import Depends, FastAPI, File, HTTPException, UploadFile
from fastapi.middleware.cors import CORSMiddleware
from fastapi.staticfiles import StaticFiles
from fastapi.responses import FileResponse
from pydantic import BaseModel, HttpUrl
from slowapi import _rate_limit_exceeded_handler
from slowapi.errors import RateLimitExceeded
from slowapi.middleware import SlowAPIMiddleware
from sqlalchemy.orm import Session
from starlette.middleware.sessions import SessionMiddleware

from auth.auth_dependencies import get_current_user
from auth.auth_router import router as auth_router
from database.database import engine, get_db
from database.models import Base
from jobs.job_router import router as job_router
from jobs.job_service import create_job, update_job_completed, update_job_failed
from middleware.quota_limit import check_user_quota, increment_usage
from middleware.rate_limit import limiter
from podcast_generator import generate_podcast_script
from summarizer import summarize_text
from utils.file_validation import PdfValidationError, validate_pdf_bytes
from pdf_parser import extract_text_from_bytes, extract_text_from_url
from audio_generator import generate_audio
from services.audio_service import store_audio_file

logging.basicConfig(level=logging.INFO, format="%(asctime)s [%(levelname)s] %(name)s: %(message)s")
logger = logging.getLogger(__name__)

# ---------------------------------------------------------------------------
# App setup
# ---------------------------------------------------------------------------

from jobs.cleanup_jobs import cleanup_expired_jobs, reset_stale_jobs

app = FastAPI(
    title="PDF Podcast Summarizer",
    description="Upload a PDF or provide a URL to get an AI-generated podcast summary.",
    version="1.0.0",
)

@app.on_event("startup")
async def on_startup():
    db = next(get_db())
    try:
        reset_stale_jobs(db)
        cleanup_expired_jobs(db)
    finally:
        db.close()


@app.get("/api", summary="API Root")
async def root():
    return {
        "message": "Welcome to the PDF Podcast Summarizer API",
        "docs": "/docs",
        "health": "/health",
    }


cors_origins = os.getenv("CORS_ORIGINS", "*").split(",")
if not cors_origins or cors_origins == [""]:
    cors_origins = ["*"]
app.add_middleware(
    CORSMiddleware,
    allow_origins=[origin.strip() for origin in cors_origins],
    allow_methods=["*"],
    allow_headers=["*"],
)

app.state.limiter = limiter
app.add_exception_handler(RateLimitExceeded, _rate_limit_exceeded_handler)
app.add_middleware(SlowAPIMiddleware)
app.add_middleware(SessionMiddleware, secret_key=os.getenv("JWT_SECRET_KEY"))

Base.metadata.create_all(bind=engine)

import traceback
from fastapi.responses import JSONResponse

@app.exception_handler(Exception)
async def debug_exception_handler(request, exc):
    return JSONResponse(
        status_code=500,
        content={"detail": str(exc), "trace": traceback.format_exc()}
    )

# ---------------------------------------------------------------------------
# Request / Response models
# ---------------------------------------------------------------------------


class UrlRequest(BaseModel):
    url: HttpUrl


class TextResponse(BaseModel):
    text: str


class SummaryRequest(BaseModel):
    text: str
    lang: str = "en"
    mode: str = "podcast"


class SummaryResponse(BaseModel):
    summary: str


class PodcastRequest(BaseModel):
    summary: str
    lang: str = "en"
    mode: str = "podcast"


class PodcastResponse(BaseModel):
    script: str
    audio_url: str
    job_id: str | None = None


# ---------------------------------------------------------------------------
# Helpers
# ---------------------------------------------------------------------------


def _require_google_key() -> None:
    if not os.environ.get("GOOGLE_API_KEY"):
        raise HTTPException(
            status_code=503,
            detail="Google Gemini API Key is missing. Please add GOOGLE_API_KEY to your .env file.",
        )


def _handle_api_error(exc: Exception) -> str:
    """Format technical API errors into user-friendly messages."""
    error_str = str(exc).lower()

    if "quota" in error_str or "429" in error_str:
        return "You have reached the free tier limit for Gemini. Wait a minute and try again."
    if "api_key_invalid" in error_str:
        return "The provided Gemini API key is invalid. Please check your .env file."
    if "blocked" in error_str:
        return "The AI blocked this content for safety or policy reasons."

    return f"Free AI Service error: {str(exc)}"


# ---------------------------------------------------------------------------
# Phase 1 – PDF ingestion endpoints
# ---------------------------------------------------------------------------


from utils.plan_utils import (
    can_upload_pdf, 
    can_generate_podcast, 
    can_store_more_jobs, 
    get_user_plan_limits
)

@app.post("/api/upload", response_model=TextResponse, summary="Upload a PDF and extract text")
async def upload_pdf(
    file: UploadFile = File(...),
    current_user=Depends(check_user_quota),  # Use check_user_quota for upload too
):
    """Accept a PDF file upload and return the extracted plain text."""
    if file.content_type not in ("application/pdf", "application/octet-stream") and not (
        file.filename or ""
    ).endswith(".pdf"):
        raise HTTPException(status_code=400, detail="Only PDF files are supported.")

    pdf_bytes = await file.read()
    if not pdf_bytes:
        raise HTTPException(status_code=400, detail="Uploaded file is empty.")

    logger.info("Received PDF for upload: %s (%d bytes)", file.filename, len(pdf_bytes))
    
    # 1. Preliminary check: Size
    limits = get_user_plan_limits(current_user)
    
    try:
        page_count, is_encrypted = validate_pdf_bytes(
            pdf_bytes, 
            max_size_mb=limits.max_size_mb, 
            max_pages=limits.max_pages, 
            require_text=True
        )
    except PdfValidationError as exc:
        # Check if the error is due to plan limits and provide upgrade hint
        err_msg = str(exc)
        if "exceeds" in err_msg.lower():
            err_msg += f" Upgrade to a higher plan for larger files/more pages."
        raise HTTPException(status_code=422, detail=err_msg) from exc

    try:
        logger.info("Starting text extraction...")
        text, final_page_count, is_scanned = extract_text_from_bytes(pdf_bytes)
    except Exception as exc:
        logger.exception("Failed to extract text from uploaded file")
        raise HTTPException(status_code=422, detail=f"Could not parse PDF: {exc}") from exc

    if not text:
        logger.warning("No text extracted from PDF.")
        msg = "No readable text found. Please ensure the PDF is not a scanned image."
        raise HTTPException(status_code=422, detail=msg)

    return TextResponse(text=text)


@app.post("/api/url", response_model=TextResponse, summary="Provide a PDF URL and extract text")
async def pdf_from_url(
    body: UrlRequest,
    current_user=Depends(check_user_quota),
):
    """Download a PDF from a URL and return the extracted plain text."""
    logger.info("Received URL for processing: %s", body.url)
    try:
        text, page_count, is_scanned = await extract_text_from_url(str(body.url))
        
        # Check plan limits AFTER download for URL uploads
        limits = get_user_plan_limits(current_user)
        if page_count > limits.max_pages:
            raise HTTPException(
                status_code=422, 
                detail=f"PDF exceeds {limits.max_pages} pages limit for your plan. Upgrade for more."
            )
            
        logger.info("URL processing complete. Text length: %d", len(text))
    except HTTPException:
        raise
    except Exception as exc:
        logger.exception("Failed to download or parse PDF from URL")
        raise HTTPException(status_code=422, detail=f"Could not fetch/parse PDF: {exc}") from exc

    if not text.strip():
        raise HTTPException(status_code=422, detail="No readable text found in the PDF.")

    return TextResponse(text=text)


# ---------------------------------------------------------------------------
# Summarization endpoints (protected)
# ---------------------------------------------------------------------------


@app.post("/api/summarize", response_model=SummaryResponse, summary="Summarize extracted text")
async def summarize(
    body: SummaryRequest,
    current_user=Depends(get_current_user),
    db: Session = Depends(get_db),
):
    """Accept raw document text and return an AI-generated summary."""
    if not body.text.strip():
        raise HTTPException(status_code=400, detail="No text provided to summarize.")

    _require_google_key()

    try:
        summary, tokens = await summarize_text(body.text, body.lang, body.mode)
        increment_usage(current_user, db, tokens)
    except Exception as exc:
        logger.exception("Summarization failed")
        raise HTTPException(status_code=500, detail=_handle_api_error(exc))

    return SummaryResponse(summary=summary)


@app.post("/api/podcast", response_model=PodcastResponse, summary="Generate podcast from summary")
async def podcast(
    body: PodcastRequest,
    current_user=Depends(check_user_quota),
    db: Session = Depends(get_db),
):
    """Convert a document summary into a podcast script and generate audio."""
    if not body.summary.strip():
        raise HTTPException(status_code=400, detail="No summary provided.")

    _require_google_key()
    
    # 1. Check if plan allows podcast generation
    ok, err = can_generate_podcast(current_user)
    if not ok:
        raise HTTPException(status_code=403, detail=err)
    
    # 2. Check storage limit
    ok, err = can_store_more_jobs(current_user, db)
    if not ok:
        raise HTTPException(status_code=403, detail=err)

    try:
        script, tokens = await generate_podcast_script(body.summary, body.lang, body.mode)
        audio_path = await generate_audio(script, body.lang)

        job = create_job(
            db,
            str(current_user.id),
            "summary.txt",
            0,
            body.mode,
        )
        storage_dir = Path(__file__).resolve().parent / "storage" / str(current_user.id) / str(job.id)
        stored_audio = store_audio_file(audio_path, storage_dir)
        job = update_job_completed(db, job, stored_audio)
        job.tokens_used = tokens
        db.add(job)
        increment_usage(current_user, db, tokens)
        audio_url = f"/api/audio/{job.id}"
    except Exception as exc:
        logger.exception("Podcast generation failed")
        if "job" in locals():
            update_job_failed(db, job)
        raise HTTPException(status_code=500, detail=_handle_api_error(exc))

    return PodcastResponse(script=script, audio_url=audio_url, job_id=str(job.id))


from auth.admin_router import router as admin_router

app.include_router(auth_router)
app.include_router(job_router)
app.include_router(admin_router)


@app.get("/health")
async def health():
    return {"status": "ok"}


# ---------------------------------------------------------------------------
# Serve React frontend (production build)
# ---------------------------------------------------------------------------
_FRONTEND_DIST = Path(__file__).resolve().parent.parent / "frontend" / "dist"

if _FRONTEND_DIST.exists():
    # Serve compiled JS/CSS/assets
    app.mount("/assets", StaticFiles(directory=_FRONTEND_DIST / "assets"), name="assets")

    # Serve any other static file (favicon, manifest, icons, sw.js, etc.)
    # then fall back to index.html for all SPA routes
    @app.get("/{full_path:path}")
    async def serve_spa(full_path: str):
        candidate = _FRONTEND_DIST / full_path
        if candidate.is_file():
            return FileResponse(candidate)
        return FileResponse(_FRONTEND_DIST / "index.html")
