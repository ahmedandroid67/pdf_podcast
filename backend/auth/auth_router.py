from fastapi import APIRouter, Depends, HTTPException, status, Request
from sqlalchemy.orm import Session

from auth.auth_service import authenticate_user, create_user, get_user_by_email, update_user_password
from auth.auth_dependencies import get_current_user
from database.database import get_db
from schemas.user_schema import TokenResponse, UserCreate, UserLogin, UserRead, UserChangePassword
from utils.security import create_access_token
from middleware.rate_limit import limiter
from authlib.integrations.starlette_client import OAuth
from starlette.responses import RedirectResponse
import os

router = APIRouter(prefix="/auth", tags=["auth"])

oauth = OAuth()
oauth.register(
    name='google',
    client_id=os.getenv('GOOGLE_CLIENT_ID'),
    client_secret=os.getenv('GOOGLE_CLIENT_SECRET'),
    server_metadata_url='https://accounts.google.com/.well-known/openid-configuration',
    client_kwargs={
        'scope': 'openid email profile',
        'timeout': 30.0
    }
)

@router.get("/google")
async def google_login(request: Request):
    redirect_uri = os.getenv('REDIRECT_URI')
    return await oauth.google.authorize_redirect(request, redirect_uri)

@router.get("/callback/google")
async def google_callback(request: Request, db: Session = Depends(get_db)):
    token = await oauth.google.authorize_access_token(request)
    user_info = token.get('userinfo')
    if not user_info:
        raise HTTPException(status_code=400, detail="Google authentication failed.")
    
    email = user_info.get('email')
    user = get_user_by_email(db, email)
    if not user:
        user = create_user(db, email, None) # password is None for social users
    
    access_token = create_access_token(str(user.id))
    frontend_url = os.getenv('FRONTEND_URL', 'http://localhost:5173')
    return RedirectResponse(url=f"{frontend_url}/oauth-callback?token={access_token}")


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


@router.post("/change-password")
def change_password(
    payload: UserChangePassword,
    current_user=Depends(get_current_user),
    db: Session = Depends(get_db)
):
    update_user_password(db, current_user, payload.new_password)
    return {"message": "Password updated successfully."}
