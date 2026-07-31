import os
import google.generativeai as genai
import logging
import asyncio

logger = logging.getLogger(__name__)

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


async def generate_podcast_script(summary: str, lang: str = "en", mode: str = "podcast") -> tuple[str, int]:
    """Generate a podcast narration script, a dialogue script, or return cleaned text for audiobook.

    Returns (script, tokens_used).
    """

    if mode == "audiobook":
        return summary, 0

    api_key = os.environ.get("GOOGLE_API_KEY")
    if not api_key:
        raise ValueError("GOOGLE_API_KEY not found in environment")

    genai.configure(api_key=api_key)
    model = genai.GenerativeModel("gemini-flash-latest")

    target_lang = LANG_MAP.get(lang, "English")

    if mode == "dialogue":
        system_prompt = f"""
You are an expert podcast scriptwriter specialising in interview-style conversations.
Convert the provided document summary into an engaging two-host dialogue in {target_lang}.

STRICT FORMAT RULES:
- Every line MUST start with either [HOST]: or [GUEST]:
- HOST asks thoughtful questions and sets context.
- GUEST answers with insights, elaborates on key points, and adds commentary.
- Alternate naturally between HOST and GUEST — aim for 6–12 exchanges.
- Start with HOST welcoming listeners and introducing the topic.
- End with HOST wrapping up and thanking GUEST.
- Do NOT include stage directions like [Music] or [Laughter].
- Keep it under 600 words total.
- YOU MUST RESPOND IN {target_lang.upper()}.

EXAMPLE FORMAT:
[HOST]: Welcome back to PodcastPDF! Today we're exploring an fascinating document...
[GUEST]: Thanks for having me! This document really stood out because...
[HOST]: So what would you say is the main takeaway?
[GUEST]: Great question. The key insight is...
"""
    else:
        system_prompt = f"""
You are an expert podcast scriptwriter.
Convert the document summary into a conversational, engaging narration script in {target_lang}.

GUIDELINES:
- Write for ONE narrator.
- Use a friendly, professional, yet enthusiastic tone.
- Start with a hook (e.g. "Welcome back to PodcastPDF... Today we're diving into..." or equivalent in {target_lang}).
- Use transitions like "Now, this is where it gets interesting..." or "But wait, there's more."
- End with a strong wrap-up.
- Do NOT include stage directions like [Music] or [Laughter]. Just the spoken text.
- Keep it under 500 words.
- YOU MUST RESPOND IN {target_lang.upper()}.
"""

    prompt = f"{system_prompt}\n\nSUMMARY TO CONVERT:\n{summary}"

    try:
        loop = asyncio.get_event_loop()
        response = await loop.run_in_executor(None, lambda: model.generate_content(prompt))
        tokens = getattr(getattr(response, "usage_metadata", None), "total_token_count", 0) or 0
        return response.text, tokens
    except Exception as e:
        logger.error(f"Gemini Script Generation failed: {e}")
        raise
