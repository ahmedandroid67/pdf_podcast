from pydantic import BaseModel
from enum import Enum

class PlanType(str, Enum):
    FREE = "free"
    BASIC = "basic"
    PRO = "pro"
    BUSINESS = "business"

class PlanLimits(BaseModel):
    daily_docs: int
    max_pages: int
    max_size_mb: int
    can_generate_podcast: bool
    max_stored_jobs: int  # -1 for unlimited
    retention_days: int   # days to keep jobs
    monthly_token_limit: int

PLANS = {
    PlanType.FREE: PlanLimits(
        daily_docs=1,
        max_pages=10,
        max_size_mb=5,
        can_generate_podcast=False,
        max_stored_jobs=3,
        retention_days=1,
        monthly_token_limit=20000
    ),
    PlanType.BASIC: PlanLimits(
        daily_docs=10,
        max_pages=50,
        max_size_mb=15,
        can_generate_podcast=True,
        max_stored_jobs=30,
        retention_days=7,
        monthly_token_limit=150000
    ),
    PlanType.PRO: PlanLimits(
        daily_docs=50,
        max_pages=150,
        max_size_mb=30,
        can_generate_podcast=True,
        max_stored_jobs=-1,
        retention_days=30,
        monthly_token_limit=750000
    ),
    PlanType.BUSINESS: PlanLimits(
        daily_docs=9999,  # Effectively unlimited
        max_pages=300,
        max_size_mb=100,
        can_generate_podcast=True,
        max_stored_jobs=-1,
        retention_days=-1,  # Unlimited
        monthly_token_limit=10000000  # 10M tokens
    )
}
