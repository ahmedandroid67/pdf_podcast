from fastapi import APIRouter, Depends, HTTPException, status, Request
from sqlalchemy.orm import Session

from auth.auth_service import authenticate_user, create_user, get_user_by_email
from auth.auth_dependencies import get_current_user
from database.database import get_db
from schemas.user_schema import TokenResponse, UserCreate, UserLogin, UserRead
from utils.security import create_access_token
from middleware.rate_limit import limiter

router = APIRouter(prefix="/auth", tags=["auth"])


@router.post("/register", response_model=UserRead, status_code=status.HTTP_201_CREATED)
def register(payload: UserCreate, db: Session = Depends(get_db)):
    if get_user_by_email(db, payload.email):
        raise HTTPException(status_code=400, detail="Email is already registered.")
    user = create_user(db, payload.email, payload.password)
    return user


@router.post("/login", response_model=TokenResponse)
@limiter.limit("5/minute")
def login(request: Request, payload: UserLogin, db: Session = Depends(get_db)):
    user = authenticate_user(db, payload.email, payload.password)
    if not user:
        raise HTTPException(status_code=401, detail="Invalid email or password.")
    token = create_access_token(str(user.id))
    return TokenResponse(access_token=token)


@router.get("/me", response_model=UserRead)
def me(current_user=Depends(get_current_user)):
    return current_user
