"""
tests/test_pdf_service.py
Regression test for the token-accounting bug: run_full_pipeline must report
real Gemini token usage (summary + script tokens), not page_count.
"""
import io
from pathlib import Path
from unittest.mock import AsyncMock, patch

import pymupdf
import pytest

from services.pdf_service import run_full_pipeline


def _make_pdf_bytes(text: str, pages: int = 1) -> bytes:
    doc = pymupdf.open()
    for _ in range(pages):
        page = doc.new_page()
        page.insert_text((50, 100), text)
    buf = io.BytesIO()
    doc.save(buf)
    return buf.getvalue()


@pytest.mark.anyio
async def test_pipeline_reports_real_token_usage(tmp_path):
    pdf_bytes = _make_pdf_bytes("Hello world", pages=7)
    fake_audio = tmp_path / "audio.mp3"
    fake_audio.write_bytes(b"\xff\xfb")

    with (
        patch("services.pdf_service.summarize_text", new_callable=AsyncMock) as mock_summarize,
        patch("services.pdf_service.generate_podcast_script", new_callable=AsyncMock) as mock_script,
        patch("services.pdf_service.generate_audio", new_callable=AsyncMock) as mock_audio,
    ):
        mock_summarize.return_value = ("A summary.", 123)
        mock_script.return_value = ("A script.", 456)
        mock_audio.return_value = fake_audio

        result = await run_full_pipeline(pdf_bytes, lang="en", mode="podcast")

    # tokens_used must be the sum of the two Gemini calls' real usage,
    # not the PDF's page_count (7) as it was before the fix.
    assert result.tokens_used == 123 + 456
    assert result.page_count == 7
    assert result.summary == "A summary."
    assert result.script == "A script."
    assert result.audio_path == fake_audio
