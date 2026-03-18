import traceback
from database.database import SessionLocal
from auth.auth_service import create_user
from database.database import engine
from database.models import Base
from schemas.user_schema import UserRead
import uuid

def test():
    Base.metadata.create_all(bind=engine)
    db = SessionLocal()
    try:
        user = create_user(db, f"{uuid.uuid4()}@test.com", "password123")
        print("Success user created")
        try:
            read_user = UserRead.model_validate(user)
            print("Success model dump:", read_user.model_dump())
        except AttributeError:
            read_user = UserRead.from_orm(user)
            print("Success from_orm:", read_user.dict())
    except Exception as e:
        print("Failed!")
        traceback.print_exc()
    finally:
        db.close()

if __name__ == "__main__":
    test()
