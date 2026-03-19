import sys
import os
from sqlalchemy import text
from dotenv import load_dotenv

# Ensure we're in the backend directory
sys.path.append(os.path.dirname(__file__))

# Load env variables
load_dotenv()

from database.database import engine

def migrate_enum():
    print(f"Updating ENUM type in database: {engine.url.render_as_string(hide_password=True)}")
    
    # IMPORTANT: ALTER TYPE ADD VALUE cannot run inside a transaction block in some Postgres versions
    # We use connect() and then isolation_level AUTOCOMMIT or just run raw SQL if supported
    with engine.connect().execution_options(isolation_level="AUTOCOMMIT") as conn:
        try:
            print("Adding 'basic' to plantype...")
            conn.execute(text("ALTER TYPE plantype ADD VALUE IF NOT EXISTS 'basic'"))
            print("Adding 'business' to plantype...")
            conn.execute(text("ALTER TYPE plantype ADD VALUE IF NOT EXISTS 'business'"))
            print("ENUM update completed successfully.")
        except Exception as e:
            # If 'IF NOT EXISTS' is not supported (PG < 9.6), we handle error
            if "already exists" in str(e).lower():
                print("Values already exist or partial success.")
            else:
                raise e

if __name__ == "__main__":
    try:
        migrate_enum()
    except Exception as e:
        print(f"Migration error: {e}")
        sys.exit(1)
