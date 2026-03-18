from pathlib import Path

from sqlalchemy.orm import Session

from database.models import JobStatus, PodcastJob


def create_job(
    db: Session,
    user_id: str,
    pdf_name: str,
    pdf_size: int,
    summary_mode: str,
) -> PodcastJob:
    job = PodcastJob(
        user_id=user_id,
        pdf_name=pdf_name,
        pdf_size=pdf_size,
        summary_mode=summary_mode,
        status=JobStatus.processing,
    )
    db.add(job)
    db.commit()
    db.refresh(job)
    return job


def update_job_completed(db: Session, job: PodcastJob, audio_path: Path):
    job.audio_path = str(audio_path)
    job.status = JobStatus.completed
    db.add(job)
    db.commit()
    db.refresh(job)
    return job


def update_job_failed(db: Session, job: PodcastJob):
    job.status = JobStatus.failed
    db.add(job)
    db.commit()
    db.refresh(job)
    return job


def get_jobs_for_user(db: Session, user_id: str):
    return (
        db.query(PodcastJob)
        .filter(PodcastJob.user_id == user_id)
        .order_by(PodcastJob.created_at.desc())
        .all()
    )


def get_job_for_user(db: Session, user_id: str, job_id: str):
    return (
        db.query(PodcastJob)
        .filter(PodcastJob.user_id == user_id, PodcastJob.id == job_id)
        .first()
    )
