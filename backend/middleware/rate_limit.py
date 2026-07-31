import ipaddress
import os

from slowapi import Limiter
from starlette.requests import Request

# Only trust X-Forwarded-For/X-Real-IP when the TCP connection actually comes
# from our own reverse proxy. Otherwise any internet client can set these
# headers themselves and rotate the "IP" the limiter sees on every request,
# bypassing login/register rate limits entirely.
#
# Defaults to RFC1918 + loopback since the proxy (Traefik) and backend sit on
# the same private Docker network. Override with a comma-separated list of
# exact IPs/CIDRs via TRUSTED_PROXY_IPS for a different topology.
_DEFAULT_TRUSTED_PROXIES = ["127.0.0.1/32", "10.0.0.0/8", "172.16.0.0/12", "192.168.0.0/16"]
_trusted_proxy_networks = [
    ipaddress.ip_network(cidr.strip(), strict=False)
    for cidr in os.getenv("TRUSTED_PROXY_IPS", ",".join(_DEFAULT_TRUSTED_PROXIES)).split(",")
    if cidr.strip()
]


def _is_trusted_proxy(ip: str) -> bool:
    try:
        addr = ipaddress.ip_address(ip)
    except ValueError:
        return False
    return any(addr in network for network in _trusted_proxy_networks)


def _get_ip_from_proxy(request: Request) -> str:
    """Get the real client IP, honoring X-Forwarded-For only from a trusted proxy."""
    client = request.client
    direct_ip = client.host if client else None

    if direct_ip and _is_trusted_proxy(direct_ip):
        forwarded = request.headers.get("X-Forwarded-For")
        if forwarded:
            return forwarded.split(",")[0].strip()
        real_ip = request.headers.get("X-Real-IP")
        if real_ip:
            return real_ip.strip()

    return direct_ip or "unknown"


limiter = Limiter(key_func=_get_ip_from_proxy)
