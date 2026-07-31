/**
 * api.js — thin wrapper over the FastAPI backend
 */

export const API_BASE = import.meta.env.VITE_API_BASE || '';
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
    const err = new Error(errorMessage);
    err.status = res.status;
    throw err;
  }
  return res.json();
}

/** Start full pipeline (upload) → { job_id, status } */
export async function processPdfUpload(file, lang = 'en', mode = 'podcast', { signal } = {}) {
  const form = new FormData();
  form.append('file', file);
  form.append('lang', lang);
  form.append('mode', mode);
  const res = await fetch(`${BASE}/process/upload`, {
    method: 'POST',
    headers: authHeaders(),
    body: form,
    signal,
  });
  return handleResponse(res);
}

/** Start full pipeline (URL) → { job_id, status } */
export async function processPdfUrl(url, lang = 'en', mode = 'podcast', { signal } = {}) {
  const res = await fetch(`${BASE}/process/url`, {
    method: 'POST',
    headers: authHeaders({ 'Content-Type': 'application/json' }),
    body: JSON.stringify({ url, lang, mode }),
    signal,
  });
  return handleResponse(res);
}

/** Poll pipeline progress → { job_id, step, status } */
export async function fetchJobProgress(jobId, { signal } = {}) {
  const res = await fetch(`${BASE}/jobs/${jobId}/progress`, {
    headers: authHeaders(),
    signal,
  });
  return handleResponse(res);
}

/** Fetch final result (text/summary/script/audio_url) once completed */
export async function fetchJobResult(jobId, { signal } = {}) {
  const res = await fetch(`${BASE}/jobs/${jobId}/result`, {
    headers: authHeaders(),
    signal,
  });
  return handleResponse(res);
}

export async function fetchJobs({ signal } = {}) {
  const res = await fetch(`${BASE}/jobs`, {
    method: 'GET',
    headers: authHeaders(),
    signal,
  });
  return handleResponse(res);
}

export async function loginUser(email, password, { signal } = {}) {
  const res = await fetch(`${API_BASE}/auth/login`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ email, password }),
    signal,
  });
  return handleResponse(res);
}

export async function registerUser(email, password, { signal } = {}) {
  const res = await fetch(`${API_BASE}/auth/register`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ email, password }),
    signal,
  });
  return handleResponse(res);
}

export async function fetchMe({ signal } = {}) {
  const res = await fetch(`${API_BASE}/auth/me`, {
    method: 'GET',
    headers: authHeaders(),
    signal,
  });
  return handleResponse(res);
}

export async function changePassword(newPassword, { signal } = {}) {
  const res = await fetch(`${API_BASE}/auth/change-password`, {
    method: 'POST',
    headers: authHeaders({ 'Content-Type': 'application/json' }),
    body: JSON.stringify({ new_password: newPassword }),
    signal,
  });
  return handleResponse(res);
}

// ---- Admin API ----

export async function fetchAdminStats({ signal } = {}) {
  const res = await fetch(`${API_BASE}/api/admin/stats`, {
    headers: authHeaders(),
    signal,
  });
  return handleResponse(res);
}

export async function fetchAdminUsers({ signal } = {}) {
  const res = await fetch(`${API_BASE}/api/admin/users`, {
    headers: authHeaders(),
    signal,
  });
  return handleResponse(res);
}

export async function adminCreateUser(data, { signal } = {}) {
  const res = await fetch(`${API_BASE}/api/admin/users`, {
    method: 'POST',
    headers: authHeaders({ 'Content-Type': 'application/json' }),
    body: JSON.stringify(data),
    signal,
  });
  return handleResponse(res);
}

export async function adminUpdateUser(userId, data, { signal } = {}) {
  const res = await fetch(`${API_BASE}/api/admin/users/${userId}`, {
    method: 'PUT',
    headers: authHeaders({ 'Content-Type': 'application/json' }),
    body: JSON.stringify(data),
    signal,
  });
  return handleResponse(res);
}

export async function adminResetPassword(userId, password, { signal } = {}) {
  const res = await fetch(`${API_BASE}/api/admin/users/${userId}/reset-password`, {
    method: 'POST',
    headers: authHeaders({ 'Content-Type': 'application/json' }),
    body: JSON.stringify({ password }),
    signal,
  });
  return handleResponse(res);
}

export async function adminBulkDeleteOldUsers({ signal } = {}) {
  const res = await fetch(`${API_BASE}/api/admin/users/bulk/old?confirm=true`, {
    method: 'DELETE',
    headers: authHeaders(),
    signal,
  });
  return handleResponse(res);
}

export async function adminToggleUserStatus(userId, { signal } = {}) {
  const res = await fetch(`${API_BASE}/api/admin/users/${userId}/status`, {
    method: 'PUT',
    headers: authHeaders(),
    signal,
  });
  return handleResponse(res);
}

export async function adminDeleteUser(userId, { signal } = {}) {
  const res = await fetch(`${API_BASE}/api/admin/users/${userId}`, {
    method: 'DELETE',
    headers: authHeaders(),
    signal,
  });
  return handleResponse(res);
}
