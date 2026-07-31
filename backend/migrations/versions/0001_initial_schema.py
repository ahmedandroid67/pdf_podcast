"""initial schema

Baseline migration reflecting the schema as of the current database.models
(the schema previously managed by Base.metadata.create_all() plus a series
of ad-hoc migrate_*.py scripts). If you are applying this to a database that
already has this schema (i.e. any existing deployment), run:

    alembic stamp 0001_initial_schema

instead of `alembic upgrade head` — stamping records the revision without
re-running the (already-applied) DDL. Only run `upgrade head` against a
genuinely empty database.

Revision ID: 0001_initial_schema
Revises:
Create Date: 2026-07-31 12:40:20.585899

"""
from typing import Sequence, Union

import sqlalchemy as sa
from alembic import op

import database.models

# revision identifiers, used by Alembic.
revision: str = "0001_initial_schema"
down_revision: Union[str, None] = None
branch_labels: Union[str, Sequence[str], None] = None
depends_on: Union[str, Sequence[str], None] = None


def upgrade() -> None:
    op.create_table(
        "users",
        sa.Column("id", database.models.GUID(), nullable=False),
        sa.Column("email", sa.String(length=255), nullable=False),
        sa.Column("password_hash", sa.String(length=255), nullable=True),
        sa.Column("plan", sa.Enum("free", "basic", "pro", "business", name="plantype"), nullable=False),
        sa.Column("daily_quota", sa.Integer(), nullable=False),
        sa.Column("used_today", sa.Integer(), nullable=False),
        sa.Column("tokens_used_today", sa.Integer(), nullable=False),
        sa.Column("tokens_used_month", sa.Integer(), nullable=False),
        sa.Column("total_tokens_used", sa.Integer(), nullable=False),
        sa.Column("quota_reset_at", sa.DateTime(), nullable=False),
        sa.Column("month_reset_at", sa.DateTime(), nullable=False),
        sa.Column("subscription_status", sa.String(length=32), nullable=False),
        sa.Column("subscription_end_at", sa.DateTime(), nullable=True),
        sa.Column("created_at", sa.DateTime(), nullable=False),
        sa.Column("is_active", sa.Boolean(), nullable=False),
        sa.Column("is_admin", sa.Boolean(), nullable=False),
        sa.PrimaryKeyConstraint("id"),
    )
    op.create_index(op.f("ix_users_email"), "users", ["email"], unique=True)
    op.create_table(
        "podcast_jobs",
        sa.Column("id", database.models.GUID(), nullable=False),
        sa.Column("user_id", database.models.GUID(), nullable=False),
        sa.Column("pdf_name", sa.String(length=255), nullable=False),
        sa.Column("pdf_size", sa.Integer(), nullable=False),
        sa.Column("summary_mode", sa.String(length=32), nullable=False),
        sa.Column("audio_path", sa.Text(), nullable=True),
        sa.Column("tokens_used", sa.Integer(), nullable=False),
        sa.Column(
            "status",
            sa.Enum("queued", "processing", "completed", "failed", name="jobstatus"),
            nullable=False,
        ),
        sa.Column("created_at", sa.DateTime(), nullable=False),
        sa.ForeignKeyConstraint(["user_id"], ["users.id"]),
        sa.PrimaryKeyConstraint("id"),
    )
    op.create_index(op.f("ix_podcast_jobs_user_id"), "podcast_jobs", ["user_id"], unique=False)


def downgrade() -> None:
    op.drop_index(op.f("ix_podcast_jobs_user_id"), table_name="podcast_jobs")
    op.drop_table("podcast_jobs")
    op.drop_index(op.f("ix_users_email"), table_name="users")
    op.drop_table("users")
