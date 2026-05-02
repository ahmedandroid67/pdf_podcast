import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { useAuth } from '../context/AuthContext';
import LanguageSelector from '../components/LanguageSelector';

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
    <div style={{
      minHeight: '100vh',
      display: 'grid',
      placeItems: 'center',
      padding: '20px',
      background: 'radial-gradient(circle at 60% 10%, rgba(99,102,241,0.06) 0%, transparent 55%), #fafafa',
    }}>
      <form
        onSubmit={handleSubmit}
        style={{
          background: '#fff',
          border: '1.5px solid #e5e7eb',
          borderRadius: '20px',
          padding: '36px 32px',
          width: '100%',
          maxWidth: '420px',
          position: 'relative',
          boxShadow: '0 8px 32px rgba(0,0,0,0.07), 0 2px 8px rgba(0,0,0,0.04)',
        }}
      >
        <div style={{ position: 'absolute', top: '16px', right: '16px' }}>
          <LanguageSelector />
        </div>

        <div style={{ display: 'flex', alignItems: 'center', gap: '10px', marginBottom: '24px' }}>
          <div style={{
            width: '38px', height: '38px',
            background: 'linear-gradient(135deg, #6366f1, #818cf8)',
            borderRadius: '10px',
            display: 'flex', alignItems: 'center', justifyContent: 'center',
            fontSize: '18px',
            boxShadow: '0 3px 10px rgba(99,102,241,0.26)',
          }}>🎙️</div>
          <span style={{
            fontFamily: "'Plus Jakarta Sans', system-ui, sans-serif",
            fontWeight: 800, fontSize: '1.1rem', color: '#111827', letterSpacing: '-0.02em',
          }}>PdfParser</span>
        </div>

        <h1 style={{
          fontFamily: "'Plus Jakarta Sans', system-ui, sans-serif",
          fontWeight: 800, fontSize: '1.55rem',
          marginBottom: '6px', color: '#111827', letterSpacing: '-0.02em',
        }}>
          {t('register_title')}
        </h1>
        <p style={{ color: '#9ca3af', fontSize: '0.875rem', marginBottom: '24px', fontWeight: 500 }}>
          {t('register_subtitle')}
        </p>

        <label style={{
          display: 'block', fontSize: '0.72rem', color: '#6b7280',
          marginBottom: '7px', fontWeight: 700, textTransform: 'uppercase', letterSpacing: '0.07em',
        }}>
          {t('email')}
        </label>
        <input
          type="email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          className="input-field"
          placeholder="name@company.com"
          style={{ marginBottom: '14px' }}
          required
        />

        <label style={{
          display: 'block', fontSize: '0.72rem', color: '#6b7280',
          marginBottom: '7px', fontWeight: 700, textTransform: 'uppercase', letterSpacing: '0.07em',
        }}>
          {t('password')}
        </label>
        <input
          type="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          className="input-field"
          placeholder="Min. 8 characters"
          minLength={8}
          required
        />

        {error && (
          <div style={{
            marginTop: '14px',
            background: '#fef2f2', border: '1px solid #fecaca',
            color: '#dc2626', padding: '10px 14px',
            borderRadius: '10px', fontSize: '0.83rem', fontWeight: 500,
          }}>
            {error}
          </div>
        )}

        <button
          className="btn-primary"
          style={{ width: '100%', marginTop: '20px', padding: '13px' }}
          disabled={loading}
        >
          {loading ? t('loading') : t('register_action')}
        </button>

        <p style={{ marginTop: '16px', fontSize: '0.82rem', color: '#9ca3af', textAlign: 'center', fontWeight: 500 }}>
          {t('have_account')}{' '}
          <Link to="/login" style={{ color: '#6366f1', fontWeight: 700, textDecoration: 'none' }}>
            {t('login_action')}
          </Link>
        </p>
      </form>
    </div>
  );
}
