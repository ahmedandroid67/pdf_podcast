import sys
import os
from sqlalchemy import text, inspect
from dotenv import load_dotenv

# Ensure we're in the backend directory
sys.path.append(os.path.dirname(__file__))

# Load env variables
load_dotenv()

from database.database import engine

def migrate():
    url = engine.url.render_as_string(hide_password=True)
    print(f"Migrating database: {url}")
    
    dialect = engine.dialect.name
    
    with engine.connect() as conn:
        inspector = inspect(engine)
        columns = [c['name'] for c in inspector.get_columns('users')]
        print(f"Current columns in 'users' table: {columns}")

        if dialect == "postgresql":
            print("Detected PostgreSQL. Running ALTER TABLE...")
            conn.execute(text("ALTER TABLE users ALTER COLUMN password_hash DROP NOT NULL"))
            conn.commit()
            print("PostgreSQL migration successful.")
            
        elif dialect == "sqlite":
            print("Detected SQLite. Performing table recreation strategy...")
            # SQLite doesn't support ALTER COLUMN DROP NOT NULL.
            # We must recreate the table.
            
            # 1. Get current schema to be safe, but we'll use a standard one
            # inspired by the User model.
            
            try:
                # Disable foreign key checks for the swap
                conn.execute(text("PRAGMA foreign_keys=OFF"))
                
                # Create a new table with the correct schema
                # We'll use the existing columns but remove NOT NULL from password_hash
                # Note: This is a simplified version of the User model schema
                conn.execute(text("""
                    CREATE TABLE users_new (
                        id CHAR(36) PRIMARY KEY,
                        email VARCHAR(255) UNIQUE NOT NULL,
                        password_hash VARCHAR(255),
                        "plan" VARCHAR(16) NOT NULL DEFAULT 'free',
                        daily_quota INTEGER NOT NULL DEFAULT 1,
                        used_today INTEGER NOT NULL DEFAULT 0,
                        tokens_used_today INTEGER NOT NULL DEFAULT 0,
                        tokens_used_month INTEGER NOT NULL DEFAULT 0,
                        total_tokens_used INTEGER NOT NULL DEFAULT 0,
                        quota_reset_at DATETIME NOT NULL,
                        month_reset_at DATETIME NOT NULL,
                        subscription_status VARCHAR(32) NOT NULL DEFAULT 'active',
                        subscription_end_at DATETIME,
                        created_at DATETIME NOT NULL,
                        is_active BOOLEAN NOT NULL DEFAULT 1,
                        is_admin BOOLEAN NOT NULL DEFAULT 0
                    )
                """))
                
                # Copy data from old to new
                # We map columns explicitly to avoid issues if order changed
                cols_str = ", ".join([f'"{c}"' if c == "plan" else c for c in columns])
                conn.execute(text(f"INSERT INTO users_new ({cols_str}) SELECT {cols_str} FROM users"))
                
                # Drop old table
                conn.execute(text("DROP TABLE users"))
                
                # Rename new table
                conn.execute(text("ALTER TABLE users_new RENAME TO users"))
                
                # Re-enable foreign key checks
                conn.execute(text("PRAGMA foreign_keys=ON"))
                
                conn.commit()
                print("SQLite migration successful.")
            except Exception as e:
                conn.rollback()
                print(f"SQLite migration failed: {e}")
                raise
        else:
            print(f"Unsupported dialect: {dialect}. Please manually make 'password_hash' nullable in the 'users' table.")

if __name__ == "__main__":
    try:
        migrate()
    except Exception as e:
        print(f"Migration error: {e}")
        sys.exit(1)
