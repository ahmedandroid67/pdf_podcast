import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { API_BASE, fetchJobs, changePassword } from '../api';
import { useAuth } from '../context/AuthContext';
import PodcastPipeline from '../components/PodcastPipeline';
import LanguageSelector from '../components/LanguageSelector';
import './Dashboard.css';

/* ── inline audio loader ── */
function JobAudio({ jobId }) {
  const [audioUrl, setAudioUrl] = useState('');

  useEffect(() => {
    let active = true;
    let objectUrl = '';
    const load = async () => {
      const token = localStorage.getItem('auth_token');
      const res = await fetch(`${API_BASE}/api/audio/${jobId}`, {
        headers: token ? { Authorization: `Bearer ${token}` } : {},
      });
      if (!res.ok) return;
      const blob = await res.blob();
      objectUrl = URL.createObjectURL(blob);
      if (active) setAudioUrl(objectUrl);
    };
    load();
    return () => { active = false; if (objectUrl) URL.revokeObjectURL(objectUrl); };
  }, [jobId]);

  if (!audioUrl) return null;
  return (
    <div className="db-job-audio">
      <audio controls src={audioUrl} />
    </div>
  );
}

/* ── avatar initials ── */
function getInitials(email = '') {
  const parts = email.split('@')[0].split(/[._-]/);
  return parts.slice(0, 2).map((p) => p[0]?.toUpperCase() || '').join('') || '?';
}

/* ── plan styling map ── */
const PLAN_STYLES = {
  free:     { badgeBg: '#64748b',   cardBg: '#f8fafc', border: '#e2e8f0' },
  basic:    { badgeBg: '#2563eb',   cardBg: '#eff6ff', border: '#bfdbfe' },
  pro:      { badgeBg: 'linear-gradient(135deg,#6366f1,#818cf8)', cardBg: '#eef2ff', border: '#c7d2fe' },
  business: { badgeBg: '#d97706',   cardBg: '#fffbeb', border: '#fde68a' },
};

