"""
Migration: Add is_admin and is_active columns to users table.
Run once to update existing databases.
"""
import sys
import os
sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from database.database import engine
from sqlalchemy import text

def migrate():
    with engine.connect() as conn:
        # Check if columns already exist
        result = conn.execute(text("PRAGMA table_info(users)")).fetchall()
        existing_cols = {row[1] for row in result}

        if "is_active" not in existing_cols:
            conn.execute(text("ALTER TABLE users ADD COLUMN is_active BOOLEAN NOT NULL DEFAULT 1"))
            print("Added column: is_active")
        else:
            print("Column is_active already exists, skipping.")

        if "is_admin" not in existing_cols:
            conn.execute(text("ALTER TABLE users ADD COLUMN is_admin BOOLEAN NOT NULL DEFAULT 0"))
            print("Added column: is_admin")
        else:
            print("Column is_admin already exists, skipping.")

        conn.commit()
    print("Migration complete!")

if __name__ == "__main__":
    migrate()
