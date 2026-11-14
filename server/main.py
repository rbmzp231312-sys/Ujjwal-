import os
from fastapi import FastAPI, HTTPException
from fastapi.middleware.cors import CORSMiddleware
from fastapi.responses import Response
from pydantic import BaseModel, Field
from huggingface_hub import InferenceClient

app = FastAPI(title="UJJWAL AI Video Server", version="1.0.0")

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_methods=["GET", "POST"],
    allow_headers=["*"],
)

class VideoRequest(BaseModel):
    prompt: str = Field(min_length=5, max_length=1200)

@app.get("/health")
def health():
    return {"status": "ok", "service": "UJJWAL Text-to-Video API"}

@app.post("/generate")
def generate_video(request: VideoRequest):
    token = os.getenv("HF_TOKEN")
    if not token:
        raise HTTPException(status_code=503, detail="Server is not configured: HF_TOKEN is missing.")

    try:
        client = InferenceClient(provider="fal-ai", api_key=token, timeout=600)
        video = client.text_to_video(
            request.prompt,
            model="Wan-AI/Wan2.2-TI2V-5B",
        )
        if not video:
            raise RuntimeError("The AI provider returned an empty video.")
        return Response(
            content=video,
            media_type="video/mp4",
            headers={"Content-Disposition": 'attachment; filename="ujjwal-video.mp4"'},
        )
    except Exception as exc:
        # Do not expose credentials or internal stack traces to the app.
        raise HTTPException(
            status_code=502,
            detail="AI video generation failed. Check provider availability and free credits.",
        ) from exc
