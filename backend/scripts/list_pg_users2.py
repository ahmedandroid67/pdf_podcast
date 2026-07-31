import os
import sys
sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from dotenv import load_dotenv
load_dotenv()
print("DATABASE_URL:", os.getenv("DATABASE_URL"))
from database.database import SessionLocal
from database.models import User
db = SessionLocal()
users = db.query(User).all()
print("Number of users:", len(users))
for u in users:
    print(f"User: {u.email}, ID: {u.id}, Active: {u.is_active}")
db.close()
