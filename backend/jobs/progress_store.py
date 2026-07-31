"""
progress_store.py
In-memory store for tracking pipeline progress per job.
Works for single-instance deployments. For multi-instance, replace with Redis.

Entries are evicted after RESULT_TTL_SECONDS so a server that runs for a long
time without restarting doesn't accumulate every job's full text/summary/script
in memory forever.
"""
import time
from typing import Dict, Optional, Tuple

RESULT_TTL_SECONDS = 30 * 60  # 30 minutes is plenty for a client to poll+fetch

_steps: Dict[str, str] = {}
_results: Dict[str, Tuple[float, dict]] = {}  # job_id -> (stored_at, data)


def _evict_expired():
    cutoff = time.time() - RESULT_TTL_SECONDS
    expired = [job_id for job_id, (stored_at, _) in _results.items() if stored_at < cutoff]
    for job_id in expired:
        _results.pop(job_id, None)
        _steps.pop(job_id, None)


def set_step(job_id: str, step: str):
    _steps[job_id] = step


def get_step(job_id: str) -> str:
    return _steps.get(job_id, "queued")


def set_result(job_id: str, data: dict):
    _evict_expired()
    _results[job_id] = (time.time(), data)
    _steps[job_id] = "done"


def get_result(job_id: str) -> Optional[dict]:
    entry = _results.get(job_id)
    return entry[1] if entry else None


def set_error(job_id: str, message: str):
    _evict_expired()
    _steps[job_id] = "failed"
    _results[job_id] = (time.time(), {"error": message})


def clear(job_id: str):
    _steps.pop(job_id, None)
    _results.pop(job_id, None)
