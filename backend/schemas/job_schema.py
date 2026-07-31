from datetime import datetime
from uuid import UUID

from pydantic import BaseModel, Field, computed_field


class JobRead(BaseModel):
    id: UUID
    pdf_name: str
    pdf_size: int
    summary_mode: str
    status: str
    created_at: datetime

    # Never expose the server-side filesystem path to clients.
    audio_path: str | None = Field(default=None, exclude=True)

    @computed_field
    @property
    def has_audio(self) -> bool:
        return bool(self.audio_path)

    class Config:
        from_attributes = True
