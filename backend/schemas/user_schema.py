from pydantic import BaseModel, EmailStr, Field


class UserCreate(BaseModel):
    email: EmailStr
    password: str = Field(min_length=8, max_length=128)


class UserLogin(BaseModel):
    email: EmailStr
    password: str


class UserChangePassword(BaseModel):
    new_password: str = Field(min_length=8, max_length=128)


from uuid import UUID

class UserRead(BaseModel):
    id: UUID
    email: EmailStr
    plan: str
    daily_quota: int
    used_today: int
    is_active: bool = True
    is_admin: bool = False

    class Config:
        from_attributes = True


class TokenResponse(BaseModel):
    access_token: str
    token_type: str = "bearer"
