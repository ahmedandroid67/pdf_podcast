from sqlalchemy.orm import Session

from database.models import User
from utils.security import get_password_hash, verify_password


def get_user_by_email(db: Session, email: str) -> User | None:
    return db.query(User).filter(User.email == email).first()


def get_user_by_id(db: Session, user_id: str) -> User | None:
    return db.query(User).filter(User.id == user_id).first()


def create_user(db: Session, email: str, password: str | None) -> User:
    password_hash = get_password_hash(password) if password else None
    user = User(email=email, password_hash=password_hash)
    db.add(user)
    db.commit()
    db.refresh(user)
    return user


def authenticate_user(db: Session, email: str, password: str) -> User | None:
    user = get_user_by_email(db, email.strip().lower())
    if not user:
        return None
    if user.password_hash is None:
        return None
    if not verify_password(password.strip(), user.password_hash):
        return None
    return user


def update_user_password(db: Session, user: User, new_password: str) -> User:
    user.password_hash = get_password_hash(new_password)
    db.commit()
    db.refresh(user)
    return user
