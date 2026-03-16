import os
import edge_tts
from pathlib import Path
import logging
import uuid

logger = logging.getLogger(__name__)

# Directory to store audio files
AUDIO_DIR = Path(__file__).parent / "static" / "audio"
AUDIO_DIR.mkdir(parents=True, exist_ok=True)

async def generate_audio(text: str, lang: str = "en") -> Path:
    """
    Generate audio from text using Microsoft Edge TTS (Free).
    """
    voices = {
        "en": "en-US-AndrewNeural",
        "fr": "fr-FR-HenriNeural",
        "ar": "ar-EG-ShakirNeural"
    }
    voice = voices.get(lang, "en-US-AndrewNeural")
    
    filename = f"{uuid.uuid4()}.mp3"
    filepath = AUDIO_DIR / filename
    
    try:
        # Clean text: remove characters that might break TTS
        clean_text = text.replace("**", "").replace("__", "").replace("#", "")
        
        communicate = edge_tts.Communicate(clean_text, voice)
        await communicate.save(str(filepath))
        
        logger.info(f"Generated free audio ({lang}): {filepath}")
        return filepath
    except Exception as e:
        logger.error(f"Edge-TTS generation failed: {e}")
        raise

def delete_old_audio_files():
    """Cleanup utility to remove generated audio files."""
    for file in AUDIO_DIR.glob("*.mp3"):
        try:
            file.unlink()
        except Exception:
            pass

def list_audio_files():
    """List all generated audio files."""
    return [f.name for f in AUDIO_DIR.glob("*.mp3")]

def delete_audio_file(filename: str) -> bool:
    """Delete a specific audio file by name."""
    filepath = AUDIO_DIR / filename
    try:
        if filepath.exists():
            filepath.unlink()
            return True
    except Exception:
        pass
    return False
