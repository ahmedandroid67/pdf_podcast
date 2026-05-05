import { useState, useMemo } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { useAuth } from '../context/AuthContext';
import LanguageSelector from '../components/LanguageSelector';
import ThemeToggle from '../components/ThemeToggle';

function getPasswordStrength(password) {
  if (!password) return { score: 0, label: '', color: '#d1d5db' };
  let score = 0;
  if (password.length >= 8) score++;
  if (password.length >= 12) score++;
  if (/[A-Z]/.test(password)) score++;
  if (/[0-9]/.test(password)) score++;
  if (/[^A-Za-z0-9]/.test(password)) score++;
  if (score <= 1) return { score, label: 'Weak', color: '#ef4444' };
  if (score <= 3) return { score, label: 'Fair', color: '#f59e0b' };
  return { score, label: 'Strong', color: '#22c55e' };
}

export default function Register() {
  const { t } = useTranslation();
  const { register } = useAuth();
  const navigate = useNavigate();
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const strength = useMemo(() => getPasswordStrength(password), [password]);

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
      background: 'var(--surface)',
    }}>
      <form
        onSubmit={handleSubmit}
        style={{
          background: 'var(--surface-2)',
          border: '1.5px solid var(--border)',
          borderRadius: '20px',
          padding: '36px 32px',
          width: '100%',
          maxWidth: '420px',
          position: 'relative',
          boxShadow: 'var(--shadow-md)',
        }}
      >
        <div style={{ position: 'absolute', top: '16px', right: '16px', display: 'flex', alignItems: 'center', gap: '10px' }}>
          <ThemeToggle />
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
            fontWeight: 800, fontSize: '1.1rem', color: 'var(--text-1)', letterSpacing: '-0.02em',
          }}>PdfPodcaster</span>
        </div>

        <label style={{
          display: 'block', fontSize: '0.72rem', color: 'var(--text-2)',
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
          display: 'block', fontSize: '0.72rem', color: 'var(--text-2)',
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
        {password && (
          <div style={{ marginTop: '8px', marginBottom: '6px' }}>
            <div style={{ display: 'flex', gap: '4px', marginBottom: '4px' }}>
              {[1, 2, 3, 4, 5].map((i) => (
                <div
                  key={i}
                  style={{
                    flex: 1,
                    height: '4px',
                    borderRadius: '2px',
                    background: i <= strength.score ? strength.color : 'var(--border)',
                    transition: 'background 0.2s',
                  }}
                />
              ))}
            </div>
            <span style={{ fontSize: '0.72rem', fontWeight: 600, color: strength.color }}>
              {strength.label}
            </span>
          </div>
        )}

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

        <p style={{ marginTop: '16px', fontSize: '0.82rem', color: 'var(--text-3)', textAlign: 'center', fontWeight: 500 }}>
          {t('have_account')}{' '}
          <Link to="/login" style={{ color: 'var(--brand)', fontWeight: 700, textDecoration: 'none' }}>
            {t('login_action')}
          </Link>
        </p>
      </form>
    </div>
  );
}
