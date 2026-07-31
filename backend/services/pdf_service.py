from dataclasses import dataclass
from pathlib import Path
from typing import Callable, Optional

from audio_generator import generate_audio
from pdf_parser import extract_text_from_bytes
from podcast_generator import generate_podcast_script
from summarizer import summarize_text


@dataclass
class PipelineResult:
    text: str
    summary: str
    script: str
    audio_path: Path
    page_count: int
    tokens_used: int


async def run_full_pipeline(
    pdf_bytes: bytes,
    lang: str = "en",
    mode: str = "podcast",
    on_step: Optional[Callable[[str], None]] = None,
) -> PipelineResult:
    if on_step:
        on_step("extracting")
    text, page_count, is_scanned = extract_text_from_bytes(pdf_bytes)
    if not text:
        msg = "Could not extract text."
        if is_scanned:
            msg = "Document appears to be a scanned image."
        raise ValueError(msg)

    if on_step:
        on_step("summarizing")
    summary, summary_tokens = await summarize_text(text, lang, mode)

    if on_step:
        on_step("scripting")
    script, script_tokens = await generate_podcast_script(summary, lang, mode)

    if on_step:
        on_step("audio")
    audio_path = await generate_audio(script, lang, mode)

    return PipelineResult(
        text=text,
        summary=summary,
        script=script,
        audio_path=audio_path,
        page_count=page_count,
        tokens_used=summary_tokens + script_tokens,
    )
