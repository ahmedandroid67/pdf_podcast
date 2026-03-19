from typing import Tuple
from sqlalchemy.orm import Session
from database.models import User, PodcastJob
from plans import PLANS, PlanLimits, PlanType

def get_user_plan_limits(user: User) -> PlanLimits:
    """Return the limits for the user's current plan."""
    plan_type = PlanType(user.plan)
    return PLANS.get(plan_type, PLANS[PlanType.FREE])

def can_upload_pdf(user: User, file_size_bytes: int, page_count: int) -> Tuple[bool, str]:
    """Check if the user can upload a PDF based on plan limits."""
    limits = get_user_plan_limits(user)
    plan_name = user.plan.upper()
    
    # 1. Check daily document limit
    if user.used_today >= limits.daily_docs:
        return False, f"We cannot process your file because you've reached your daily document limit for the {plan_name} plan. Please upgrade for higher limits."
    
    # 2. Check file size
    max_bytes = limits.max_size_mb * 1024 * 1024
    if file_size_bytes > max_bytes:
        size_mb = file_size_bytes/(1024*1024)
        return False, f"This file ({size_mb:.1f}MB) cannot be processed on your current plan. The {plan_name} plan has a {limits.max_size_mb}MB limit. Please upgrade to a higher plan."
    
    # 3. Check page count
    if page_count > limits.max_pages:
        return False, f"We cannot process this document because it has {page_count} pages, which exceeds the {limits.max_pages}-page limit of the {plan_name} plan. Upgrade for larger documents."
    
    return True, ""

def can_generate_podcast(user: User) -> Tuple[bool, str]:
    """Check if the user's plan allows podcast/audio generation."""
    limits = get_user_plan_limits(user)
    plan_name = user.plan.upper()
    if not limits.can_generate_podcast:
        return False, f"Podcast and audio generation are not included in the {plan_name} plan. Please upgrade to BASIC or higher to enjoy this feature."
    return True, ""

def check_token_limit(user: User, additional_tokens: int = 0) -> Tuple[bool, str]:
    """Check if the user has enough monthly token quota remaining."""
    limits = get_user_plan_limits(user)
    plan_name = user.plan.upper()
    if user.tokens_used_month + additional_tokens > limits.monthly_token_limit:
        return False, f"Your monthly AI token quota has been reached for the {plan_name} plan. We cannot process your request at this time. Please upgrade for more tokens."
    return True, ""

def can_store_more_jobs(user: User, db: Session) -> Tuple[bool, str]:
    """Check if the user has reached their job storage limit."""
    limits = get_user_plan_limits(user)
    plan_name = user.plan.upper()
    if limits.max_stored_jobs == -1:
        return True, ""
    
    job_count = db.query(PodcastJob).filter(PodcastJob.user_id == user.id).count()
    if job_count >= limits.max_stored_jobs:
        return False, f"Your storage limit of {limits.max_stored_jobs} jobs has been reached on the {plan_name} plan. Please delete some old jobs or upgrade to increase your storage."
    
    return True, ""
