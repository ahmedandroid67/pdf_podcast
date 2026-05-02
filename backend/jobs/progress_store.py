"""
progress_store.py
In-memory store for tracking pipeline progress per job.
Works for single-instance deployments. For multi-instance, replace with Redis.
"""
from typing import Dict, Optional

_steps: Dict[str, str] = {}
_results: Dict[str, dict] = {}


def set_step(job_id: str, step: str):
    _steps[job_id] = step


def get_step(job_id: str) -> str:
    return _steps.get(job_id, "queued")


def set_result(job_id: str, data: dict):
    _results[job_id] = data
    _steps[job_id] = "done"


def get_result(job_id: str) -> Optional[dict]:
    return _results.get(job_id)


def set_error(job_id: str, message: str):
    _steps[job_id] = "failed"
    _results[job_id] = {"error": message}


def clear(job_id: str):
    _steps.pop(job_id, None)
    _results.pop(job_id, None)
