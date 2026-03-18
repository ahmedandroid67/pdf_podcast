"""Quick smoke test for admin endpoints."""
from fastapi.testclient import TestClient
from main import app
client = TestClient(app)

# Step 1: Login as admin
resp = client.post("/auth/login", json={"email": "ahmed@yahoo.fr", "password": "123456789"})
print("Login status:", resp.status_code, resp.text[:200])

if resp.status_code == 200:
    token = resp.json()["access_token"]
    headers = {"Authorization": f"Bearer {token}"}
    
    # Step 2: Get admin stats  
    stats = client.get("/api/admin/stats", headers=headers)
    print("Stats status:", stats.status_code, stats.json() if stats.status_code == 200 else stats.text[:200])
    
    # Step 3: List users
    users = client.get("/api/admin/users", headers=headers)
    print("Users status:", users.status_code, f"{len(users.json())} users" if users.status_code == 200 else users.text[:200])
else:
    print("Login failed, skipping further tests")
