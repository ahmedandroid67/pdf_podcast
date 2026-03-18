import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { useAuth } from '../context/AuthContext';

export default function Login() {
  const { t } = useTranslation();
  const { login } = useAuth();
  const navigate = useNavigate();
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (event) => {
    event.preventDefault();
    setLoading(true);
    setError('');
    try {
      await login(email, password);
      navigate('/');
    } catch (e) {
      setError(e.message || t('login_failed'));
    } finally {
      setLoading(false);
    }
  };

  return (
    <div
      style={{
        minHeight: '100vh',
        display: 'grid',
        placeItems: 'center',
        padding: '20px',
        background: 'radial-gradient(circle at 20% 0%, rgba(59,130,246,0.15) 0%, transparent 55%), var(--surface)',
      }}
    >
      <form onSubmit={handleSubmit} className="card" style={{ width: '100%', maxWidth: '420px', padding: '28px' }}>
        <h1 style={{ fontFamily: "'Outfit',sans-serif", fontWeight: 700, fontSize: '1.6rem', marginBottom: '6px' }}>
          {t('login_title')}
        </h1>
        <p style={{ color: 'var(--text-3)', fontSize: '0.9rem', marginBottom: '18px' }}>
          {t('login_subtitle')}
        </p>
        <label style={{ fontSize: '0.8rem', color: 'var(--text-2)' }}>{t('email')}</label>
        <input
          type="email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          className="input-field"
          required
        />
        <label style={{ fontSize: '0.8rem', color: 'var(--text-2)', marginTop: '12px' }}>{t('password')}</label>
        <input
          type="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          className="input-field"
          required
        />
        {error && (
          <div style={{ marginTop: '12px', color: '#f87171', fontSize: '0.85rem' }}>
            {error}
          </div>
        )}
        <button className="btn-primary" style={{ width: '100%', marginTop: '18px' }} disabled={loading}>
          {loading ? t('loading') : t('login_action')}
        </button>
        <p style={{ marginTop: '14px', fontSize: '0.8rem', color: 'var(--text-3)' }}>
          {t('no_account')} <Link to="/register">{t('register_action')}</Link>
        </p>
      </form>
    </div>
  );
}
