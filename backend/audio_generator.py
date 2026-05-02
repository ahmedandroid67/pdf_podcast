import os
import tempfile
import edge_tts
from pathlib import Path
import logging
import uuid

logger = logging.getLogger(__name__)

AUDIO_DIR = Path(__file__).parent / "static" / "audio"
AUDIO_DIR.mkdir(parents=True, exist_ok=True)

VOICES = {
    "en": "en-US-AndrewNeural",
    "fr": "fr-FR-HenriNeural",
    "ar": "ar-EG-ShakirNeural",
    "es": "es-ES-AlvaroNeural",
    "de": "de-DE-ConradNeural",
    "pt": "pt-BR-AntonioNeural",
    "it": "it-IT-DiegoNeural",
    "zh": "zh-CN-YunxiNeural",
    "ja": "ja-JP-KeitaNeural",
    "ko": "ko-KR-InJoonNeural",
    "ru": "ru-RU-DmitryNeural",
    "nl": "nl-NL-MaartenNeural",
    "tr": "tr-TR-AhmetNeural",
    "pl": "pl-PL-MarekNeural",
    "hi": "hi-IN-MadhurNeural",
    "vi": "vi-VN-NamMinhNeural",
}

DIALOGUE_VOICES = {
    "en": ("en-US-AndrewNeural",   "en-US-JennyNeural"),
    "fr": ("fr-FR-HenriNeural",    "fr-FR-DeniseNeural"),
    "ar": ("ar-EG-ShakirNeural",   "ar-EG-SalmaNeural"),
    "es": ("es-ES-AlvaroNeural",   "es-ES-ElviraNeural"),
    "de": ("de-DE-ConradNeural",   "de-DE-KatjaNeural"),
    "pt": ("pt-BR-AntonioNeural",  "pt-BR-FranciscaNeural"),
    "it": ("it-IT-DiegoNeural",    "it-IT-ElsaNeural"),
    "zh": ("zh-CN-YunxiNeural",    "zh-CN-XiaoxiaoNeural"),
    "ja": ("ja-JP-KeitaNeural",    "ja-JP-NanamiNeural"),
    "ko": ("ko-KR-InJoonNeural",   "ko-KR-SunHiNeural"),
    "ru": ("ru-RU-DmitryNeural",   "ru-RU-SvetlanaNeural"),
    "nl": ("nl-NL-MaartenNeural",  "nl-NL-ColetteNeural"),
    "tr": ("tr-TR-AhmetNeural",    "tr-TR-EmelNeural"),
    "pl": ("pl-PL-MarekNeural",    "pl-PL-ZofiaNeural"),
    "hi": ("hi-IN-MadhurNeural",   "hi-IN-SwaraNeural"),
    "vi": ("vi-VN-NamMinhNeural",  "vi-VN-HoaiMyNeural"),
}


def _clean(text: str) -> str:
    return text.replace("**", "").replace("__", "").replace("#", "").strip()


async def generate_audio(text: str, lang: str = "en", mode: str = "podcast") -> Path:
    """Generate audio. For dialogue mode, alternates between two voices."""
    if mode == "dialogue":
        return await generate_dialogue_audio(text, lang)

    voice = VOICES.get(lang, VOICES["en"])
    filename = f"{uuid.uuid4()}.mp3"
    filepath = AUDIO_DIR / filename
    try:
        communicate = edge_tts.Communicate(_clean(text), voice)
        await communicate.save(str(filepath))
        logger.info(f"Generated audio ({lang}, {mode}): {filepath}")
        return filepath
    except Exception as e:
        logger.error(f"Edge-TTS generation failed: {e}")
        raise


async def generate_dialogue_audio(script: str, lang: str = "en") -> Path:
    """
    Parse a HOST/GUEST script and generate audio with alternating voices.
    Lines must start with [HOST]: or [GUEST]:
    Falls back to single-voice if no markers are found.
    """
    host_voice, guest_voice = DIALOGUE_VOICES.get(lang, DIALOGUE_VOICES["en"])

    segments = []
    current_speaker = None
    current_lines = []

    for raw_line in script.split("\n"):
        line = raw_line.strip()
        if line.upper().startswith("[HOST]:"):
            if current_speaker and current_lines:
                segments.append((current_speaker, " ".join(current_lines)))
            current_speaker = "host"
            current_lines = [line[7:].strip()]
        elif line.upper().startswith("[GUEST]:"):
            if current_speaker and current_lines:
                segments.append((current_speaker, " ".join(current_lines)))
            current_speaker = "guest"
            current_lines = [line[8:].strip()]
        elif line and current_speaker:
            current_lines.append(line)

    if current_speaker and current_lines:
        segments.append((current_speaker, " ".join(current_lines)))

    if not segments:
        logger.warning("No HOST/GUEST markers found — falling back to single voice.")
        return await generate_audio(script, lang, mode="podcast")

    audio_parts = []
    for speaker, text in segments:
        text = _clean(text)
        if not text:
            continue
        voice = host_voice if speaker == "host" else guest_voice
        tmp = tempfile.NamedTemporaryFile(suffix=".mp3", delete=False)
        tmp_path = tmp.name
        tmp.close()
        try:
            communicate = edge_tts.Communicate(text, voice)
            await communicate.save(tmp_path)
            with open(tmp_path, "rb") as f:
                audio_parts.append(f.read())
        finally:
            try:
                os.unlink(tmp_path)
            except OSError:
                pass

    filename = f"{uuid.uuid4()}.mp3"
    filepath = AUDIO_DIR / filename
    with open(filepath, "wb") as out:
        for part in audio_parts:
            out.write(part)

    logger.info(f"Generated dialogue audio ({lang}) with {len(segments)} segments: {filepath}")
    return filepath


def delete_old_audio_files():
    for file in AUDIO_DIR.glob("*.mp3"):
        try:
            file.unlink()
        except Exception:
            pass


def list_audio_files():
    return [f.name for f in AUDIO_DIR.glob("*.mp3")]


def delete_audio_file(filename: str) -> bool:
    filepath = AUDIO_DIR / filename
    try:
        if filepath.exists():
            filepath.unlink()
            return True
    except Exception:
        pass
    return False
