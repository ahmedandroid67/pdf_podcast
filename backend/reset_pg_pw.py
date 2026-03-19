from dotenv import load_dotenv
load_dotenv()

from database.database import SessionLocal
from database.models import User
from utils.security import get_password_hash

db = SessionLocal()
try:
    user = db.query(User).filter(User.email == 'k.ahmed.lara@gmail.com').first()
    if user:
        new_password = "password123"
        user.password_hash = get_password_hash(new_password)
        db.commit()
        print(f"Password reset successfully for {user.email} in PG to: {new_password}")
    else:
        print("User not found in Postgres.")
finally:
    db.close()
