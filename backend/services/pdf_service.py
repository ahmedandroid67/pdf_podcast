from audio_generator import generate_audio
from pdf_parser import extract_text_from_bytes
from podcast_generator import generate_podcast_script
from summarizer import summarize_text


async def run_full_pipeline(pdf_bytes: bytes, lang: str = "en", mode: str = "podcast"):
    text, page_count, is_scanned = extract_text_from_bytes(pdf_bytes)
    if not text:
        msg = "Could not extract text."
        if is_scanned:
            msg = "Document appears to be a scanned image."
        raise ValueError(msg)

    summary = await summarize_text(text, lang, mode)
    script = await generate_podcast_script(summary, lang, mode)
    audio_path = await generate_audio(script, lang)
    return text, summary, script, audio_path, page_count, is_scanned
