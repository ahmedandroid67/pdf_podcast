"""
Admin Router — /api/admin/* endpoints.
All routes require a valid JWT token belonging to an admin user (is_admin=True).
"""
from uuid import UUID
from datetime import datetime, timedelta
from typing import List

from fastapi import APIRouter, Depends, HTTPException, status
from pydantic import BaseModel, EmailStr, Field
from sqlalchemy.orm import Session

from auth.auth_dependencies import require_admin
from auth.auth_service import create_user, get_user_by_email
from database.database import get_db
from database.models import PlanType, PodcastJob, User
from utils.security import get_password_hash

router = APIRouter(prefix="/api/admin", tags=["admin"])


# ---------------------------------------------------------------------------
# Pydantic schemas (admin-only)
# ---------------------------------------------------------------------------

class AdminUserRead(BaseModel):
    id: UUID
    email: str
    plan: str
    daily_quota: int
    used_today: int
    is_active: bool
    is_admin: bool
    tokens_used_today: int
    total_tokens_used: int
    created_at: datetime
    total_jobs: int

    class Config:
        from_attributes = True


class AdminUserCreate(BaseModel):
    email: EmailStr
    password: str = Field(min_length=8, max_length=128)
    plan: str = "free"
    daily_quota: int = 1
    is_admin: bool = False


class AdminUserUpdate(BaseModel):
    email: EmailStr | None = None
    plan: str | None = None
    daily_quota: int | None = None
    is_admin: bool | None = None


class AdminPasswordReset(BaseModel):
    password: str = Field(min_length=8, max_length=128)


class AdminStatsResponse(BaseModel):
    total_users: int
    active_users: int
    total_jobs: int
    completed_jobs: int
    total_gemini_uses: int  # sum of used_today across all users
    total_tokens_used_today: int
    remaining_tokens: int


# ---------------------------------------------------------------------------
# Helper
# ---------------------------------------------------------------------------

def _user_to_admin_read(user: User, db: Session) -> AdminUserRead:
    total_jobs = db.query(PodcastJob).filter(PodcastJob.user_id == str(user.id)).count()
    return AdminUserRead(
        id=user.id,
        email=user.email,
        plan=user.plan,
        daily_quota=user.daily_quota,
        used_today=user.used_today,
        is_active=user.is_active,
        is_admin=user.is_admin,
        tokens_used_today=user.tokens_used_today,
        total_tokens_used=user.total_tokens_used,
        created_at=user.created_at,
        total_jobs=total_jobs,
    )


# ---------------------------------------------------------------------------
# Routes
# ---------------------------------------------------------------------------

@router.post("/users/{user_id}/reset-password")
def admin_reset_password(
    user_id: str,
    payload: AdminPasswordReset,
    _admin=Depends(require_admin),
    db: Session = Depends(get_db),
):
    user = db.query(User).filter(User.id == user_id).first()
    if not user:
        raise HTTPException(status_code=404, detail="User not found.")
    
    user.password_hash = get_password_hash(payload.password)
    db.add(user)
    db.commit()
    return {"message": "Password reset successfully."}


@router.get("/stats", response_model=AdminStatsResponse)
def admin_stats(
    _admin=Depends(require_admin),
    db: Session = Depends(get_db),
):
    users = db.query(User).all()
    total_users = len(users)
    active_users = sum(1 for u in users if u.is_active)
    total_gemini_uses = sum(u.used_today for u in users)
    total_tokens_used_today = sum(u.tokens_used_today for u in users)
    
    # Standard Gemini 1.5 Flash Free Tier limit is ~1,500,000 tokens/day (based on 1.5k RPD)
    DAILY_TOKEN_LIMIT = 1500000
    remaining_tokens = max(0, DAILY_TOKEN_LIMIT - total_tokens_used_today)

    total_jobs = db.query(PodcastJob).count()
    from database.models import JobStatus
    completed_jobs = db.query(PodcastJob).filter(PodcastJob.status == JobStatus.completed).count()

    return AdminStatsResponse(
        total_users=total_users,
        active_users=active_users,
        total_jobs=total_jobs,
        completed_jobs=completed_jobs,
        total_gemini_uses=total_gemini_uses,
        total_tokens_used_today=total_tokens_used_today,
        remaining_tokens=remaining_tokens,
    )


