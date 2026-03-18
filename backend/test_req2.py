import requests
try:
    resp = requests.post("http://localhost:8000/auth/register", json={"email": "passlib_is_gone@yahoo.com", "password": "password123"})
    print("Status:", resp.status_code)
    try:
        print("Body JSON:", resp.json())
    except:
        print("Body Text:", resp.text)
except Exception as e:
    print("Request failed:", e)
