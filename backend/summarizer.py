import os
import google.generativeai as genai
import logging
import asyncio

logger = logging.getLogger(__name__)


def chunk_text(text: str, size: int = 3000, overlap: int = 200) -> list[str]:
    """Split long text into overlapping chunks for LLM processing."""
    if not text or not text.strip():
        return []
    text = text.strip()
    if len(text) <= size:
        return [text]
    chunks = []
    start = 0
    while start < len(text):
        end = min(start + size, len(text))
        if end < len(text):
            end = text.rfind(" ", start, end)
            if end <= start:
                end = min(start + size, len(text))
        chunks.append(text[start:end])
        start = max(end - overlap, start + 1)
    return chunks


LANG_MAP = {
    "en": "English",
    "fr": "French",
    "ar": "Arabic",
    "es": "Spanish",
    "de": "German",
    "pt": "Portuguese",
    "it": "Italian",
    "zh": "Chinese",
    "ja": "Japanese",
    "ko": "Korean",
    "ru": "Russian",
    "nl": "Dutch",
    "tr": "Turkish",
    "pl": "Polish",
    "hi": "Hindi",
    "vi": "Vietnamese",
}


async def summarize_text(text: str, lang: str = "en", mode: str = "podcast") -> tuple[str, int]:
    """Summarize or clean text using Google Gemini. Returns (summary, tokens_used)."""
    api_key = os.environ.get("GOOGLE_API_KEY")
    if not api_key:
        raise ValueError("GOOGLE_API_KEY not found in environment")

    genai.configure(api_key=api_key)
    model = genai.GenerativeModel("gemini-flash-latest")

    target_lang = LANG_MAP.get(lang, "English")

    if mode == "audiobook":
        prompt = f"""
You are a text cleaning assistant.
Please clean the following text extracted from a PDF to prepare it for a high-quality audiobook narration in {target_lang}.

RULES:
- REMOVE page numbers, repetitive headers, and footers.
- REMOVE broken words caused by line breaks.
- KEEP 100% of the actual content and meaning.
- DO NOT summarize. Use the original words as much as possible.
- Fix minor OCR or extraction errors.
- YOU MUST RESPOND IN {target_lang.upper()}.

TEXT TO CLEAN:
{text}
"""
    else:
        prompt = f"""
Please provide a comprehensive and engaging summary of the following text in {target_lang}.
Focus on the key themes, main arguments, and interesting facts.
The summary should be structured in a way that is easy to read and understand.
YOU MUST RESPOND IN {target_lang.upper()}.

TEXT TO SUMMARIZE:
{text}
"""

    try:
        loop = asyncio.get_event_loop()
        response = await loop.run_in_executor(None, lambda: model.generate_content(prompt))
        tokens = getattr(getattr(response, "usage_metadata", None), "total_token_count", 0) or 0
        return response.text, tokens
    except Exception as e:
        logger.error(f"Gemini Summarization failed: {e}")
        raise
