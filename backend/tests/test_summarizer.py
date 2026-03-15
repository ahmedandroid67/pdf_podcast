"""
tests/test_summarizer.py
Unit tests for summarizer.py text chunking (no OpenAI calls needed).
"""

from summarizer import chunk_text


class TestChunkText:
    def test_short_text_produces_one_chunk(self):
        text = "Hello world " * 10  # 120 chars — well under 3000
        chunks = chunk_text(text, size=500, overlap=50)
        assert len(chunks) == 1

    def test_long_text_splits_into_multiple_chunks(self):
        text = "A" * 10_000
        chunks = chunk_text(text, size=3000, overlap=200)
        assert len(chunks) > 1

    def test_chunks_are_nonempty(self):
        text = "Hello " * 2000
        chunks = chunk_text(text, size=300, overlap=50)
        assert all(len(c) > 0 for c in chunks)

    def test_empty_text_returns_empty_list(self):
        assert chunk_text("") == []
        assert chunk_text("   ") == []

    def test_overlap_preserved(self):
        text = "ABCDE" * 200  # 1000 chars
        chunks = chunk_text(text, size=300, overlap=100)
        # The tail of chunk[0] should appear in the start of chunk[1]
        assert chunks[0][-50:] in chunks[1]

    def test_single_page_text(self):
        text = "The quick brown fox jumps over the lazy dog. " * 5
        chunks = chunk_text(text, size=1000, overlap=100)
        assert len(chunks) == 1
        assert text.strip() in chunks[0]
