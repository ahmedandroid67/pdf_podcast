import os
import google.generativeai as genai
import logging

logger = logging.getLogger(__name__)

async def generate_podcast_script(summary: str) -> str:
    """Generate a podcast narration script using Google Gemini."""
    api_key = os.environ.get("GOOGLE_API_KEY")
    if not api_key:
        raise ValueError("GOOGLE_API_KEY not found in environment")

    genai.configure(api_key=api_key)
    model = genai.GenerativeModel('gemini-1.5-flash')

    system_prompt = """
    You are an expert podcast scriptwriter. 
    Your goal is to convert a document summary into a conversational, engaging, and lively narration script.
    
    GUIDELINES:
    - Write for ONE narrator.
    - Use a friendly, professional, yet enthusiastic tone.
    - Start with a hook ("Welcome back to the PodcastPDF... Today we're diving into...")
    - Use transitions like "Now, this is where it gets interesting..." or "But wait, there's more."
    - End with a strong wrap-up.
    - Do NOT include stage directions like [Music] or [Laughter]. Just the spoken text.
    - Keep it under 500 words.
    """

    prompt = f"{system_prompt}\n\nSUMMARY TO CONVERT:\n{summary}"

    try:
        response = model.generate_content(prompt)
        return response.text
    except Exception as e:
        logger.error(f"Gemini Script Generation failed: {e}")
        raise
