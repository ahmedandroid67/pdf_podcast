import traceback
from database.database import SessionLocal
from auth.auth_service import create_user
from database.models import Base
from database.database import engine

def test():
    Base.metadata.create_all(bind=engine)
    db = SessionLocal()
    try:
        user = create_user(db, "ahmed3@yahoo.fr", "password123")
        print("Success:", user.id)
    except Exception as e:
        print("Failed!")
        traceback.print_exc()
    finally:
        db.close()

if __name__ == "__main__":
    test()
