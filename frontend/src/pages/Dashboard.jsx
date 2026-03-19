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
      if (!res.ok) {
        return;
      }
      const blob = await res.blob();
      objectUrl = URL.createObjectURL(blob);
      if (active) {
        setAudioUrl(objectUrl);
      }
    };

    load();

    return () => {
      active = false;
      if (objectUrl) {
        URL.revokeObjectURL(objectUrl);
      }
    };
  }, [jobId]);

  if (!audioUrl) {
    return null;
  }

  return <audio controls src={audioUrl} style={{ width: '240px' }} />;
}

export default function Dashboard() {
  const { t } = useTranslation();
  const { user, logout } = useAuth();
  const [jobs, setJobs] = useState([]);
  const [loadingJobs, setLoadingJobs] = useState(false);
  
  // Password change states
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

  useEffect(() => {
    loadJobs();
  }, []);

  return (
    <div
      style={{
        minHeight: '100vh',
        display: 'flex',
        flexDirection: 'column',
        background: 'radial-gradient(ellipse at 15% 0%, rgba(16,185,129,0.12) 0%, transparent 55%), var(--surface)',
      }}
    >
      <header
        className="glass"
        style={{
          position: 'sticky',
          top: 0,
          zIndex: 50,
          padding: '14px 32px',
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'space-between',
          gap: '12px',
          borderRadius: 0,
          borderTop: 'none',
          borderLeft: 'none',
          borderRight: 'none',
        }}
      >
        <div style={{ display: 'flex', alignItems: 'center', gap: '10px' }}>
          <span style={{ fontSize: '1.4rem' }} aria-hidden="true">🎧</span>
          <span style={{ fontFamily: "'Outfit',sans-serif", fontWeight: 800, fontSize: '1.1rem' }} className="gradient-text">
            {t('dashboard_title')}
          </span>
        </div>
        <div style={{ display: 'flex', alignItems: 'center', gap: '12px' }}>
          <LanguageSelector />
          <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'flex-end', gap: '2px' }}>
            <span style={{ color: 'var(--text-3)', fontSize: '0.85rem' }}>{user?.email}</span>
            <button 
              onClick={() => setShowPasswordModal(true)} 
              style={{ background: 'none', border: 'none', color: 'var(--accent)', fontSize: '0.72rem', padding: 0, cursor: 'pointer', textDecoration: 'underline' }}
            >
              {t('change_password', 'Change password')}
            </button>
          </div>
          {user?.is_admin && (
            <Link to="/admin" className="btn-secondary" style={{ fontSize: '0.8rem', padding: '6px 14px', background: 'rgba(245,158,11,0.1)', borderColor: 'rgba(245,158,11,0.3)', color: '#f59e0b' }}>
              🛡️ Admin Panel
            </Link>
          )}
          <button className="btn-secondary" onClick={logout}>
            {t('logout')}
          </button>
        </div>
      </header>

      <main style={{ flex: 1, width: '100%', maxWidth: '1100px', margin: '0 auto', padding: '36px 20px 80px' }}>
        <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit,minmax(240px,1fr))', gap: '16px', marginBottom: '24px' }}>
          <div className="card" style={{ padding: '18px' }}>
            <p style={{ fontSize: '0.78rem', color: 'var(--text-3)' }}>{t('plan')}</p>
            <h3 style={{ fontSize: '1.1rem', fontWeight: 700, marginTop: '6px' }}>
              {user?.plan || 'free'}
            </h3>
          </div>
          <div className="card" style={{ padding: '18px' }}>
            <p style={{ fontSize: '0.78rem', color: 'var(--text-3)' }}>{t('quota_used')}</p>
            <h3 style={{ fontSize: '1.1rem', fontWeight: 700, marginTop: '6px' }}>
              {user?.used_today ?? 0} / {user?.daily_quota ?? 0}
            </h3>
          </div>
          <div className="card" style={{ padding: '18px' }}>
            <p style={{ fontSize: '0.78rem', color: 'var(--text-3)' }}>{t('quota_remaining')}</p>
            <h3 style={{ fontSize: '1.1rem', fontWeight: 700, marginTop: '6px' }}>
              {remainingQuota}
            </h3>
          </div>
        </div>

        <div className="card" style={{ padding: '16px', marginBottom: '24px' }}>
          <PodcastPipeline embedded onJobCreated={loadJobs} />
        </div>

        <div className="card" style={{ padding: '18px' }}>
          <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between', marginBottom: '12px' }}>
            <h3 style={{ fontSize: '1rem', fontWeight: 700 }}>{t('recent_jobs')}</h3>
            <button className="btn-secondary" onClick={loadJobs} disabled={loadingJobs}>
              {loadingJobs ? t('loading') : t('refresh')}
            </button>
          </div>
          {jobs.length === 0 && (
            <p style={{ color: 'var(--text-3)', fontSize: '0.85rem' }}>{t('no_jobs')}</p>
          )}
          {jobs.map((job) => (
            <div
              key={job.id}
              style={{
                padding: '12px',
                borderBottom: '1px solid var(--border)',
                display: 'flex',
                alignItems: 'center',
                justifyContent: 'space-between',
                gap: '12px',
              }}
            >
              <div>
                <p style={{ fontWeight: 600, fontSize: '0.9rem' }}>{job.pdf_name}</p>
                <p style={{ color: 'var(--text-3)', fontSize: '0.75rem' }}>{job.status}</p>
              </div>
              {job.audio_path && <JobAudio jobId={job.id} />}
            </div>
          ))}
        </div>
      </main>

      {/* Change Password Modal */}
      {showPasswordModal && (
        <div style={{
          position: 'fixed',
          inset: 0,
          background: 'rgba(0,0,0,0.85)',
          backdropFilter: 'blur(8px)',
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'center',
          zIndex: 100,
          padding: '20px'
        }}>
          <div className="card glass d700 fade-in" style={{ maxWidth: '400px', width: '100%', padding: '32px' }}>
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '24px' }}>
              <h2 style={{ fontSize: '1.4rem', fontWeight: 700 }}>{t('change_password', 'Change password')}</h2>
              <button 
                onClick={() => setShowPasswordModal(false)}
                style={{ background: 'none', border: 'none', fontSize: '1.4rem', cursor: 'pointer', color: 'var(--text-3)' }}
              >
                &times;
              </button>
            </div>
            
            <form onSubmit={handleChangePassword}>
              <div style={{ marginBottom: '20px' }}>
                <label style={{ display: 'block', fontSize: '0.8rem', color: 'var(--text-3)', marginBottom: '8px', textTransform: 'uppercase', letterSpacing: '0.05em' }}>
                  {t('new_password', 'New password')}
                </label>
                <input 
                  type="password"
                  className="input"
                  style={{ width: '100%' }}
                  placeholder="********"
                  value={newPassword}
                  onChange={(e) => setNewPassword(e.target.value)}
                  required
                />
              </div>

              {passwordError && (
                <div style={{ background: 'rgba(239,68,68,0.1)', border: '1px solid rgba(239,68,68,0.2)', color: '#f87171', padding: '10px', borderRadius: '8px', fontSize: '0.85rem', marginBottom: '16px' }}>
                  {passwordError}
                </div>
              )}

              {passwordSuccess && (
                <div style={{ background: 'rgba(16,185,129,0.1)', border: '1px solid rgba(16,185,129,0.2)', color: '#34d399', padding: '10px', borderRadius: '8px', fontSize: '0.85rem', marginBottom: '16px' }}>
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
