import requests
import json
import traceback
try:
    resp = requests.post("http://localhost:8000/auth/register", json={"email": "ahmed2@yahoo.fr", "password": "password123"})
    print("Status:", resp.status_code)
    print("Body:", resp.text)
except Exception as e:
    print(traceback.format_exc())
