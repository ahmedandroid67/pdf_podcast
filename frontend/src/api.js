/**
 * api.js — thin wrapper over the FastAPI backend
 */

const BASE = '/api';

async function handleResponse(res) {
  if (!res.ok) {
    const body = await res.json().catch(() => ({}));
    throw new Error(body.detail || `HTTP ${res.status}`);
  }
  return res.json();
}

/** Upload a PDF File object → { text } */
export async function uploadPdf(file) {
  const form = new FormData();
  form.append('file', file);
  const res = await fetch(`${BASE}/upload`, { method: 'POST', body: form });
  return handleResponse(res);
}

/** Fetch PDF from URL → { text } */
export async function fetchPdfUrl(url) {
  const res = await fetch(`${BASE}/url`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ url }),
  });
  return handleResponse(res);
}

/** Summarize extracted text → { summary } */
export async function summarizeText(text, lang = 'en') {
  const res = await fetch(`${BASE}/summarize`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ text, lang }),
  });
  return handleResponse(res);
}

/** Generate podcast script + audio from summary → { script, audio_url } */
export async function generatePodcast(summary, lang = 'en') {
  const res = await fetch(`${BASE}/podcast`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ summary, lang }),
  });
  return handleResponse(res);
}
