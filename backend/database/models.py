import enum
import uuid
from datetime import datetime

from sqlalchemy import Boolean, Column, DateTime, Enum, ForeignKey, Integer, String, Text
from sqlalchemy.orm import declarative_base, relationship
from sqlalchemy.types import TypeDecorator, CHAR


Base = declarative_base()


class GUID(TypeDecorator):
    impl = CHAR
    cache_ok = True

    def load_dialect_impl(self, dialect):
        if dialect.name == "postgresql":
            from sqlalchemy.dialects.postgresql import UUID

            return dialect.type_descriptor(UUID(as_uuid=True))
        return dialect.type_descriptor(CHAR(36))

    def process_bind_param(self, value, dialect):
        if value is None:
            return value
        if isinstance(value, uuid.UUID):
            return str(value)
        return str(uuid.UUID(value))

    def process_result_value(self, value, dialect):
        if value is None:
            return value
        if isinstance(value, uuid.UUID):
            return value
        return uuid.UUID(value)


class PlanType(str, enum.Enum):
    free = "free"
    basic = "basic"
    pro = "pro"
    business = "business"


class User(Base):
    __tablename__ = "users"

    id = Column(GUID(), primary_key=True, default=uuid.uuid4)
    email = Column(String(255), unique=True, index=True, nullable=False)
    password_hash = Column(String(255), nullable=True)
    plan = Column(Enum(PlanType), default=PlanType.free, nullable=False)
    daily_quota = Column(Integer, default=1, nullable=False)
    used_today = Column(Integer, default=0, nullable=False)
    tokens_used_today = Column(Integer, default=0, nullable=False)
    tokens_used_month = Column(Integer, default=0, nullable=False)  # Yearly or Monthly tracking
    total_tokens_used = Column(Integer, default=0, nullable=False)
    quota_reset_at = Column(DateTime, default=datetime.utcnow, nullable=False)
    month_reset_at = Column(DateTime, default=datetime.utcnow, nullable=False)
    subscription_status = Column(String(32), default="active", nullable=False)
    subscription_end_at = Column(DateTime, nullable=True)
    created_at = Column(DateTime, default=datetime.utcnow, nullable=False)
    is_active = Column(Boolean, default=True, nullable=False)
    is_admin = Column(Boolean, default=False, nullable=False)

    jobs = relationship("PodcastJob", back_populates="user", cascade="all, delete-orphan")


class JobStatus(str, enum.Enum):
    queued = "queued"
    processing = "processing"
    completed = "completed"
    failed = "failed"


class PodcastJob(Base):
    __tablename__ = "podcast_jobs"

    id = Column(GUID(), primary_key=True, default=uuid.uuid4)
    user_id = Column(GUID(), ForeignKey("users.id"), nullable=False, index=True)
    pdf_name = Column(String(255), nullable=False)
    pdf_size = Column(Integer, nullable=False)
    summary_mode = Column(String(32), default="podcast", nullable=False)
    audio_path = Column(Text, nullable=True)
    tokens_used = Column(Integer, default=0, nullable=False)
    status = Column(Enum(JobStatus), default=JobStatus.queued, nullable=False)
    created_at = Column(DateTime, default=datetime.utcnow, nullable=False)

    user = relationship("User", back_populates="jobs")
