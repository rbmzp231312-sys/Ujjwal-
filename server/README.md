# UJJWAL Text-to-Video Server

This is the private-key-holding backend for the Android app.

## Endpoints
- `GET /health` — server status
- `POST /generate` — accepts JSON `{"prompt":"..."}` and returns an MP4 video

## Required secret
Set `HF_TOKEN` as a server environment variable. Create a Hugging Face access token with Inference Providers permission and never put it in the Android app or commit it to Git.

The server currently requests `Wan-AI/Wan2.2-TI2V-5B` through the `fal-ai` provider. Availability and charges depend on the provider account. Hugging Face Free accounts currently receive only a small monthly credit, not unlimited free video generation. Check provider pricing before generating.

## Run locally
```bash
pip install -r requirements.txt
export HF_TOKEN=your_token
uvicorn main:app --host 0.0.0.0 --port 8000
```
