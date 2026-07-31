import os
from datetime import datetime, timedelta, timezone
from jose import jwt
import bcrypt

_DEBUG = os.getenv("DEBUG", "false").lower() == "true"

JWT_SECRET_KEY = os.getenv("JWT_SECRET_KEY")
if not JWT_SECRET_KEY:
    if _DEBUG:
        JWT_SECRET_KEY = "dev-insecure-secret"
    else:
        raise RuntimeError(
            "JWT_SECRET_KEY environment variable must be set. "
            "Generate one with: openssl rand -hex 32"
        )

JWT_ALGORITHM = os.getenv("JWT_ALGORITHM", "HS256")
JWT_EXPIRES_MINUTES = int(os.getenv("JWT_EXPIRES_MINUTES", "120"))

def verify_password(plain_password: str, hashed_password: str) -> bool:
    try:
        return bcrypt.checkpw(
            plain_password.encode('utf-8'),
            hashed_password.encode('utf-8')
        )
    except ValueError:
        return False

def get_password_hash(password: str) -> str:
    salt = bcrypt.gensalt()
    return bcrypt.hashpw(password.encode('utf-8'), salt).decode('utf-8')

def create_access_token(user_id: str) -> str:
    expires = datetime.now(timezone.utc) + timedelta(minutes=JWT_EXPIRES_MINUTES)
    payload = {"user_id": str(user_id), "exp": expires}
    return jwt.encode(payload, JWT_SECRET_KEY, algorithm=JWT_ALGORITHM)
