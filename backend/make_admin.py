"""
Quick script to grant admin rights to a user by email.
Run: python make_admin.py your@email.com
"""
import sys
import os
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))

from database.database import SessionLocal
from database.models import User

def make_admin(email: str):
    db = SessionLocal()
    try:
        user = db.query(User).filter(User.email == email).first()
        if not user:
            print(f"No user found with email: {email}")
            return
        user.is_admin = True
        user.is_active = True
        db.add(user)
        db.commit()
        print(f"✅ User '{email}' is now an admin!")
    finally:
        db.close()

if __name__ == "__main__":
    if len(sys.argv) != 2:
        print("Usage: python make_admin.py <email>")
        sys.exit(1)
    make_admin(sys.argv[1])
