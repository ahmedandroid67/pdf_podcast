# PDF Podcast Summarizer

This application allows users to upload a PDF or provide a URL to a PDF.

The system will:

1. Extract text from the document
2. Summarize the content using an LLM
3. Convert the summary into a podcast-style narration
4. Generate audio using TTS
5. Allow users to listen to the audio

Stack

Frontend
- React
- Tailwind

Backend
- FastAPI (Python)

Libraries
- pymupdf for PDF parsing
- OpenAI API for summarization
- OpenAI TTS / ElevenLabs for audio

Core modules

pdf_parser.py
summarizer.py
podcast_generator.py
audio_generator.py