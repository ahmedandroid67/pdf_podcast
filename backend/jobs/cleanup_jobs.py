import logging
from datetime import datetime, timedelta
from sqlalchemy.orm import Session
from database.models import User, PodcastJob
from plans import PLANS, PlanType
import os
import shutil

logger = logging.getLogger(__name__)

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
                # 4. Delete audio files from storage
                if job.audio_path:
                    # audio_path is usually relative or absolute
                    # If it's a directory (as in main.py storage logic), remove it
                    # main.py: storage_dir = Path(__file__).resolve().parent / "storage" / str(user.id) / str(job.id)
                    # For simplicity, we assume we need to clean up the directory
                    # But if audio_path is stored, we use that.
                    
                    # Ensure we have a valid path
                    if os.path.exists(job.audio_path):
                        if os.path.isfile(job.audio_path):
                            os.remove(job.audio_path)
                            # Also try to remove parent dir if empty
                            parent_dir = os.path.dirname(job.audio_path)
                            if parent_dir and os.path.exists(parent_dir) and not os.listdir(parent_dir):
                                os.rmdir(parent_dir)
                        elif os.path.isdir(job.audio_path):
                            shutil.rmtree(job.audio_path)
                
                # 5. Delete from DB
                db.delete(job)
                deleted_count += 1
            except Exception as e:
                logger.error(f"Failed to cleanup job {job.id}: {e}")

    if deleted_count > 0:
        db.commit()
        logger.info(f"Cleanup finished. Deleted {deleted_count} expired jobs.")
    else:
        logger.info("No expired jobs found.")

if __name__ == "__main__":
    # This allow running it as a standalone script
    from database.database import SessionLocal
    db = SessionLocal()
    try:
        cleanup_expired_jobs(db)
    finally:
        db.close()
