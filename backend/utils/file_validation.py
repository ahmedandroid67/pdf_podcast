from __future__ import annotations

import io
import logging
from typing import Tuple

import pymupdf

logger = logging.getLogger(__name__)


class PdfValidationError(ValueError):
    pass


def validate_pdf_bytes(
    pdf_bytes: bytes,
    *,
    max_size_mb: int = 20,
    max_pages: int = 100,
    require_text: bool = True,
) -> Tuple[int, bool]:
    if not pdf_bytes:
        raise PdfValidationError("Uploaded file is empty.")

    max_bytes = max_size_mb * 1024 * 1024
    if len(pdf_bytes) > max_bytes:
        raise PdfValidationError(f"PDF exceeds {max_size_mb}MB limit.")

    try:
        with pymupdf.open(stream=io.BytesIO(pdf_bytes), filetype="pdf") as doc:
            if doc.is_encrypted:
                raise PdfValidationError("Encrypted PDFs are not supported.")

            page_count = doc.page_count
            if page_count > max_pages:
                raise PdfValidationError(f"PDF exceeds {max_pages} pages limit.")

            if require_text:
                has_text = False
                for page in doc:
                    if page.get_text("text").strip():
                        has_text = True
                        break
                if not has_text:
                    raise PdfValidationError("No readable text found in the PDF.")

            return page_count, doc.is_encrypted
    except PdfValidationError:
        raise
    except Exception as exc:
        logger.exception("Failed to validate PDF")
        raise PdfValidationError(f"Could not validate PDF: {exc}") from exc
