"""
tests/test_pdf_parser.py
Unit tests for pdf_parser.py
"""

import io
import pytest
import pymupdf
import sys
import os

# Ensure parent (backend) is on the path
sys.path.insert(0, os.path.dirname(os.path.dirname(__file__)))

from pdf_parser import extract_text_from_bytes


def _make_pdf_bytes(text: str) -> bytes:
    """Create a minimal in-memory PDF containing the given text."""
    doc = pymupdf.open()
    page = doc.new_page()
    page.insert_text((50, 100), text)
    buf = io.BytesIO()
    doc.save(buf)
    return buf.getvalue()


class TestExtractTextFromBytes:
    def test_extracts_text_from_valid_pdf(self):
        pdf = _make_pdf_bytes("Hello, world!")
        text, page_count, is_scanned = extract_text_from_bytes(pdf)
        assert "Hello" in text
        assert page_count == 1
        assert is_scanned is False

    def test_returns_empty_for_blank_page(self):
        doc = pymupdf.open()
        doc.new_page()
        buf = io.BytesIO()
        doc.save(buf)
        text, page_count, is_scanned = extract_text_from_bytes(buf.getvalue())
        assert text.strip() == ""
        assert page_count == 1

    def test_multipage_pdf(self):
        doc = pymupdf.open()
        for i in range(3):
            page = doc.new_page()
            page.insert_text((50, 100), f"Page {i + 1}")
        buf = io.BytesIO()
        doc.save(buf)
        text, page_count, is_scanned = extract_text_from_bytes(buf.getvalue())
        assert "Page 1" in text
        assert "Page 3" in text
        assert page_count == 3

    def test_invalid_bytes_returns_empty_result(self):
        # extract_text_from_bytes deliberately swallows PyMuPDF errors and
        # returns an empty result rather than raising, so callers can treat
        # "no text" uniformly regardless of cause.
        text, page_count, is_scanned = extract_text_from_bytes(b"not a pdf")
        assert text == ""
        assert page_count == 0
