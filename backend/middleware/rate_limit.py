from slowapi import Limiter
from starlette.requests import Request


def _get_ip_from_proxy(request: Request) -> str:
    """Get the real client IP, respecting X-Forwarded-For from reverse proxies."""
    forwarded = request.headers.get("X-Forwarded-For")
    if forwarded:
        return forwarded.split(",")[0].strip()
    real_ip = request.headers.get("X-Real-IP")
    if real_ip:
        return real_ip.strip()
    client = request.client
    return client.host if client else "unknown"


limiter = Limiter(key_func=_get_ip_from_proxy)
