import os
import sys
sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from database.database import SessionLocal
from database.models import User, PlanType

def migrate_free_quota():
    db = SessionLocal()
    try:
        users = db.query(User).filter(User.plan == PlanType.free).all()
        count = 0
        for user in users:
            if user.daily_quota != 1:
                user.daily_quota = 1
                count += 1
        db.commit()
        print(f"Updated {count} existing free accounts to daily_quota = 1")
    finally:
        db.close()

if __name__ == "__main__":
    migrate_free_quota()
