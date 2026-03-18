import sqlite3
import os

db_path = os.path.join(os.path.dirname(__file__), "app.db")

def migrate():
    if not os.path.exists(db_path):
        print(f"Database not found at {db_path}")
        return

    conn = sqlite3.connect(db_path)
    cursor = conn.cursor()

    print("Migrating User table...")
    try:
        cursor.execute("ALTER TABLE users ADD COLUMN tokens_used_today INTEGER DEFAULT 0 NOT NULL")
        print("Added tokens_used_today to users")
    except sqlite3.OperationalError:
        print("tokens_used_today already exists in users")

    try:
        cursor.execute("ALTER TABLE users ADD COLUMN total_tokens_used INTEGER DEFAULT 0 NOT NULL")
        print("Added total_tokens_used to users")
    except sqlite3.OperationalError:
        print("total_tokens_used already exists in users")

    print("\nMigrating PodcastJob table...")
    try:
        cursor.execute("ALTER TABLE podcast_jobs ADD COLUMN tokens_used INTEGER DEFAULT 0 NOT NULL")
        print("Added tokens_used to podcast_jobs")
    except sqlite3.OperationalError:
        print("tokens_used already exists in podcast_jobs")

    conn.commit()
    conn.close()
    print("\nMigration complete!")

if __name__ == "__main__":
    migrate()
