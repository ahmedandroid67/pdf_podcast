/**
 * api.js — thin wrapper over the FastAPI backend
 */

export const API_BASE = import.meta.env.VITE_API_BASE || 'http://localhost:8000';
const BASE = `${API_BASE}/api`;

function getToken() {
  return localStorage.getItem('auth_token');
}

function authHeaders(extra = {}) {
  const token = getToken();
  return token ? { ...extra, Authorization: `Bearer ${token}` } : extra;
}

async function handleResponse(res) {
  if (!res.ok) {
    const body = await res.json().catch(() => ({}));
    let errorMessage = `HTTP ${res.status}`;
    if (body.detail) {
      if (Array.isArray(body.detail)) {
        errorMessage = body.detail.map(err => err.msg || JSON.stringify(err)).join(', ');
      } else if (typeof body.detail === 'string') {
        errorMessage = body.detail;
      } else {
        errorMessage = JSON.stringify(body.detail);
      }
    }
    throw new Error(errorMessage);
  }
  return res.json();
}

/** Upload a PDF File object → { text } */
export async function uploadPdf(file) {
  const form = new FormData();
  form.append('file', file);
  const res = await fetch(`${BASE}/upload`, {
    method: 'POST',
    headers: authHeaders(),
    body: form,
  });
  return handleResponse(res);
}

/** Fetch PDF from URL → { text } */
export async function fetchPdfUrl(url) {
  const res = await fetch(`${BASE}/url`, {
    method: 'POST',
    headers: authHeaders({ 'Content-Type': 'application/json' }),
    body: JSON.stringify({ url }),
  });
  return handleResponse(res);
}

/** Summarize extracted text → { summary } */
export async function summarizeText(text, lang = 'en', mode = 'podcast') {
  const res = await fetch(`${BASE}/summarize`, {
    method: 'POST',
    headers: authHeaders({ 'Content-Type': 'application/json' }),
    body: JSON.stringify({ text, lang, mode }),
  });
  return handleResponse(res);
}

/** Generate podcast script + audio from summary → { script, audio_url } */
export async function generatePodcast(summary, lang = 'en', mode = 'podcast') {
  const res = await fetch(`${BASE}/podcast`, {
    method: 'POST',
    headers: authHeaders({ 'Content-Type': 'application/json' }),
    body: JSON.stringify({ summary, lang, mode }),
  });
  return handleResponse(res);
}

export async function processPdfUpload(file, lang = 'en', mode = 'podcast') {
  const form = new FormData();
  form.append('file', file);
  form.append('lang', lang);
  form.append('mode', mode);
  const res = await fetch(`${BASE}/process/upload`, {
    method: 'POST',
    headers: authHeaders(),
    body: form,
  });
  return handleResponse(res);
}

export async function processPdfUrl(url, lang = 'en', mode = 'podcast') {
  const res = await fetch(`${BASE}/process/url`, {
    method: 'POST',
    headers: authHeaders({ 'Content-Type': 'application/json' }),
    body: JSON.stringify({ url, lang, mode }),
  });
  return handleResponse(res);
}

export async function fetchJobs() {
  const res = await fetch(`${BASE}/jobs`, {
    method: 'GET',
    headers: authHeaders(),
  });
  return handleResponse(res);
}

export async function loginUser(email, password) {
  const res = await fetch(`${API_BASE}/auth/login`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ email, password }),
  });
  return handleResponse(res);
}

export async function registerUser(email, password) {
  const res = await fetch(`${API_BASE}/auth/register`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ email, password }),
  });
  return handleResponse(res);
}

export async function fetchMe() {
  const res = await fetch(`${API_BASE}/auth/me`, {
    method: 'GET',
    headers: authHeaders(),
  });
  return handleResponse(res);
}

// ---- Admin API ----

export async function fetchAdminStats() {
  const res = await fetch(`${API_BASE}/api/admin/stats`, {
    headers: authHeaders(),
  });
  return handleResponse(res);
}

export async function fetchAdminUsers() {
  const res = await fetch(`${API_BASE}/api/admin/users`, {
    headers: authHeaders(),
  });
  return handleResponse(res);
}

export async function adminCreateUser(data) {
  const res = await fetch(`${API_BASE}/api/admin/users`, {
    method: 'POST',
    headers: authHeaders({ 'Content-Type': 'application/json' }),
    body: JSON.stringify(data),
  });
  return handleResponse(res);
}

export async function adminUpdateUser(userId, data) {
  const res = await fetch(`${API_BASE}/api/admin/users/${userId}`, {
    method: 'PUT',
    headers: authHeaders({ 'Content-Type': 'application/json' }),
    body: JSON.stringify(data),
  });
  return handleResponse(res);
}

export async function adminResetPassword(userId, password) {
  const res = await fetch(`${API_BASE}/api/admin/users/${userId}/reset-password`, {
    method: 'POST',
    headers: authHeaders({ 'Content-Type': 'application/json' }),
    body: JSON.stringify({ password }),
  });
  return handleResponse(res);
}

export async function adminBulkDeleteOldUsers() {
  const res = await fetch(`${API_BASE}/api/admin/users/bulk/old`, {
    method: 'DELETE',
    headers: authHeaders(),
  });
  return handleResponse(res);
}

export async function adminToggleUserStatus(userId) {
  const res = await fetch(`${API_BASE}/api/admin/users/${userId}/status`, {
    method: 'PUT',
    headers: authHeaders(),
  });
  return handleResponse(res);
}

export async function adminDeleteUser(userId) {
  const res = await fetch(`${API_BASE}/api/admin/users/${userId}`, {
    method: 'DELETE',
    headers: authHeaders(),
  });
  if (!res.ok) {
    const body = await res.json().catch(() => ({}));
    throw new Error(body.detail || `HTTP ${res.status}`);
  }
}
