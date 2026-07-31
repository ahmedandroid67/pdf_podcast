from uuid import UUID

from pydantic import BaseModel, EmailStr, Field, model_validator


class UserCreate(BaseModel):
    email: EmailStr
    password: str = Field(min_length=8, max_length=128)


class UserLogin(BaseModel):
    email: EmailStr
    password: str


class UserChangePassword(BaseModel):
    new_password: str = Field(min_length=8, max_length=128)


class UserRead(BaseModel):
    id: UUID
    email: EmailStr
    plan: str
    daily_quota: int
    used_today: int
    monthly_token_limit: int
    tokens_used_month: int
    is_active: bool = True
    is_admin: bool = False

    @model_validator(mode="before")
    @classmethod
    def _use_effective_plan_limits(cls, obj):
        """Report the *actual* enforced limits from plans.py, not the stale
        `daily_quota` DB column (which nothing keeps in sync with the user's plan).
        """
        if isinstance(obj, dict):
            return obj

        from utils.plan_utils import get_user_plan_limits

        limits = get_user_plan_limits(obj)
        return {
            "id": obj.id,
            "email": obj.email,
            "plan": obj.plan,
            "daily_quota": limits.daily_docs,
            "used_today": obj.used_today,
            "monthly_token_limit": limits.monthly_token_limit,
            "tokens_used_month": obj.tokens_used_month,
            "is_active": obj.is_active,
            "is_admin": obj.is_admin,
        }

    class Config:
        from_attributes = True


class TokenResponse(BaseModel):
    access_token: str
    token_type: str = "bearer"
