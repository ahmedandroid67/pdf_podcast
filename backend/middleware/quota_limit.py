from datetime import datetime, timedelta
from fastapi import Depends, HTTPException, status
from sqlalchemy.orm import Session

from auth.auth_dependencies import get_current_user
from database.database import get_db
from utils.plan_utils import get_user_plan_limits, check_token_limit


def _reset_quota_if_needed(user, db: Session):
    """Resets daily and monthly quotas if needed."""
    now = datetime.utcnow()
    changed = False

    # 1. Daily Reset (24h)
    if user.quota_reset_at + timedelta(hours=24) <= now:
        user.used_today = 0
        user.tokens_used_today = 0
        user.quota_reset_at = now
        changed = True

    # 2. Monthly Reset (30 days approx)
    # Using simple 30-day window or actual calendar month? Let's go with 30 days for consistency.
    if user.month_reset_at + timedelta(days=30) <= now:
        user.tokens_used_month = 0
        user.month_reset_at = now
        changed = True

    if changed:
        db.add(user)
        db.commit()
        db.refresh(user)


def check_user_quota(
    current_user=Depends(get_current_user),
    db: Session = Depends(get_db),
):
    """Enforces daily document and monthly token limits."""
    _reset_quota_if_needed(current_user, db)
    
    limits = get_user_plan_limits(current_user)
    
    # 1. Daily doc limit
    if current_user.used_today >= limits.daily_docs:
        raise HTTPException(
            status_code=status.HTTP_429_TOO_MANY_REQUESTS,
            detail=f"Daily document limit reached for {current_user.plan} plan.",
        )
    
    # 2. Monthly token limit
    is_ok, msg = check_token_limit(current_user)
    if not is_ok:
        raise HTTPException(
            status_code=status.HTTP_429_TOO_MANY_REQUESTS,
            detail=msg,
        )
        
    return current_user


def increment_usage(user, db: Session, tokens: int = 0):
    """Increments doc count and token usage (daily, monthly, total)."""
    user.used_today += 1
    user.tokens_used_today += tokens
    user.tokens_used_month += tokens
    user.total_tokens_used += tokens
    db.add(user)
    db.commit()
    db.refresh(user)
