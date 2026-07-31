import os
import sys
import tempfile

# Add the backend directory to the Python path so all modules resolve
sys.path.insert(0, os.path.dirname(os.path.dirname(__file__)))

# Force a throwaway SQLite DB + deterministic secrets for the test run.
# These must be set *before* anything imports `main`/`database.database`,
# since load_dotenv() in main.py won't override already-set env vars.
_TEST_DB_PATH = os.path.join(tempfile.gettempdir(), "pdfparser_test.db")
if os.path.exists(_TEST_DB_PATH):
    os.remove(_TEST_DB_PATH)

os.environ.setdefault("DATABASE_URL", f"sqlite:///{_TEST_DB_PATH}")
os.environ.setdefault("JWT_SECRET_KEY", "test-only-secret-do-not-use-in-prod")
os.environ.setdefault("GOOGLE_API_KEY", "test-key")
os.environ.setdefault("DEBUG", "true")
os.environ.setdefault("CORS_ORIGINS", "*")

_BACKEND_DIR = os.path.dirname(os.path.dirname(__file__))

# main.py no longer calls Base.metadata.create_all() — schema is managed by
# Alembic. Build the test DB the same way production does (alembic upgrade
# head), which also exercises the migrations themselves on every test run.
from alembic import command
from alembic.config import Config

_alembic_cfg = Config(os.path.join(_BACKEND_DIR, "alembic.ini"))
_alembic_cfg.set_main_option("script_location", os.path.join(_BACKEND_DIR, "migrations"))
command.upgrade(_alembic_cfg, "head")

import pytest
from fastapi.testclient import TestClient

from main import app
from database.database import SessionLocal
from middleware.rate_limit import limiter

# Rate limits are keyed by client IP, which is identical for every request
# the TestClient makes — without this, the 2nd+ test hitting /auth/register
# or /auth/login trips the same 3-per-minute / 5-per-minute limits.
limiter.enabled = False


@pytest.fixture()
def client():
    with TestClient(app) as c:
        yield c


@pytest.fixture()
def db_session():
    session = SessionLocal()
    try:
        yield session
    finally:
        session.close()


@pytest.fixture()
def auth_headers(client):
    """Register + log in a fresh user, return {'Authorization': 'Bearer ...'}."""
    import uuid

    email = f"test-{uuid.uuid4().hex[:12]}@example.com"
    password = "TestPassword123!"

    resp = client.post("/auth/register", json={"email": email, "password": password})
    assert resp.status_code == 201, resp.text

    resp = client.post("/auth/login", json={"email": email, "password": password})
    assert resp.status_code == 200, resp.text
    token = resp.json()["access_token"]

    return {"Authorization": f"Bearer {token}"}
