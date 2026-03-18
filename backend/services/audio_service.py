from pathlib import Path
import shutil


def store_audio_file(source_path: Path, destination_dir: Path) -> Path:
    destination_dir.mkdir(parents=True, exist_ok=True)
    destination_path = destination_dir / "audio.mp3"
    shutil.move(str(source_path), str(destination_path))
    return destination_path
