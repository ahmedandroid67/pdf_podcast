"""
pdf_parser.py
Extracts text from PDF files (file path or bytes).
"""

import io
import logging
from typing import Union

import httpx
import pymupdf  # PyMuPDF

logger = logging.getLogger(__name__)


def extract_text_from_bytes(pdf_bytes: bytes) -> tuple[str, int, bool]:
    """Extract all text and return (text, page_count, is_scanned_likely)."""
    text_parts: list[str] = []
    has_images = False
    try:
        with pymupdf.open(stream=pdf_bytes, filetype="pdf") as doc:
            page_count = doc.page_count
            for page in doc:
                # Check for text
                page_text = page.get_text("text")
                if page_text.strip():
                    text_parts.append(page_text)
                
                # Check for images if no text found yet
                if not has_images and len(page.get_images()) > 0:
                    has_images = True
            
            full_text = "\n".join(text_parts).strip()
            is_scanned = has_images and not full_text
            return full_text, page_count, is_scanned
    except Exception as e:
        logger.error("Failed to open PDF: %s", str(e))
        return "", 0, False


async def extract_text_from_url(url: str) -> tuple[str, int, bool]:
    """Download a PDF from a URL and extract its text."""
    logger.info("Downloading PDF from URL: %s", url)
    async with httpx.AsyncClient(follow_redirects=True, timeout=30) as client:
        response = await client.get(url)
        response.raise_for_status()

    content_type = response.headers.get("content-type", "")
    if "pdf" not in content_type and not url.lower().endswith(".pdf"):
        logger.warning("URL may not be a PDF. Content-Type: %s", content_type)

    return extract_text_from_bytes(response.content)