export default function Dashboard() {
  const { t } = useTranslation();
  const { user, logout } = useAuth();
  const [jobs, setJobs]               = useState([]);
  const [loadingJobs, setLoadingJobs] = useState(false);
  const [activeNav, setActiveNav]     = useState('create');

  const [showPasswordModal, setShowPasswordModal] = useState(false);
  const [newPassword, setNewPassword]             = useState('');
  const [passwordLoading, setPasswordLoading]     = useState(false);
  const [passwordError, setPasswordError]         = useState('');
  const [passwordSuccess, setPasswordSuccess]     = useState('');

  const plan      = user?.plan || 'free';
  const planStyle = PLAN_STYLES[plan] || PLAN_STYLES.pro;
  const used      = user?.used_today ?? 0;
  const quota     = user?.daily_quota ?? 0;
  const remaining = Math.max(quota - used, 0);
  const pct       = quota > 0 ? Math.min(100, (used / quota) * 100) : 0;

  const loadJobs = async () => {
    setLoadingJobs(true);
    try { setJobs(await fetchJobs()); }
    finally { setLoadingJobs(false); }
  };

  const handleChangePassword = async (e) => {
    e.preventDefault();
    if (newPassword.length < 8) {
      setPasswordError(t('password_short', 'Password must be at least 8 characters.'));
      return;
    }
    setPasswordLoading(true);
    setPasswordError('');
    setPasswordSuccess('');
    try {
      await changePassword(newPassword);
      setPasswordSuccess(t('password_changed', 'Password updated successfully!'));
      setNewPassword('');
      setTimeout(() => { setShowPasswordModal(false); setPasswordSuccess(''); }, 2000);
    } catch (err) {
      setPasswordError(err.message || t('password_change_failed', 'Failed to update password.'));
    } finally {
      setPasswordLoading(false);
    }
  };

  useEffect(() => { loadJobs(); }, []);

  const NAV = [
    { id: 'create',  icon: '✨', label: t('nav_create',  'Create Podcast') },
    { id: 'history', icon: '📋', label: t('nav_history', 'Job History') },
  ];

  return (
    <div className="db-root">

      {/* ════════════ HEADER ════════════ */}
      <header className="db-header">
        <div className="db-header-brand">
          <div className="db-header-logo">🎙️</div>
          <span className="db-header-name">Pdf<em>Parser</em></span>
        </div>
        <div className="db-header-right">
          <LanguageSelector />
          {user?.is_admin && (
            <Link to="/admin" className="db-header-admin">🛡️ Admin</Link>
          )}
          <button className="db-logout" onClick={logout}>{t('logout')}</button>
        </div>
      </header>

      {/* ════════════ BODY ════════════ */}
      <div className="db-body">

        {/* ── Sidebar ── */}
        <aside className="db-sidebar">

          {/* User card */}
          <div className={`db-user-card db-plan-${plan}`} style={{ borderColor: planStyle.border }}>
            <div className="db-avatar">{getInitials(user?.email)}</div>

            <div className="db-user-email" title={user?.email}>{user?.email}</div>

            <div className="db-user-plan-row">
              <span
                className="db-plan-badge"
                style={{ background: planStyle.badgeBg }}
              >
                {plan}
              </span>
              <span style={{ fontSize: '0.7rem', color: '#9ca3af', fontWeight: 500 }}>plan</span>
            </div>

            <button className="db-change-pw" onClick={() => setShowPasswordModal(true)}>
              🔑 {t('change_password', 'Change password')}
            </button>

            <div className="db-divider" />

            {/* Quota */}
            <div className="db-quota-label">{t('quota_used', 'Daily Usage')}</div>
            <div className="db-quota-row">
              <span className="db-quota-used">{used}</span>
              <span className="db-quota-total">/ {quota}</span>
            </div>
            <div className="db-quota-bar-wrap">
              <div className="db-quota-bar-fill" style={{ width: `${pct}%` }} />
            </div>
            <span
              className="db-quota-remaining"
              style={{ color: remaining > 0 ? '#059669' : '#dc2626' }}
            >
              {remaining} {t('remaining', 'remaining today')}
            </span>
          </div>

          {/* Nav */}
          <nav className="db-nav-card">
            {NAV.map((item) => (
              <button
                key={item.id}
                className={`db-nav-item${activeNav === item.id ? ' db-nav-item-active' : ''}`}
                onClick={() => setActiveNav(item.id)}
              >
                <span className="db-nav-icon">{item.icon}</span>
                {item.label}
              </button>
            ))}
          </nav>

          {/* Tip */}
          <div className="db-tip-card">
            <div className="db-tip-title">💡 {t('tip_title', 'Pro tip')}</div>
            <p className="db-tip-text">
              {t('tip_body', 'Upload multi-page PDFs — our AI extracts only the key ideas, keeping your podcast crisp and engaging.')}
            </p>
          </div>
        </aside>

        {/* ── Main ── */}
        <main className="db-main">

          {/* ── Create Podcast ── */}
          {activeNav === 'create' && (
            <div className="db-pipeline-card">
              <div className="db-pipeline-header">
                <div className="db-section-title">
                  <span className="db-section-icon">✨</span>
                  {t('nav_create', 'Create Podcast')}
                </div>
              </div>
              <div className="db-pipeline-body">
                <PodcastPipeline embedded onJobCreated={() => { loadJobs(); setActiveNav('history'); }} />
              </div>
            </div>
          )}

          {/* ── Job History ── */}
          {activeNav === 'history' && (
            <div className="db-jobs-card">
              <div className="db-jobs-header">
                <div className="db-section-title">
                  <span className="db-section-icon">📋</span>
                  {t('recent_jobs', 'Recent Jobs')}
                  {jobs.length > 0 && (
                    <span style={{
                      marginLeft: '6px', fontSize: '0.68rem', fontWeight: 700,
                      background: '#eef2ff', color: '#6366f1',
                      padding: '2px 8px', borderRadius: '100px',
                    }}>
                      {jobs.length}
                    </span>
                  )}
                </div>
                <button className="db-refresh-btn" onClick={loadJobs} disabled={loadingJobs}>
                  {loadingJobs ? '…' : '↺'} {t('refresh', 'Refresh')}
                </button>
              </div>

              {jobs.length === 0 ? (
                <div className="db-jobs-empty">
                  <div className="db-jobs-empty-icon">🎙️</div>
                  <p className="db-jobs-empty-text">{t('no_jobs', 'No jobs yet — create your first podcast!')}</p>
                  <button
                    onClick={() => setActiveNav('create')}
                    style={{
                      marginTop: '14px', padding: '9px 20px',
                      background: '#eef2ff', border: '1.5px solid #c7d2fe',
                      borderRadius: '10px', color: '#6366f1',
                      fontSize: '0.82rem', fontWeight: 700, cursor: 'pointer',
                      fontFamily: 'inherit', transition: 'background 0.15s',
                    }}
                    onMouseEnter={e => e.currentTarget.style.background = '#e0e7ff'}
                    onMouseLeave={e => e.currentTarget.style.background = '#eef2ff'}
                  >
                    ✨ {t('nav_create', 'Create Podcast')}
                  </button>
                </div>
              ) : (
                jobs.map((job) => {
                  const statusClass = {
                    completed:  'db-status-completed',
                    failed:     'db-status-failed',
                    processing: 'db-status-processing',
                  }[job.status] || 'db-status-pending';

                  return (
                    <div key={job.id} className="db-job-row">
                      <div style={{ display: 'flex', alignItems: 'center', gap: '12px', minWidth: 0 }}>
                        <div className="db-job-file-icon">📄</div>
                        <div className="db-job-info">
                          <p className="db-job-name">{job.pdf_name}</p>
                          <div className="db-job-meta">
                            <span className={`db-status-chip ${statusClass}`}>{job.status}</span>
                            {job.created_at && (
                              <span style={{ fontSize: '0.72rem', color: '#9ca3af', fontWeight: 500 }}>
                                {new Date(job.created_at).toLocaleDateString()}
                              </span>
                            )}
                          </div>
                        </div>
                      </div>
                      {job.audio_path && <JobAudio jobId={job.id} />}
                    </div>
                  );
                })
              )}
            </div>
          )}

          {/* Show both when no specific view selected — default shows create */}
        </main>
      </div>

      {/* ════════════ CHANGE PASSWORD MODAL ════════════ */}
      {showPasswordModal && (
        <div className="db-modal-backdrop" onClick={(e) => e.target === e.currentTarget && setShowPasswordModal(false)}>
          <div className="db-modal">
            <div className="db-modal-title">
              {t('change_password', 'Change password')}
              <button className="db-modal-close" onClick={() => setShowPasswordModal(false)}>×</button>
            </div>

            <form onSubmit={handleChangePassword}>
              <label className="db-modal-label">{t('new_password', 'New password')}</label>
              <input
                type="password"
                className="input-field"
                placeholder="Min. 8 characters"
                value={newPassword}
                onChange={(e) => setNewPassword(e.target.value)}
                style={{ marginBottom: '14px' }}
                required
                autoComplete="new-password"
              />

              {passwordError   && <div className="db-alert-error">{passwordError}</div>}
              {passwordSuccess && <div className="db-alert-success">{passwordSuccess}</div>}

              <button type="submit" className="db-modal-submit" disabled={passwordLoading}>
                {passwordLoading ? t('loading') : t('save_changes', 'Save password')}
              </button>
            </form>
          </div>
        </div>
      )}
    </div>
  );
}
