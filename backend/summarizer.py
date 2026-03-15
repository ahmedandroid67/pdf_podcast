import os
import google.generativeai as genai
import logging

logger = logging.getLogger(__name__)

async def summarize_text(text: str) -> str:
    """Summarize text using Google Gemini (Free Tier)."""
    api_key = os.environ.get("GOOGLE_API_KEY")
    if not api_key:
        raise ValueError("GOOGLE_API_KEY not found in environment")

    genai.configure(api_key=api_key)
    model = genai.GenerativeModel('gemini-1.5-flash')

    prompt = f"""
    Please provide a comprehensive and engaging summary of the following text. 
    Focus on the key themes, main arguments, and interesting facts.
    The summary should be structured in a way that is easy to read and understand.
    
    TEXT TO SUMMARIZE:
    {text}
    """

    try:
        response = model.generate_content(prompt)
        return response.text
    except Exception as e:
        logger.error(f"Gemini Summarization failed: {e}")
        raise
