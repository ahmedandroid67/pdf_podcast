"""
tests/test_api.py
Integration tests for the current FastAPI surface (no LLM calls — all AI
calls are mocked). Exercises the real /api/process/* pipeline, not the old
/api/upload+/api/summarize+/api/podcast endpoints, which have been removed.
"""

import io
import uuid
from pathlib import Path
from unittest.mock import AsyncMock, patch

import pymupdf

from services.pdf_service import PipelineResult


def _make_pdf_bytes(text: str = "Test document content.") -> bytes:
    doc = pymupdf.open()
    page = doc.new_page()
    page.insert_text((50, 100), text)
    buf = io.BytesIO()
    doc.save(buf)
    return buf.getvalue()


class TestHealthEndpoint:
    def test_health_returns_ok(self, client):
        resp = client.get("/health")
        assert resp.status_code == 200
        assert resp.json()["status"] == "ok"


class TestAuthFlow:
    def test_register_login_me(self, client):
        email = f"flow-{uuid.uuid4().hex[:8]}@example.com"
        resp = client.post("/auth/register", json={"email": email, "password": "TestPassword123!"})
        assert resp.status_code == 201

        resp = client.post("/auth/login", json={"email": email, "password": "TestPassword123!"})
        assert resp.status_code == 200
        token = resp.json()["access_token"]

        resp = client.get("/auth/me", headers={"Authorization": f"Bearer {token}"})
        assert resp.status_code == 200
        data = resp.json()
        assert data["email"] == email
        # New free-plan user should see the *plan's* daily limit, not a stale column.
        assert data["daily_quota"] == 1

    def test_duplicate_registration_rejected(self, client):
        email = f"dup-{uuid.uuid4().hex[:8]}@example.com"
        client.post("/auth/register", json={"email": email, "password": "TestPassword123!"})
        resp = client.post("/auth/register", json={"email": email, "password": "TestPassword123!"})
        assert resp.status_code == 400

    def test_login_wrong_password_rejected(self, client):
        email = f"wrong-{uuid.uuid4().hex[:8]}@example.com"
        client.post("/auth/register", json={"email": email, "password": "TestPassword123!"})
        resp = client.post("/auth/login", json={"email": email, "password": "WrongPassword!"})
        assert resp.status_code == 401


class TestProcessUploadEndpoint:
    def test_requires_auth(self, client):
        pdf_bytes = _make_pdf_bytes()
        resp = client.post(
            "/api/process/upload",
            files={"file": ("test.pdf", io.BytesIO(pdf_bytes), "application/pdf")},
        )
        assert resp.status_code == 401

    def test_rejects_non_pdf(self, client, auth_headers):
        resp = client.post(
            "/api/process/upload",
            files={"file": ("test.txt", io.BytesIO(b"not a pdf"), "text/plain")},
            headers=auth_headers,
        )
        assert resp.status_code == 400

    def test_rejects_empty_file(self, client, auth_headers):
        # mode="audiobook" bypasses the free-plan can_generate_podcast gate so
        # this test actually exercises the empty-file validation, not the
        # (separately tested) plan-gating logic.
        resp = client.post(
            "/api/process/upload",
            files={"file": ("empty.pdf", io.BytesIO(b""), "application/pdf")},
            data={"mode": "audiobook"},
            headers=auth_headers,
        )
        assert resp.status_code == 422

    def test_free_plan_cannot_generate_podcast(self, client, auth_headers):
        pdf_bytes = _make_pdf_bytes("Hello from a PDF")
        resp = client.post(
            "/api/process/upload",
            files={"file": ("test.pdf", io.BytesIO(pdf_bytes), "application/pdf")},
            data={"mode": "podcast"},
            headers=auth_headers,
        )
        assert resp.status_code == 403

    def test_full_pipeline_success(self, client, auth_headers, tmp_path, monkeypatch):
        monkeypatch.setattr("jobs.job_router.STORAGE_ROOT", tmp_path)

        pdf_bytes = _make_pdf_bytes("Hello from a PDF")
        fake_audio = tmp_path / "generated.mp3"
        fake_audio.write_bytes(b"\xff\xfb" * 10)

        fake_result = PipelineResult(
            text="Hello from a PDF",
            summary="A short summary.",
            script="Welcome to the podcast!",
            audio_path=fake_audio,
            page_count=1,
            tokens_used=42,
        )

        with patch("jobs.job_router.run_full_pipeline", new_callable=AsyncMock) as mock_pipeline:
            mock_pipeline.return_value = fake_result
            resp = client.post(
                "/api/process/upload",
                files={"file": ("test.pdf", io.BytesIO(pdf_bytes), "application/pdf")},
                data={"lang": "en", "mode": "audiobook"},
                headers=auth_headers,
            )

        assert resp.status_code == 200
        job_id = resp.json()["job_id"]

        result_resp = client.get(f"/api/jobs/{job_id}/result", headers=auth_headers)
        assert result_resp.status_code == 200
        data = result_resp.json()
        assert data["summary"] == "A short summary."
        assert data["script"] == "Welcome to the podcast!"

        jobs_resp = client.get("/api/jobs", headers=auth_headers)
        assert jobs_resp.status_code == 200
        jobs = jobs_resp.json()
        assert any(j["id"] == job_id for j in jobs)
        completed = next(j for j in jobs if j["id"] == job_id)
        assert completed["has_audio"] is True
        assert "audio_path" not in completed  # server filesystem path must not leak


class TestJobLookup:
    def test_unknown_job_returns_404(self, client, auth_headers):
        resp = client.get(f"/api/jobs/{uuid.uuid4()}/result", headers=auth_headers)
        assert resp.status_code == 404

    def test_invalid_job_id_returns_422(self, client, auth_headers):
        resp = client.get("/api/jobs/not-a-uuid/result", headers=auth_headers)
        assert resp.status_code == 422


class TestProcessUrlSsrfProtection:
    def test_rejects_private_ip(self, client, auth_headers):
        resp = client.post(
            "/api/process/url",
            json={"url": "http://127.0.0.1/document.pdf", "mode": "audiobook"},
            headers=auth_headers,
        )
        assert resp.status_code == 400

    def test_rejects_non_http_scheme(self, client, auth_headers):
        resp = client.post(
            "/api/process/url",
            json={"url": "file:///etc/passwd", "mode": "audiobook"},
            headers=auth_headers,
        )
        assert resp.status_code == 400

    def test_requires_auth(self, client):
        resp = client.post("/api/process/url", json={"url": "https://example.com/a.pdf"})
        assert resp.status_code == 401
