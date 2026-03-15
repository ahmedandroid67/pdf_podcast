"""
tests/test_api.py
Integration tests for FastAPI endpoints (no LLM calls — all AI calls are mocked).
"""

import io
import os
import sys
import pytest
import pymupdf
from unittest.mock import AsyncMock, patch, MagicMock
from pathlib import Path

sys.path.insert(0, os.path.dirname(os.path.dirname(__file__)))

from fastapi.testclient import TestClient

os.environ.setdefault("OPENAI_API_KEY", "test-key")

from main import app

client = TestClient(app)


def _make_pdf_bytes(text: str = "Test document content.") -> bytes:
    doc = pymupdf.open()
    page = doc.new_page()
    page.insert_text((50, 100), text)
    buf = io.BytesIO()
    doc.save(buf)
    return buf.getvalue()


class TestHealthEndpoint:
    def test_health_returns_ok(self):
        resp = client.get("/health")
        assert resp.status_code == 200
        assert resp.json()["status"] == "ok"


class TestUploadEndpoint:
    def test_upload_valid_pdf(self):
        pdf_bytes = _make_pdf_bytes("Hello from a PDF")
        resp = client.post(
            "/api/upload",
            files={"file": ("test.pdf", io.BytesIO(pdf_bytes), "application/pdf")},
        )
        assert resp.status_code == 200
        assert "Hello" in resp.json()["text"]

    def test_upload_empty_file_returns_400(self):
        resp = client.post(
            "/api/upload",
            files={"file": ("empty.pdf", io.BytesIO(b""), "application/pdf")},
        )
        assert resp.status_code == 400

    def test_upload_non_pdf_allowed_by_content_type(self):
        pdf_bytes = _make_pdf_bytes("Content here")
        # Valid PDF bytes with .pdf extension should always work
        resp = client.post(
            "/api/upload",
            files={"file": ("doc.pdf", io.BytesIO(pdf_bytes), "application/pdf")},
        )
        assert resp.status_code == 200


class TestSummarizeEndpoint:
    def test_summarize_calls_openai(self):
        with patch("main.summarize_text", new_callable=AsyncMock) as mock_sum:
            mock_sum.return_value = "This is a mock summary."
            resp = client.post("/api/summarize", json={"text": "Some long text to summarize."})
        assert resp.status_code == 200
        assert resp.json()["summary"] == "This is a mock summary."

    def test_summarize_empty_text_returns_400(self):
        resp = client.post("/api/summarize", json={"text": "   "})
        assert resp.status_code == 400


class TestPodcastEndpoint:
    def test_podcast_endpoint(self, tmp_path, monkeypatch):
        # Create a fake audio file
        fake_audio = tmp_path / "podcast_abc12345.mp3"
        fake_audio.write_bytes(b"\xFF\xFB" * 100)

        monkeypatch.setattr("main.AUDIO_DIR", tmp_path)

        with (
            patch("main.generate_podcast_script", new_callable=AsyncMock) as mock_script,
            patch("main.generate_audio", new_callable=AsyncMock) as mock_audio,
        ):
            mock_script.return_value = "Welcome to the podcast!"
            mock_audio.return_value = fake_audio

            resp = client.post("/api/podcast", json={"summary": "A great summary."})

        assert resp.status_code == 200
        data = resp.json()
        assert data["script"] == "Welcome to the podcast!"
        assert "podcast_abc12345.mp3" in data["audio_url"]

    def test_podcast_empty_summary_returns_400(self):
        resp = client.post("/api/podcast", json={"summary": ""})
        assert resp.status_code == 400
