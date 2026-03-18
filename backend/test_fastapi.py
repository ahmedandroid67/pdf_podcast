from fastapi.testclient import TestClient
from main import app
import traceback
import sys

client = TestClient(app)

try:
    response = client.post("/auth/register", json={"email": "tester99@yahoo.com", "password": "password123"})
    print("Status code:", response.status_code)
    print("Response JSON:", response.json() if "application/json" in response.headers.get("content-type", "") else response.text)
except Exception as e:
    print("Exception occurred!")
    traceback.print_exc()
