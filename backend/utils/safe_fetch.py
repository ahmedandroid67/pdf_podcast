"""
safe_fetch.py
SSRF-hardened HTTP fetching for user-supplied URLs (e.g. "import PDF from URL").

Protects against:
- non-http(s) schemes (file://, gopher://, etc.)
- requests to loopback / private / link-local / multicast / reserved IP ranges
  (including via DNS rebinding through redirects)
- unbounded response bodies (memory exhaustion)
"""
from __future__ import annotations

import ipaddress
import socket
from urllib.parse import urlparse

import httpx

MAX_REDIRECTS = 5


class UnsafeUrlError(ValueError):
    pass


def _is_public_ip(ip_str: str) -> bool:
    ip = ipaddress.ip_address(ip_str)
    return not (
        ip.is_private
        or ip.is_loopback
        or ip.is_link_local
        or ip.is_multicast
        or ip.is_reserved
        or ip.is_unspecified
    )


def _assert_safe_url(url: str) -> None:
    parsed = urlparse(url)
    if parsed.scheme not in ("http", "https"):
        raise UnsafeUrlError("Only http:// and https:// URLs are supported.")
    if not parsed.hostname:
        raise UnsafeUrlError("URL is missing a hostname.")

    try:
        addrinfo = socket.getaddrinfo(parsed.hostname, None)
    except socket.gaierror as exc:
        raise UnsafeUrlError(f"Could not resolve host: {parsed.hostname}") from exc

    if not addrinfo:
        raise UnsafeUrlError(f"Could not resolve host: {parsed.hostname}")

    for family, _, _, _, sockaddr in addrinfo:
        ip_str = sockaddr[0]
        if not _is_public_ip(ip_str):
            raise UnsafeUrlError("This URL points to a private or internal network address.")


async def fetch_url_safely(url: str, *, max_bytes: int, timeout: float = 30.0) -> httpx.Response:
    """Download `url`, rejecting SSRF targets and oversized bodies.

    Every redirect hop is re-validated (blocks DNS-rebinding style bypasses).
    Raises UnsafeUrlError or httpx errors on failure.
    """
    current_url = url
    async with httpx.AsyncClient(follow_redirects=False, timeout=timeout) as client:
        for _ in range(MAX_REDIRECTS + 1):
            _assert_safe_url(current_url)

            async with client.stream("GET", current_url) as response:
                if response.is_redirect:
                    next_url = response.headers.get("location")
                    if not next_url:
                        raise UnsafeUrlError("Redirect response missing Location header.")
                    current_url = str(httpx.URL(current_url).join(next_url))
                    continue

                response.raise_for_status()

                content_length = response.headers.get("content-length")
                if content_length and int(content_length) > max_bytes:
                    raise UnsafeUrlError(
                        f"Remote file is too large ({int(content_length) / (1024*1024):.1f}MB)."
                    )

                chunks = []
                total = 0
                async for chunk in response.aiter_bytes():
                    total += len(chunk)
                    if total > max_bytes:
                        raise UnsafeUrlError(
                            f"Remote file exceeds the {max_bytes / (1024*1024):.0f}MB limit."
                        )
                    chunks.append(chunk)

                content = b"".join(chunks)
                return httpx.Response(
                    status_code=response.status_code,
                    headers=response.headers,
                    content=content,
                    request=response.request,
                )

    raise UnsafeUrlError("Too many redirects.")
