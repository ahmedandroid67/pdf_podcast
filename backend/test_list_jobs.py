from database.database import SessionLocal
from database.models import PodcastJob, User

def test():
    db = SessionLocal()
    try:
        users = db.query(User).all()
        print(f"Total Users: {len(users)}")
        jobs = db.query(PodcastJob).all()
        print(f"Total Jobs: {len(jobs)}")
        for job in jobs:
            print(f"Job: {job.id}, User: {job.user_id}, Status: {job.status}")
    finally:
        db.close()

if __name__ == "__main__":
    test()
