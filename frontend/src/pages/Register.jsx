import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { useAuth } from '../context/AuthContext';

export default function Register() {
  const { t } = useTranslation();
  const { register } = useAuth();
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
      await register(email, password);
      navigate('/');
    } catch (e) {
      setError(e.message || t('register_failed'));
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
        background: 'radial-gradient(circle at 80% 0%, rgba(234,179,8,0.16) 0%, transparent 55%), var(--surface)',
      }}
    >
      <form onSubmit={handleSubmit} className="card" style={{ width: '100%', maxWidth: '420px', padding: '28px' }}>
        <h1 style={{ fontFamily: "'Outfit',sans-serif", fontWeight: 700, fontSize: '1.6rem', marginBottom: '6px' }}>
          {t('register_title')}
        </h1>
        <p style={{ color: 'var(--text-3)', fontSize: '0.9rem', marginBottom: '18px' }}>
          {t('register_subtitle')}
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
          minLength={8}
          required
        />
        {error && (
          <div style={{ marginTop: '12px', color: '#f87171', fontSize: '0.85rem' }}>
            {error}
          </div>
        )}
        <button className="btn-primary" style={{ width: '100%', marginTop: '18px' }} disabled={loading}>
          {loading ? t('loading') : t('register_action')}
        </button>
        <p style={{ marginTop: '14px', fontSize: '0.8rem', color: 'var(--text-3)' }}>
          {t('have_account')} <Link to="/login">{t('login_action')}</Link>
        </p>
      </form>
    </div>
  );
}
