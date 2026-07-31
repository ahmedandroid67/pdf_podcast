FROM python:3.11-slim

WORKDIR /app

# Install system dependencies (needed for some PDF/Audio operations)
RUN apt-get update && apt-get install -y \
    build-essential \
    && rm -rf /var/lib/apt/lists/*

# Copy requirements and install
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt
RUN pip install --upgrade google-generativeai

# Copy application code
COPY . .

# Create directory for audio files
RUN mkdir -p static/audio

# Expose the port
EXPOSE 8000

# No healthcheck here to avoid "unhealthy" status


RUN useradd --create-home --shell /bin/bash appuser && \
    chown -R appuser:appuser /app

USER appuser

# Apply pending Alembic migrations, then start the app. On an existing
# deployment whose DB predates Alembic, run `alembic stamp 0001_initial_schema`
# once (see migrations/versions/0001_initial_schema.py) before deploying this.
CMD ["sh", "-c", "alembic upgrade head && uvicorn main:app --host 0.0.0.0 --port 8000"]
