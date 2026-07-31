"""add job cascade and indexes

- ON DELETE CASCADE on podcast_jobs.user_id, so deleting a user (e.g. the
  admin "delete user" endpoint) can't orphan their jobs at the DB level —
  previously only the ORM's cascade="all, delete-orphan" enforced this,
  which does nothing for direct SQL deletes.
- Composite (user_id, created_at) index: speeds up get_jobs_for_user's
  ORDER BY created_at and the retention-cleanup per-user cutoff scan.
- status index: used by admin_stats' completed-jobs count.

The FK constraint name assumes the default the original unnamed
ForeignKeyConstraint got when created (Postgres: "<table>_<column>_fkey").
If your deployment's constraint has a different name, adjust FK_NAME below
before running `alembic upgrade head`.

Revision ID: 0002_job_cascade_and_indexes
Revises: 0001_initial_schema
Create Date: 2026-07-31 12:44:13.715160

"""
from typing import Sequence, Union

from alembic import op

# revision identifiers, used by Alembic.
revision: str = "0002_job_cascade_and_indexes"
down_revision: Union[str, None] = "0001_initial_schema"
branch_labels: Union[str, Sequence[str], None] = None
depends_on: Union[str, Sequence[str], None] = None

FK_NAME = "podcast_jobs_user_id_fkey"


def upgrade() -> None:
    op.create_index("ix_podcast_jobs_status", "podcast_jobs", ["status"], unique=False)
    op.create_index(
        "ix_podcast_jobs_user_id_created_at", "podcast_jobs", ["user_id", "created_at"], unique=False
    )

    # ON DELETE CASCADE only applies on Postgres (the production database).
    # SQLite (used only for local dev/tests here) doesn't name FK constraints
    # the same way, and every delete in this codebase already goes through
    # the ORM's cascade="all, delete-orphan", so SQLite doesn't need the
    # DB-level constraint to behave correctly for this app.
    if op.get_bind().dialect.name == "postgresql":
        op.drop_constraint(FK_NAME, "podcast_jobs", type_="foreignkey")
        op.create_foreign_key(FK_NAME, "podcast_jobs", "users", ["user_id"], ["id"], ondelete="CASCADE")


def downgrade() -> None:
    if op.get_bind().dialect.name == "postgresql":
        op.drop_constraint(FK_NAME, "podcast_jobs", type_="foreignkey")
        op.create_foreign_key(FK_NAME, "podcast_jobs", "users", ["user_id"], ["id"])

    op.drop_index("ix_podcast_jobs_user_id_created_at", table_name="podcast_jobs")
    op.drop_index("ix_podcast_jobs_status", table_name="podcast_jobs")
