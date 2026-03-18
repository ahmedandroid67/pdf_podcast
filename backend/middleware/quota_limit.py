from datetime import datetime, timedelta

from fastapi import Depends, HTTPException, status
from sqlalchemy.orm import Session

from auth.auth_dependencies import get_current_user
from database.database import get_db


def _reset_quota_if_needed(user, db: Session):
    now = datetime.utcnow()
    if user.quota_reset_at + timedelta(hours=24) <= now:
        user.used_today = 0
        user.tokens_used_today = 0
        user.quota_reset_at = now
        db.add(user)
        db.commit()
        db.refresh(user)


def check_user_quota(
    current_user=Depends(get_current_user),
    db: Session = Depends(get_db),
):
    _reset_quota_if_needed(current_user, db)
    if current_user.used_today >= current_user.daily_quota:
        raise HTTPException(
            status_code=status.HTTP_429_TOO_MANY_REQUESTS,
            detail="error_quota_exceeded",
        )
    return current_user


def increment_usage(user, db: Session, tokens: int = 0):
    user.used_today += 1
    user.tokens_used_today += tokens
    user.total_tokens_used += tokens
    db.add(user)
    db.commit()
    db.refresh(user)
