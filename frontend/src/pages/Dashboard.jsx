import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { API_BASE, fetchJobs } from '../api';
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
          <span style={{ color: 'var(--text-3)', fontSize: '0.85rem' }}>{user?.email}</span>
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
    </div>
  );
}
