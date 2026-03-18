import os
import google.generativeai as genai
import logging
import asyncio

logger = logging.getLogger(__name__)

async def summarize_text(text: str, lang: str = "en", mode: str = "podcast") -> str:
    """Summarize or clean text using Google Gemini."""
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
        # Run the synchronous generate_content in a separate thread to avoid blocking
        loop = asyncio.get_event_loop()
        response = await loop.run_in_executor(None, lambda: model.generate_content(prompt))
        tokens = response.usage_metadata.total_token_count
        return response.text, tokens
    except Exception as e:
        logger.error(f"Gemini Summarization failed: {e}")
        raise
