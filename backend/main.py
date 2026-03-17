"""
main.py
FastAPI application entry point for the PDF Podcast Summarizer.
"""

import logging
import os
from pathlib import Path



from fastapi import FastAPI, File, HTTPException, UploadFile
from fastapi.middleware.cors import CORSMiddleware
from fastapi.responses import FileResponse
from fastapi.staticfiles import StaticFiles
from pydantic import BaseModel, HttpUrl

from audio_generator import AUDIO_DIR, delete_audio_file, generate_audio, list_audio_files
from pdf_parser import extract_text_from_bytes, extract_text_from_url
from podcast_generator import generate_podcast_script
from summarizer import summarize_text
from dotenv import load_dotenv

load_dotenv()

logging.basicConfig(level=logging.INFO, format="%(asctime)s [%(levelname)s] %(name)s: %(message)s")
logger = logging.getLogger(__name__)

# ---------------------------------------------------------------------------
# App setup
# ---------------------------------------------------------------------------

app = FastAPI(
    title="PDF Podcast Summarizer",
    description="Upload a PDF or provide a URL to get an AI-generated podcast summary.",
    version="1.0.0",
)

@app.get("/", summary="API Root")
async def root():
    return {
        "message": "Welcome to the PDF Podcast Summarizer API",
        "docs": "/docs",
        "health": "/health"
    }

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_methods=["*"],
    allow_headers=["*"],
)

# Serve generated audio files statically
AUDIO_DIR.mkdir(exist_ok=True)
app.mount("/audio", StaticFiles(directory=str(AUDIO_DIR)), name="audio")

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


class FullPipelineResponse(BaseModel):
    text: str
    summary: str
    script: str
    audio_url: str


# ---------------------------------------------------------------------------
# Phase 1 – PDF ingestion endpoints
# ---------------------------------------------------------------------------


@app.post("/api/upload", response_model=TextResponse, summary="Upload a PDF and extract text")
async def upload_pdf(file: UploadFile = File(...)):
    """Accept a PDF file upload and return the extracted plain text."""
    if file.content_type not in ("application/pdf", "application/octet-stream") and not (
        file.filename or ""
    ).endswith(".pdf"):
        raise HTTPException(status_code=400, detail="Only PDF files are supported.")

    pdf_bytes = await file.read()
    if not pdf_bytes:
        raise HTTPException(status_code=400, detail="Uploaded file is empty.")

    logger.info("Received PDF for upload: %s (%d bytes)", file.filename, len(pdf_bytes))
    try:
        logger.info("Starting text extraction...")
        text, page_count, is_scanned = extract_text_from_bytes(pdf_bytes)
        logger.info("Extraction complete. Text length: %d, Pages: %d, Scanned: %s", len(text), page_count, is_scanned)
    except Exception as exc:
        logger.exception("Failed to extract text from uploaded file")
        raise HTTPException(status_code=422, detail=f"Could not parse PDF: {exc}") from exc

    if not text:
        logger.warning("No text extracted from PDF.")
        if is_scanned:
            msg = f"This document ({page_count} pages) appears to be a scanned image. We currently only support digital PDFs with readable text."
        else:
            msg = f"No readable text found. (File has {page_count} pages). Please ensure the PDF is not empty or corrupted."
        raise HTTPException(status_code=422, detail=msg)

    return TextResponse(text=text)


@app.post("/api/url", response_model=TextResponse, summary="Provide a PDF URL and extract text")
async def pdf_from_url(body: UrlRequest):
    """Download a PDF from a URL and return the extracted plain text."""
    logger.info("Received URL for processing: %s", body.url)
    try:
        text, page_count, is_scanned = await extract_text_from_url(str(body.url))
        logger.info("URL processing complete. Text length: %d", len(text))
    except Exception as exc:
        logger.exception("Failed to download or parse PDF from URL")
        raise HTTPException(status_code=422, detail=f"Could not fetch/parse PDF: {exc}") from exc

    if not text.strip():
        raise HTTPException(status_code=422, detail="No readable text found in the PDF.")

    return TextResponse(text=text)


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


