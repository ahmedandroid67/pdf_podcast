from datetime import datetime, timedelta
from fastapi import Depends, HTTPException, status
from sqlalchemy.orm import Session

from auth.auth_dependencies import get_current_user
from database.database import get_db
from database.models import User
from utils.plan_utils import get_user_plan_limits, check_token_limit


def _reset_quota_if_needed(user, db: Session):
    """Resets daily and monthly quotas if needed."""
    now = datetime.utcnow()
    changed = False

    if user.quota_reset_at + timedelta(hours=24) <= now:
        user.used_today = 0
        user.tokens_used_today = 0
        user.quota_reset_at = now
        changed = True

    if user.month_reset_at + timedelta(days=30) <= now:
        user.tokens_used_month = 0
        user.month_reset_at = now
        changed = True

    if changed:
        db.add(user)
        db.flush()


def check_user_quota(
    current_user=Depends(get_current_user),
    db: Session = Depends(get_db),
):
    """Enforces daily document and monthly token limits.

    Re-queries the user with FOR UPDATE to serialize concurrent requests."""
    user = db.query(User).filter(User.id == current_user.id).with_for_update().first()
    if not user:
        raise HTTPException(status_code=status.HTTP_401_UNAUTHORIZED)
    _reset_quota_if_needed(user, db)

    limits = get_user_plan_limits(user)

    if user.used_today >= limits.daily_docs:
        raise HTTPException(
            status_code=status.HTTP_429_TOO_MANY_REQUESTS,
            detail=f"Daily document limit reached for {user.plan} plan.",
        )

    is_ok, msg = check_token_limit(user)
    if not is_ok:
        raise HTTPException(
            status_code=status.HTTP_429_TOO_MANY_REQUESTS,
            detail=msg,
        )

    # Reserve the daily document slot now, still inside the FOR UPDATE lock,
    # so concurrent requests can't all pass the check above before any of
    # them commits (the job itself may still fail later; we don't refund).
    user.used_today += 1
    db.add(user)
    db.commit()
    db.refresh(user)

    return user


def increment_usage(user, db: Session, tokens: int = 0):
    """Records token usage (daily, monthly, total) for a completed job.

    Document-count quota is reserved up front by check_user_quota.
    """
    user.tokens_used_today += tokens
    user.tokens_used_month += tokens
    user.total_tokens_used += tokens
    db.add(user)
    db.commit()
    db.refresh(user)
