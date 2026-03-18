from datetime import datetime
from pydantic import BaseModel


from uuid import UUID

class JobRead(BaseModel):
    id: UUID
    pdf_name: str
    pdf_size: int
    summary_mode: str
    audio_path: str | None
    status: str
    created_at: datetime

    class Config:
        from_attributes = True