@app.post("/api/summarize", response_model=SummaryResponse, summary="Summarize extracted text")
async def summarize(body: SummaryRequest):
    """Accept raw document text and return an AI-generated summary."""
    if not body.text.strip():
        raise HTTPException(status_code=400, detail="No text provided to summarize.")

    _require_google_key()

    try:
        summary = await summarize_text(body.text, body.lang, body.mode)
    except Exception as exc:
        logger.exception("Summarization failed")
        raise HTTPException(status_code=500, detail=_handle_api_error(exc))

    return SummaryResponse(summary=summary)


@app.post("/api/podcast", response_model=PodcastResponse, summary="Generate podcast from summary")
async def podcast(body: PodcastRequest):
    """Convert a document summary into a podcast script and generate audio."""
    if not body.summary.strip():
        raise HTTPException(status_code=400, detail="No summary provided.")

    _require_google_key()

    try:
        script = await generate_podcast_script(body.summary, body.lang, body.mode)
        audio_path = await generate_audio(script, body.lang)
        audio_url = f"/audio/{audio_path.name}"
    except Exception as exc:
        logger.exception("Podcast generation failed")
        raise HTTPException(status_code=500, detail=_handle_api_error(exc))

    return PodcastResponse(script=script, audio_url=audio_url)


# ---------------------------------------------------------------------------
# Full pipeline – single endpoint for the whole workflow
# ---------------------------------------------------------------------------


@app.post(
    "/api/process/upload",
    response_model=FullPipelineResponse,
    summary="Full pipeline: upload PDF → summarize → generate podcast",
)
async def process_upload(file: UploadFile = File(...)):
    """One-shot endpoint: upload a PDF, get back text, summary, script, and audio."""
    _require_google_key()
    pdf_bytes = await file.read()
    if not pdf_bytes:
        raise HTTPException(status_code=400, detail="Uploaded file is empty.")

    try:
        text, page_count, is_scanned = extract_text_from_bytes(pdf_bytes)
        if not text:
            msg = "Could not extract text."
            if is_scanned:
                msg = "Document appears to be a scanned image."
            raise ValueError(msg)
            
        summary = await summarize_text(text)
        script = await generate_podcast_script(summary)
        audio_path = await generate_audio(script)
    except Exception as exc:
        logger.exception("Full pipeline failed (upload)")
        raise HTTPException(status_code=500, detail=_handle_api_error(exc))

    return FullPipelineResponse(
        text=text,
        summary=summary,
        script=script,
        audio_url=f"/audio/{audio_path.name}",
    )


@app.post(
    "/api/process/url",
    response_model=FullPipelineResponse,
    summary="Full pipeline: PDF URL → summarize → generate podcast",
)
async def process_url(body: UrlRequest):
    """One-shot endpoint: provide a PDF URL, get back text, summary, script, and audio."""
    _require_google_key()
    try:
        text, page_count, is_scanned = await extract_text_from_url(str(body.url))
        if not text:
            raise ValueError("No text found in PDF.")
            
        summary = await summarize_text(text)
        script = await generate_podcast_script(summary)
        audio_path = await generate_audio(script)
    except Exception as exc:
        logger.exception("Full pipeline failed (URL)")
        raise HTTPException(status_code=500, detail=_handle_api_error(exc))

    return FullPipelineResponse(
        text=text,
        summary=summary,
        script=script,
        audio_url=f"/audio/{audio_path.name}",
    )


# ---------------------------------------------------------------------------
# Utility endpoints
# ---------------------------------------------------------------------------


@app.get("/api/audio", summary="List generated audio files")
async def get_audio_list():
    return {"files": list_audio_files()}


@app.delete("/api/audio/{filename}", summary="Delete an audio file")
async def remove_audio(filename: str):
    if not delete_audio_file(filename):
        raise HTTPException(status_code=404, detail="File not found.")
    return {"deleted": filename}


@app.get("/health")
async def health():
    return {"status": "ok"}


# Static files serving setup
AUDIO_DIR.mkdir(parents=True, exist_ok=True)
app.mount("/audio", StaticFiles(directory=str(AUDIO_DIR)), name="audio")
