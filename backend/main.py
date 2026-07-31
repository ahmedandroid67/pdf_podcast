"""
main.py
FastAPI application entry point for PdfPodcaster.
"""

import os
from dotenv import load_dotenv
load_dotenv()

import asyncio
import logging
import traceback
from contextlib import asynccontextmanager
from pathlib import Path

from fastapi import Depends, FastAPI
from fastapi.middleware.cors import CORSMiddleware
from fastapi.responses import JSONResponse
from fastapi.staticfiles import StaticFiles
from slowapi import _rate_limit_exceeded_handler
from slowapi.errors import RateLimitExceeded
from slowapi.middleware import SlowAPIMiddleware
from sqlalchemy import text
from sqlalchemy.orm import Session
from starlette.middleware.sessions import SessionMiddleware

from auth.auth_router import router as auth_router
from database.database import get_db
from jobs.cleanup_jobs import cleanup_expired_jobs, reset_stale_jobs
from jobs.job_router import router as job_router
from middleware.rate_limit import limiter

logging.basicConfig(level=logging.INFO, format="%(asctime)s [%(levelname)s] %(name)s: %(message)s")
logger = logging.getLogger(__name__)

# ---------------------------------------------------------------------------
# App setup
# ---------------------------------------------------------------------------

_DEBUG = os.getenv("DEBUG", "false").lower() == "true"

_SESSION_SECRET_KEY = os.getenv("SESSION_SECRET_KEY") or os.getenv("JWT_SECRET_KEY")
if not _SESSION_SECRET_KEY:
    if _DEBUG:
        _SESSION_SECRET_KEY = "temporary-secret-key-for-dev-only"
    else:
        raise RuntimeError("SESSION_SECRET_KEY or JWT_SECRET_KEY must be set.")

CLEANUP_INTERVAL_SECONDS = int(os.getenv("CLEANUP_INTERVAL_SECONDS", str(6 * 3600)))


async def _cleanup_loop():
    """Periodically purge expired jobs so retention limits are actually enforced."""
    while True:
        try:
            db = next(get_db())
            try:
                cleanup_expired_jobs(db)
            finally:
                db.close()
        except Exception:
            logger.exception("Periodic cleanup failed")
        await asyncio.sleep(CLEANUP_INTERVAL_SECONDS)


@asynccontextmanager
async def lifespan(app: FastAPI):
    db = next(get_db())
    try:
        reset_stale_jobs(db)
        cleanup_expired_jobs(db)
    finally:
        db.close()
    cleanup_task = asyncio.create_task(_cleanup_loop())
    try:
        yield
    finally:
        cleanup_task.cancel()


app = FastAPI(
    title="PdfPodcaster API",
    description="Upload a PDF or provide a URL to get an AI-generated podcast summary.",
    version="1.0.0",
    lifespan=lifespan,
)


@app.get("/api", summary="API Root")
async def root():
    return {
        "message": "Welcome to the PdfPodcaster API",
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
app.add_middleware(
    SessionMiddleware,
    secret_key=_SESSION_SECRET_KEY,
    session_cookie="pdfpodcaster_session",
    max_age=3600,
    same_site="lax",
)

# Schema is managed by Alembic (see migrations/) — run `alembic upgrade head`
# before starting the app (the Docker image's CMD does this). No create_all
# here: it silently diverges from migration history and can't express
# changes like column drops, renames, or the FK/index changes in 0002.


@app.exception_handler(Exception)
async def global_exception_handler(request, exc):
    logger.exception("Unhandled exception on %s %s", request.method, request.url.path)
    if _DEBUG:
        return JSONResponse(
            status_code=500,
            content={"detail": str(exc), "trace": traceback.format_exc()},
        )
    return JSONResponse(
        status_code=500,
        content={"detail": "An internal error occurred. Please try again later."},
    )


from auth.admin_router import router as admin_router

app.include_router(auth_router)
app.include_router(job_router)
app.include_router(admin_router)


@app.get("/health")
async def health(db: Session = Depends(get_db)):
    try:
        db.execute(text("SELECT 1"))
        db_status = "ok"
    except Exception as exc:
        db_status = f"error: {exc}"
    return {"status": "ok", "database": db_status}


# ---------------------------------------------------------------------------
# Serve React frontend (production build)
# ---------------------------------------------------------------------------
_FRONTEND_DIST = Path(__file__).resolve().parent.parent / "frontend" / "dist"

if _FRONTEND_DIST.exists():
    # Mount the built frontend as a complete SPA so hashed CSS/JS, icons,
    # manifest, and client-side routes are all served through one static handler.
    app.mount("/", StaticFiles(directory=_FRONTEND_DIST, html=True), name="frontend")
