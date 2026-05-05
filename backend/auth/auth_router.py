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


def _frontend_login_redirect(error_code: str) -> RedirectResponse:
    frontend_url = os.getenv("FRONTEND_URL", "http://localhost:5173").rstrip("/")
    return RedirectResponse(url=f"{frontend_url}/login?error={error_code}")


def _google_redirect_uri(request: Request) -> str:
    configured_redirect = os.getenv("REDIRECT_URI")
    if configured_redirect:
        return configured_redirect

    callback_url = str(request.url_for("google_callback"))
    forwarded_proto = request.headers.get("x-forwarded-proto")
    if forwarded_proto:
        callback_url = callback_url.replace(request.url.scheme, forwarded_proto, 1)
    return callback_url

@router.get("/google")
async def google_login(request: Request):
    if not os.getenv("GOOGLE_CLIENT_ID") or not os.getenv("GOOGLE_CLIENT_SECRET"):
        raise HTTPException(status_code=503, detail="Google OAuth is not configured.")

    redirect_uri = _google_redirect_uri(request)
    print(f"DEBUG: Redirecting to Google with redirect_uri={redirect_uri}")
    return await oauth.google.authorize_redirect(request, redirect_uri)

@router.get("/callback/google")
async def google_callback(request: Request, db: Session = Depends(get_db)):
    try:
        try:
            token = await oauth.google.authorize_access_token(request)
        except Exception:
            return _frontend_login_redirect("google_oauth_failed")

        user_info = token.get("userinfo")
        if not user_info:
            try:
                user_info = await oauth.google.userinfo(token=token)
            except Exception:
                return _frontend_login_redirect("google_userinfo_failed")

        email = (user_info or {}).get("email")
        if not email:
            return _frontend_login_redirect("google_email_missing")

        user = get_user_by_email(db, email)
        if not user:
            user = create_user(db, email, None)

        access_token = create_access_token(str(user.id))
    except Exception:
        return _frontend_login_redirect("auth_failed")

    frontend_url = os.getenv('FRONTEND_URL', 'http://localhost:5173').rstrip("/")
    return RedirectResponse(url=f"{frontend_url}/oauth-callback?token={access_token}")


@router.post("/register", response_model=UserRead, status_code=status.HTTP_201_CREATED)
@limiter.limit("3/minute")
def register(request: Request, payload: UserCreate, db: Session = Depends(get_db)):
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
