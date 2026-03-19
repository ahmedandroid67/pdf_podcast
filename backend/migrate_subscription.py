import sys
import os
from sqlalchemy import text
from dotenv import load_dotenv

# Ensure we're in the backend directory
sys.path.append(os.path.dirname(__file__))

# Load env variables
load_dotenv()

from database.database import engine

def migrate():
    print(f"Migrating database: {engine.url.render_as_string(hide_password=True)}")
    
    with engine.connect() as conn:
        # 1. Get current columns
        # Since it's PostgreSQL, we can check information_schema
        stmt = text("""
            SELECT column_name 
            FROM information_schema.columns 
            WHERE table_name = 'users'
        """)
        result = conn.execute(stmt)
        # Fetch all results and then build the list
        columns = [row.column_name for row in result.mappings()]
        print(f"Current columns: {columns}")

        # 2. Add tokens_used_month
        if "tokens_used_month" not in columns:
            print("Adding tokens_used_month...")
            conn.execute(text("ALTER TABLE users ADD COLUMN tokens_used_month INTEGER DEFAULT 0"))

        # 3. Add month_reset_at
        if "month_reset_at" not in columns:
            print("Adding month_reset_at...")
            # For PG, CURRENT_TIMESTAMP is fine
            conn.execute(text("ALTER TABLE users ADD COLUMN month_reset_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP"))

        # 4. Add subscription_status
        if "subscription_status" not in columns:
            print("Adding subscription_status...")
            conn.execute(text("ALTER TABLE users ADD COLUMN subscription_status VARCHAR(32) DEFAULT 'active'"))

        # 5. Add subscription_end_at
        if "subscription_end_at" not in columns:
            print("Adding subscription_end_at...")
            conn.execute(text("ALTER TABLE users ADD COLUMN subscription_end_at TIMESTAMP WITHOUT TIME ZONE"))

        conn.commit()
        print("Migration completed successfully.")

if __name__ == "__main__":
    try:
        migrate()
    except Exception as e:
        print(f"Migration error: {e}")
        sys.exit(1)
