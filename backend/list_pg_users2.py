from dotenv import load_dotenv
load_dotenv()
import os
print("DATABASE_URL:", os.getenv("DATABASE_URL"))
from database.database import SessionLocal
from database.models import User
db = SessionLocal()
users = db.query(User).all()
print("Number of users:", len(users))
for u in users:
    print(f"User: {u.email}, ID: {u.id}, Active: {u.is_active}")
db.close()
