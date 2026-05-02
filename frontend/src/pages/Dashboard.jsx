import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { API_BASE, fetchJobs, changePassword } from '../api';
import { useAuth } from '../context/AuthContext';
import PodcastPipeline from '../components/PodcastPipeline';
import LanguageSelector from '../components/LanguageSelector';

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

    return () => {
      active = false;
      if (objectUrl) URL.revokeObjectURL(objectUrl);
    };
  }, [jobId]);

  if (!audioUrl) return null;

  return (
    <audio
      controls
      src={audioUrl}
      style={{
        width: '220px',
        height: '36px',
        accentColor: '#6366f1',
        borderRadius: '8px',
      }}
    />
  );
}

export default function Dashboard() {
  const { t } = useTranslation();
  const { user, logout } = useAuth();
  const [jobs, setJobs] = useState([]);
  const [loadingJobs, setLoadingJobs] = useState(false);

  const [showPasswordModal, setShowPasswordModal] = useState(false);
  const [newPassword, setNewPassword] = useState('');
  const [passwordLoading, setPasswordLoading] = useState(false);
  const [passwordError, setPasswordError] = useState('');
  const [passwordSuccess, setPasswordSuccess] = useState('');

  const remainingQuota = user ? Math.max(user.daily_quota - user.used_today, 0) : 0;

  const loadJobs = async () => {
    setLoadingJobs(true);
    try {
      const data = await fetchJobs();
      setJobs(data);
    } finally {
      setLoadingJobs(false);
    }
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
      setTimeout(() => {
        setShowPasswordModal(false);
        setPasswordSuccess('');
      }, 2000);
    } catch (err) {
      setPasswordError(err.message || t('password_change_failed', 'Failed to update password.'));
    } finally {
      setPasswordLoading(false);
    }
  };

  useEffect(() => { loadJobs(); }, []);

  const planColor = {
    free:     { bg: '#f8fafc', border: '#e2e8f0', badge: '#64748b' },
    basic:    { bg: '#eff6ff', border: '#bfdbfe', badge: '#2563eb' },
    pro:      { bg: '#eef2ff', border: '#c7d2fe', badge: '#4f46e5' },
    business: { bg: '#fffbeb', border: '#fde68a', badge: '#d97706' },
  }[user?.plan || 'free'] || { bg: '#eef2ff', border: '#c7d2fe', badge: '#4f46e5' };

  return (
    <div style={{ minHeight: '100vh', display: 'flex', flexDirection: 'column', background: '#fafafa' }}>

      {/* ── Header ── */}
      <header style={{
        position: 'sticky',
        top: 0,
        zIndex: 50,
        padding: '0 32px',
        height: '62px',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'space-between',
        gap: '12px',
        background: 'rgba(255,255,255,0.88)',
        backdropFilter: 'blur(20px)',
        WebkitBackdropFilter: 'blur(20px)',
        borderBottom: '1px solid #e5e7eb',
        boxShadow: '0 1px 8px rgba(0,0,0,0.05)',
      }}>
        <div style={{ display: 'flex', alignItems: 'center', gap: '10px' }}>
          <div style={{
            width: '32px', height: '32px',
            background: 'linear-gradient(135deg, #6366f1, #818cf8)',
            borderRadius: '9px',
            display: 'flex', alignItems: 'center', justifyContent: 'center',
            fontSize: '16px',
            boxShadow: '0 3px 10px rgba(99,102,241,0.26)',
          }}>🎧</div>
          <span style={{
            fontFamily: "'Plus Jakarta Sans', system-ui, sans-serif",
            fontWeight: 800,
            fontSize: '1.05rem',
            background: 'linear-gradient(135deg, #6366f1, #818cf8)',
            WebkitBackgroundClip: 'text',
            WebkitTextFillColor: 'transparent',
            backgroundClip: 'text',
            letterSpacing: '-0.02em',
          }}>
            {t('dashboard_title')}
          </span>
        </div>

        <div style={{ display: 'flex', alignItems: 'center', gap: '12px' }}>
          <LanguageSelector />
          <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'flex-end', gap: '2px' }}>
            <span style={{ color: '#4b5563', fontSize: '0.82rem', fontWeight: 500 }}>{user?.email}</span>
            <button
              onClick={() => setShowPasswordModal(true)}
              style={{
                background: 'none', border: 'none', color: '#6366f1',
                fontSize: '0.7rem', padding: 0, cursor: 'pointer',
                textDecoration: 'underline', fontWeight: 600,
              }}
            >
              {t('change_password', 'Change password')}
            </button>
          </div>
          {user?.is_admin && (
            <Link
              to="/admin"
              className="btn-secondary"
              style={{
                fontSize: '0.8rem', padding: '6px 14px',
                background: '#fffbeb', borderColor: '#fde68a', color: '#d97706',
              }}
            >
              🛡️ Admin Panel
            </Link>
          )}
          <button className="btn-secondary" onClick={logout} style={{ fontSize: '0.875rem' }}>
            {t('logout')}
          </button>
        </div>
      </header>

      {/* ── Main ── */}
      <main style={{ flex: 1, width: '100%', maxWidth: '1100px', margin: '0 auto', padding: '36px 20px 80px' }}>

        {/* ── Stats cards ── */}
        <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit,minmax(200px,1fr))', gap: '14px', marginBottom: '24px' }}>
          {/* Plan */}
          <div style={{
            background: planColor.bg,
            border: `1.5px solid ${planColor.border}`,
            borderRadius: '14px',
            padding: '18px 20px',
            boxShadow: '0 1px 4px rgba(0,0,0,0.05)',
          }}>
            <p style={{ fontSize: '0.72rem', color: '#9ca3af', fontWeight: 600, textTransform: 'uppercase', letterSpacing: '0.07em', marginBottom: '8px' }}>{t('plan')}</p>
            <div style={{ display: 'flex', alignItems: 'center', gap: '8px' }}>
              <h3 style={{ fontSize: '1.1rem', fontWeight: 800, color: '#111827', textTransform: 'capitalize' }}>
                {user?.plan || 'free'}
              </h3>
              <span style={{
                fontSize: '0.64rem', fontWeight: 700, padding: '2px 8px',
                borderRadius: '100px', background: planColor.badge,
                color: '#fff', textTransform: 'uppercase', letterSpacing: '0.06em',
              }}>Active</span>
            </div>
          </div>

          {/* Quota Used */}
          <div style={{
            background: '#fff', border: '1.5px solid #e5e7eb',
            borderRadius: '14px', padding: '18px 20px',
            boxShadow: '0 1px 4px rgba(0,0,0,0.05)',
          }}>
            <p style={{ fontSize: '0.72rem', color: '#9ca3af', fontWeight: 600, textTransform: 'uppercase', letterSpacing: '0.07em', marginBottom: '8px' }}>{t('quota_used')}</p>
            <h3 style={{ fontSize: '1.1rem', fontWeight: 800, color: '#111827' }}>
              {user?.used_today ?? 0}
              <span style={{ fontSize: '0.85rem', color: '#9ca3af', fontWeight: 500 }}> / {user?.daily_quota ?? 0}</span>
            </h3>
            {user?.daily_quota > 0 && (
              <div style={{ marginTop: '10px', height: '4px', background: '#e5e7eb', borderRadius: '99px', overflow: 'hidden' }}>
                <div style={{
                  height: '100%',
                  width: `${Math.min(100, ((user?.used_today ?? 0) / user.daily_quota) * 100)}%`,
                  background: 'linear-gradient(90deg, #6366f1, #818cf8)',
                  borderRadius: '99px',
                  transition: 'width 0.4s ease',
                }} />
              </div>
            )}
          </div>

          {/* Remaining */}
          <div style={{
            background: '#fff', border: '1.5px solid #e5e7eb',
            borderRadius: '14px', padding: '18px 20px',
            boxShadow: '0 1px 4px rgba(0,0,0,0.05)',
          }}>
            <p style={{ fontSize: '0.72rem', color: '#9ca3af', fontWeight: 600, textTransform: 'uppercase', letterSpacing: '0.07em', marginBottom: '8px' }}>{t('quota_remaining')}</p>
            <h3 style={{ fontSize: '1.1rem', fontWeight: 800, color: remainingQuota > 0 ? '#059669' : '#dc2626' }}>
              {remainingQuota}
              <span style={{ fontSize: '0.72rem', color: '#9ca3af', fontWeight: 500 }}> {t('remaining', 'remaining')}</span>
            </h3>
          </div>
        </div>

        {/* ── Pipeline ── */}
        <div style={{
          background: '#fff', border: '1.5px solid #e5e7eb',
          borderRadius: '16px', padding: '16px', marginBottom: '24px',
          boxShadow: '0 1px 4px rgba(0,0,0,0.05)',
        }}>
          <PodcastPipeline embedded onJobCreated={loadJobs} />
        </div>

        {/* ── Recent Jobs ── */}
        <div style={{
          background: '#fff', border: '1.5px solid #e5e7eb',
          borderRadius: '16px', padding: '20px',
          boxShadow: '0 1px 4px rgba(0,0,0,0.05)',
        }}>
          <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between', marginBottom: '16px' }}>
            <h3 style={{ fontSize: '0.95rem', fontWeight: 700, color: '#111827' }}>{t('recent_jobs')}</h3>
            <button className="btn-secondary" onClick={loadJobs} disabled={loadingJobs} style={{ fontSize: '0.8rem', padding: '6px 14px' }}>
              {loadingJobs ? t('loading') : t('refresh')}
            </button>
          </div>

          {jobs.length === 0 ? (
            <div style={{ padding: '32px 0', textAlign: 'center' }}>
              <div style={{ fontSize: '2rem', marginBottom: '10px' }}>🎙️</div>
              <p style={{ color: '#9ca3af', fontSize: '0.875rem', fontWeight: 500 }}>{t('no_jobs')}</p>
            </div>
          ) : (
            <div style={{ display: 'flex', flexDirection: 'column', gap: '2px' }}>
              {jobs.map((job) => (
                <div
                  key={job.id}
                  style={{
                    padding: '12px 14px',
                    borderRadius: '10px',
                    display: 'flex',
                    alignItems: 'center',
                    justifyContent: 'space-between',
                    gap: '12px',
                    transition: 'background 0.15s',
                  }}
                  onMouseEnter={e => e.currentTarget.style.background = '#f9fafb'}
                  onMouseLeave={e => e.currentTarget.style.background = 'transparent'}
                >
                  <div style={{ minWidth: 0 }}>
                    <p style={{ fontWeight: 600, fontSize: '0.875rem', color: '#111827', overflow: 'hidden', textOverflow: 'ellipsis', whiteSpace: 'nowrap' }}>
                      {job.pdf_name}
                    </p>
                    <span style={{
                      display: 'inline-block', marginTop: '4px',
                      fontSize: '0.68rem', fontWeight: 700, padding: '2px 8px',
                      borderRadius: '100px', textTransform: 'uppercase', letterSpacing: '0.06em',
                      background: job.status === 'completed' ? '#dcfce7' : job.status === 'failed' ? '#fee2e2' : '#f3f4f6',
                      color: job.status === 'completed' ? '#16a34a' : job.status === 'failed' ? '#dc2626' : '#6b7280',
                    }}>
                      {job.status}
                    </span>
                  </div>
                  {job.audio_path && <JobAudio jobId={job.id} />}
                </div>
              ))}
            </div>
          )}
        </div>
      </main>

      {/* ── Change Password Modal ── */}
      {showPasswordModal && (
        <div style={{
          position: 'fixed', inset: 0,
          background: 'rgba(0,0,0,0.35)',
          backdropFilter: 'blur(6px)',
          display: 'flex', alignItems: 'center', justifyContent: 'center',
          zIndex: 100, padding: '20px',
        }}>
          <div style={{
            background: '#fff', borderRadius: '20px',
            boxShadow: '0 24px 60px rgba(0,0,0,0.12), 0 8px 20px rgba(0,0,0,0.07)',
            maxWidth: '420px', width: '100%', padding: '32px',
            border: '1.5px solid #e5e7eb',
          }}>
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '24px' }}>
              <h2 style={{ fontSize: '1.3rem', fontWeight: 800, color: '#111827', letterSpacing: '-0.02em' }}>
                {t('change_password', 'Change password')}
              </h2>
              <button
                onClick={() => setShowPasswordModal(false)}
                style={{
                  background: '#f3f4f6', border: 'none', width: '32px', height: '32px',
                  borderRadius: '50%', fontSize: '1.1rem', cursor: 'pointer',
                  color: '#6b7280', display: 'flex', alignItems: 'center', justifyContent: 'center',
                  transition: 'background 0.15s',
                }}
                onMouseEnter={e => e.currentTarget.style.background = '#e5e7eb'}
                onMouseLeave={e => e.currentTarget.style.background = '#f3f4f6'}
              >
                ×
              </button>
            </div>

            <form onSubmit={handleChangePassword}>
              <div style={{ marginBottom: '20px' }}>
                <label style={{
                  display: 'block', fontSize: '0.72rem', color: '#6b7280',
                  marginBottom: '8px', textTransform: 'uppercase', letterSpacing: '0.07em', fontWeight: 700,
                }}>
                  {t('new_password', 'New password')}
                </label>
                <input
                  type="password"
                  className="input"
                  placeholder="Min. 8 characters"
                  value={newPassword}
                  onChange={(e) => setNewPassword(e.target.value)}
                  required
                />
              </div>

              {passwordError && (
                <div style={{
                  background: '#fef2f2', border: '1px solid #fecaca',
                  color: '#dc2626', padding: '10px 14px', borderRadius: '10px',
                  fontSize: '0.83rem', marginBottom: '16px', fontWeight: 500,
                }}>
                  {passwordError}
                </div>
              )}

              {passwordSuccess && (
                <div style={{
                  background: '#f0fdf4', border: '1px solid #bbf7d0',
                  color: '#16a34a', padding: '10px 14px', borderRadius: '10px',
                  fontSize: '0.83rem', marginBottom: '16px', fontWeight: 500,
                }}>
                  {passwordSuccess}
                </div>
              )}

              <button
                type="submit"
                className="btn-primary"
                style={{ width: '100%', padding: '12px' }}
                disabled={passwordLoading}
              >
                {passwordLoading ? t('loading') : t('save_changes', 'Save password')}
              </button>
            </form>
          </div>
        </div>
      )}
    </div>
  );
}
