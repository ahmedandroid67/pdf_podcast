import os
import google.generativeai as genai
import logging
import asyncio

logger = logging.getLogger(__name__)

async def generate_podcast_script(summary: str, lang: str = "en") -> str:
    """Generate a podcast narration script using Google Gemini."""
    api_key = os.environ.get("GOOGLE_API_KEY")
    if not api_key:
        raise ValueError("GOOGLE_API_KEY not found in environment")

    genai.configure(api_key=api_key)
    model = genai.GenerativeModel('gemini-flash-latest')

    lang_map = {
        "en": "English",
        "fr": "French",
        "ar": "Arabic"
    }
    target_lang = lang_map.get(lang, "English")

    system_prompt = f"""
    You are an expert podcast scriptwriter. 
    Your goal is to convert a document summary into a conversational, engaging, and lively narration script in {target_lang}.
    
    GUIDELINES:
    - Write for ONE narrator.
    - Use a friendly, professional, yet enthusiastic tone.
    - Start with a hook ("Welcome back to the PodcastPDF... Today we're diving into..." or equivalent in {target_lang}).
    - Use transitions like "Now, this is where it gets interesting..." or "But wait, there's more."
    - End with a strong wrap-up.
    - Do NOT include stage directions like [Music] or [Laughter]. Just the spoken text.
    - Keep it under 500 words.
    - YOU MUST RESPOND IN {target_lang.upper()}.
    """

    prompt = f"{system_prompt}\n\nSUMMARY TO CONVERT:\n{summary}"

    try:
        # Run the synchronous generate_content in a separate thread to avoid blocking
        loop = asyncio.get_event_loop()
        response = await loop.run_in_executor(None, lambda: model.generate_content(prompt))
        return response.text
    except Exception as e:
        logger.error(f"Gemini Script Generation failed: {e}")
        raise
