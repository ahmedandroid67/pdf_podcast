# PDF Podcast Summarizer

A full-stack web application that converts PDF documents into AI-generated podcast-style audio summaries.

## Architecture

- **Frontend**: React + Vite + Tailwind CSS (port 5000)
- **Backend**: FastAPI (Python) running on port 8000
- **Database**: SQLite (`backend/app.db`) by default; supports PostgreSQL via `DATABASE_URL`
- **Auth**: JWT-based with optional Google OAuth

## Core Features

- Upload PDF or provide URL to PDF
- Extract text using PyMuPDF
- Summarize using Google Gemini AI
- Generate podcast script and TTS audio via edge-tts
- User authentication (email/password + Google OAuth)
- Subscription plan limits (Free, Basic, Pro)
- Job history with audio playback
- Multi-language support (EN, FR, AR)

## Workflows

- **Start application** — Runs `cd frontend && npm run dev` on port 5000 (webview)
- **Backend API** — Runs `cd backend && python -m uvicorn main:app --host localhost --port 8000 --reload` (console)

## Environment Variables

Required secrets/env vars:
- `JWT_SECRET_KEY` — Secret key for JWT signing and session middleware
- `GOOGLE_API_KEY` — Google Gemini API key for AI summarization
- `CORS_ORIGINS` — Allowed CORS origins (defaults to `*`)
- `DATABASE_URL` — Database URL (defaults to SQLite `./app.db`)
- `GOOGLE_CLIENT_ID` / `GOOGLE_CLIENT_SECRET` — For Google OAuth login
- `REDIRECT_URI` — OAuth callback URI
- `FRONTEND_URL` — Frontend base URL for OAuth redirects

## Project Structure

```
frontend/          React frontend (Vite)
  src/
    pages/         Route-level components
    components/    Reusable UI components
    context/       React context (auth, etc.)
    locales/       i18n translations
    api.js         API client helpers

backend/           FastAPI backend
  main.py          Application entry point
  auth/            Auth router, service, dependencies
  database/        SQLAlchemy models and DB connection
  jobs/            Job management (create, list, cleanup)
  middleware/       Rate limiting, quota checking
  schemas/         Pydantic schemas
  services/        Audio file storage
  storage/         Per-user audio file storage
  utils/           File validation, security helpers
  pdf_parser.py    PDF text extraction
  summarizer.py    Gemini-based summarization
  podcast_generator.py  Podcast script generation
  audio_generator.py    TTS audio generation
```

## Deployment

Configured for autoscale deployment:
- **Build**: `cd frontend && npm install && npm run build`
- **Run**: `cd backend && python -m uvicorn main:app --host 0.0.0.0 --port 5000`

Note: For production deployment, the backend serves on port 5000 and should serve the built frontend static files. You may want to configure FastAPI to serve the frontend's `dist/` folder as static files.

## Key Dependencies

- Node.js 20+ (required for Vite 8)
- Python 3.12
- PyMuPDF for PDF parsing
- google-generativeai for AI summarization
- edge-tts for text-to-speech
- SQLAlchemy + Alembic for database ORM and migrations
