import logging
import shutil
from datetime import datetime, timedelta
from pathlib import Path

from sqlalchemy.orm import Session

from database.models import User, PodcastJob
from plans import PLANS, PlanType

logger = logging.getLogger(__name__)

STORAGE_ROOT = Path(__file__).resolve().parent.parent / "storage"

def cleanup_expired_jobs(db: Session):
    """
    Deletes jobs and their associated audio files that have exceeded 
    the retention period defined by the user's plan.
    """
    logger.info("Starting expired jobs cleanup...")
    now = datetime.utcnow()
    deleted_count = 0

    # 1. Get all users
    users = db.query(User).all()
    
    for user in users:
        # 2. Get limits for this user
        plan_type = PlanType(user.plan)
        limits = PLANS.get(plan_type, PLANS[PlanType.FREE])
        
        if limits.retention_days == -1:
            # Unlimited retention
            continue
            
        # 3. Find jobs older than retention period
        cutoff_date = now - timedelta(days=limits.retention_days)
        expired_jobs = db.query(PodcastJob).filter(
            PodcastJob.user_id == user.id,
            PodcastJob.created_at < cutoff_date
        ).all()
        
        for job in expired_jobs:
            try:
                # Delete the whole job storage directory — this removes both the
                # generated audio.mp3 AND the retained input.pdf, which the old
                # audio_path-only cleanup left behind forever.
                job_dir = STORAGE_ROOT / str(job.user_id) / str(job.id)
                if job_dir.exists():
                    shutil.rmtree(job_dir, ignore_errors=True)

                db.delete(job)
                deleted_count += 1
            except Exception as e:
                logger.error(f"Failed to cleanup job {job.id}: {e}")

    if deleted_count > 0:
        db.commit()
        logger.info(f"Cleanup finished. Deleted {deleted_count} expired jobs.")
    else:
        logger.info("No expired jobs found.")

def reset_stale_jobs(db: Session):
    """
    On startup, reset any jobs stuck in 'processing' (e.g. from a crash or restart).
    They will never complete, so mark them failed so users can see what happened.
    """
    from database.models import JobStatus
    stale = db.query(PodcastJob).filter(PodcastJob.status == JobStatus.processing).all()
    if stale:
        for job in stale:
            job.status = JobStatus.failed
        db.commit()
        logger.info(f"Reset {len(stale)} stale processing job(s) to failed on startup.")
    else:
        logger.info("No stale jobs found on startup.")


if __name__ == "__main__":
    # This allow running it as a standalone script
    from database.database import SessionLocal
    db = SessionLocal()
    try:
        cleanup_expired_jobs(db)
    finally:
        db.close()