@router.get("/users", response_model=list[AdminUserRead])
def admin_list_users(
    _admin=Depends(require_admin),
    db: Session = Depends(get_db),
):
    users = db.query(User).order_by(User.created_at.desc()).all()
    return [_user_to_admin_read(u, db) for u in users]


@router.post("/users", response_model=AdminUserRead, status_code=status.HTTP_201_CREATED)
def admin_create_user(
    payload: AdminUserCreate,
    _admin=Depends(require_admin),
    db: Session = Depends(get_db),
):
    if get_user_by_email(db, payload.email):
        raise HTTPException(status_code=400, detail="Email already registered.")
    user = create_user(db, payload.email, payload.password)
    # Apply extra fields
    if payload.plan:
        try:
            user.plan = PlanType(payload.plan)
        except ValueError:
            raise HTTPException(status_code=422, detail=f"Invalid plan: {payload.plan!r}")
    user.daily_quota = payload.daily_quota
    user.is_admin = payload.is_admin
    db.add(user)
    db.commit()
    db.refresh(user)
    return _user_to_admin_read(user, db)


@router.put("/users/{user_id}", response_model=AdminUserRead)
def admin_update_user(
    user_id: str,
    payload: AdminUserUpdate,
    _admin=Depends(require_admin),
    db: Session = Depends(get_db),
):
    user = db.query(User).filter(User.id == user_id).first()
    if not user:
        raise HTTPException(status_code=404, detail="User not found.")
    
    if payload.email and payload.email != user.email:
        if get_user_by_email(db, payload.email):
            raise HTTPException(status_code=400, detail="Email already registered by another user.")
        user.email = payload.email

    if payload.plan is not None:
        try:
            user.plan = PlanType(payload.plan)
        except ValueError:
            raise HTTPException(status_code=422, detail=f"Invalid plan: {payload.plan!r}")
    if payload.daily_quota is not None:
        user.daily_quota = payload.daily_quota
    if payload.is_admin is not None:
        user.is_admin = payload.is_admin
    db.add(user)
    db.commit()
    db.refresh(user)
    return _user_to_admin_read(user, db)


@router.put("/users/{user_id}/status", response_model=AdminUserRead)
def admin_toggle_status(
    user_id: str,
    admin=Depends(require_admin),
    db: Session = Depends(get_db),
):
    if str(admin.id) == user_id:
        raise HTTPException(status_code=400, detail="You cannot deactivate your own account.")
    user = db.query(User).filter(User.id == user_id).first()
    if not user:
        raise HTTPException(status_code=404, detail="User not found.")
    user.is_active = not user.is_active
    db.add(user)
    db.commit()
    db.refresh(user)
    return _user_to_admin_read(user, db)


@router.delete("/users/bulk/old", status_code=status.HTTP_200_OK)
def admin_bulk_delete_old_users(
    confirm: bool = False,
    _admin=Depends(require_admin),
    db: Session = Depends(get_db),
):
    """Delete all non-admin users created more than 60 days ago.

    Requires ?confirm=true — this is a destructive, irreversible bulk delete
    that does not distinguish inactive accounts from active paying customers.
    """
    if not confirm:
        raise HTTPException(
            status_code=400,
            detail="This permanently deletes all non-admin accounts older than 60 days. "
                   "Pass ?confirm=true to proceed.",
        )

    threshold = datetime.utcnow() - timedelta(days=60)
    old_users = db.query(User).filter(
        User.created_at < threshold,
        User.is_admin == False
    ).all()

    count = len(old_users)
    for u in old_users:
        db.delete(u)

    db.commit()
    return {"message": f"Successfully deleted {count} old user accounts."}


@router.delete("/users/{user_id}", status_code=status.HTTP_204_NO_CONTENT)
def admin_delete_user(
    user_id: str,
    admin=Depends(require_admin),
    db: Session = Depends(get_db),
):
    if str(admin.id) == user_id:
        raise HTTPException(status_code=400, detail="You cannot delete your own account.")
    user = db.query(User).filter(User.id == user_id).first()
    if not user:
        raise HTTPException(status_code=404, detail="User not found.")
    db.delete(user)
    db.commit()
