"""
Reset a user's password by email.
Run: python reset_pg_pw.py your@email.com newpassword123
"""
import os
import sys
sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from dotenv import load_dotenv
load_dotenv()

from database.database import SessionLocal
from database.models import User
from utils.security import get_password_hash


def reset_password(email: str, new_password: str):
    db = SessionLocal()
    try:
        user = db.query(User).filter(User.email == email).first()
        if not user:
            print(f"No user found with email: {email}")
            return
        user.password_hash = get_password_hash(new_password)
        db.commit()
        print(f"Password reset successfully for {user.email}.")
    finally:
        db.close()


if __name__ == "__main__":
    if len(sys.argv) != 3:
        print("Usage: python reset_pg_pw.py <email> <new_password>")
        sys.exit(1)
    reset_password(sys.argv[1], sys.argv[2])
