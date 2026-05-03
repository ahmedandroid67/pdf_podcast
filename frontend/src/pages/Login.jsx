import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { useAuth } from '../context/AuthContext';
import { API_BASE } from '../api';
import LanguageSelector from '../components/LanguageSelector';
import './Login.css';

const STATS = [
  { value: '120K+', key: 'stat_docs' },
  { value: '4.9★', key: 'stat_rating' },
  { value: '40+', key: 'stat_languages' },
];

const TESTIMONIAL = {
  key: 'testimonial_text',
  nameKey: 'testimonial_name',
  roleKey: 'testimonial_role',
  initials: 'LM',
};

const EyeIcon = () => (
  <svg width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
    <path d="M2 12s3-7 10-7 10 7 10 7-3 7-10 7-10-7-10-7Z" />
    <circle cx="12" cy="12" r="3" />
  </svg>
);

const EyeOffIcon = () => (
  <svg width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
    <path d="M9.88 9.88a3 3 0 1 0 4.24 4.24" />
    <path d="M10.73 5.08A10.43 10.43 0 0 1 12 5c7 0 10 7 10 7a13.16 13.16 0 0 1-1.67 2.68" />
    <path d="M6.61 6.61A13.52 13.52 0 0 0 2 12s3 7 10 7a9.74 9.74 0 0 0 5.39-1.61" />
    <line x1="2" y1="2" x2="22" y2="22" />
  </svg>
);

const GoogleIcon = () => (
  <svg width="18" height="18" viewBox="0 0 24 24">
    <path d="M22.56 12.25c0-.78-.07-1.53-.2-2.25H12v4.26h5.92c-.26 1.37-1.04 2.53-2.21 3.31v2.77h3.57c2.08-1.92 3.28-4.74 3.28-8.09z" fill="#4285F4" />
    <path d="M12 23c2.97 0 5.46-.98 7.28-2.66l-3.57-2.77c-.98.66-2.23 1.06-3.71 1.06-2.86 0-5.29-1.93-6.16-4.53H2.18v2.84C3.99 20.53 7.7 23 12 23z" fill="#34A853" />
    <path d="M5.84 14.09c-.22-.66-.35-1.36-.35-2.09s.13-1.43.35-2.09V7.07H2.18C1.43 8.55 1 10.22 1 12s.43 3.45 1.18 4.93l2.85-2.22.81-.62z" fill="#FBBC05" />
    <path d="M12 5.38c1.62 0 3.06.56 4.21 1.64l3.15-3.15C17.45 2.09 14.97 1 12 1 7.7 1 3.99 3.47 2.18 7.07l3.66 2.84c.87-2.6 3.3-4.53 6.16-4.53z" fill="#EA4335" />
  </svg>
);

const WaveformIcon = () => (
  <div className="login-waveform" aria-hidden="true">
    {[8, 20, 14, 28, 18, 10, 24, 16, 6, 22, 12, 26, 10, 18, 8, 22].map((h, i) => (
      <span key={i} style={{ height: `${h}px`, opacity: i > 7 ? 0.3 : 1 }} />
    ))}
  </div>
);

export default function Login() {
  const { t } = useTranslation();
  const { login } = useAuth();
  const navigate = useNavigate();

  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [showPassword, setShowPassword] = useState(false);
  const [rememberMe, setRememberMe] = useState(false);
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const handleGoogleLogin = () => {
    window.location.href = `${API_BASE}/auth/google`;
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    setLoading(true);
    setError('');
    try {
      await login(email, password);
      navigate('/');
    } catch (err) {
      setError(err.message || t('login_failed'));
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="login-root">
      {/* ── Left: brand panel ── */}
      <div className="login-brand-panel">
        <div className="login-brand-glow login-brand-glow--top" aria-hidden="true" />
        <div className="login-brand-glow login-brand-glow--bottom" aria-hidden="true" />

        {/* Logo */}
        <div className="login-logo">
          <div className="login-logo-mark">P</div>
          <span className="login-logo-name">{t('login_brand_name', 'PdfPodcaster')}</span>
        </div>

        {/* Headline */}
        <div className="login-brand-body">
          <p className="login-eyebrow">{t('login_eyebrow', 'Turn reading time into listening time')}</p>
          <h1 className="login-headline">
            {t('login_headline_1', 'Dense docs,')}
            <br />
            <span>{t('login_headline_2', 'clear audio briefings.')}</span>
          </h1>
          <p className="login-brand-sub">
            {t('login_hero_sub', 'Upload a PDF and get a polished spoken summary with multilingual narration built in.')}
          </p>

          {/* Audio preview card */}
          <div className="login-audio-card">
            <div className="login-audio-card-top">
              <div>
                <p className="login-audio-label">{t('login_now_playing', 'Now playing')}</p>
                <p className="login-audio-title">{t('login_preview_file', 'Weekly AI research digest')}</p>
                <p className="login-audio-meta">{t('login_wave_meta', '11 min · English · 1.0×')}</p>
              </div>
              <div className="login-play-btn" aria-hidden="true">
                <div className="login-play-icon" />
              </div>
            </div>
            <WaveformIcon />
            <div className="login-progress-bar">
              <div className="login-progress-fill" />
            </div>
          </div>

          {/* Stats */}
          <div className="login-stats">
            {STATS.map((stat) => (
              <div key={stat.key} className="login-stat">
                <strong>{stat.value}</strong>
                <span>{t(stat.key)}</span>
              </div>
            ))}
          </div>
        </div>

        {/* Testimonial */}
        <blockquote className="login-testimonial">
          <p>"{t(TESTIMONIAL.key)}"</p>
          <footer>
            <div className="login-avatar">{TESTIMONIAL.initials}</div>
            <div>
              <cite>{t(TESTIMONIAL.nameKey)}</cite>
              <span>{t(TESTIMONIAL.roleKey)}</span>
            </div>
          </footer>
        </blockquote>
      </div>

      {/* ── Right: auth panel ── */}
      <aside className="login-auth-panel">
        <div className="login-lang-pos">
          <LanguageSelector />
        </div>

        <div className="login-auth-card">
          {/* Secure badge */}
          <div className="login-secure-badge">
            <span className="login-secure-dot" />
            {t('secure_login', 'Secure login')}
          </div>

          <h2 className="login-form-title">
            {t('login_title_1', 'Welcome')} <span>{t('login_title_2', 'back.')}</span>
          </h2>
          <p className="login-form-sub">
            {t('login_subtitle', 'Sign in to continue your podcast summaries.')}
          </p>

          {/* Google OAuth — primary CTA */}
          <button className="login-google-btn" onClick={handleGoogleLogin} type="button">
            <GoogleIcon />
            {t('continue_google', 'Continue with Google')}
          </button>

          <div className="login-divider">
            <span>{t('continue_email', 'or continue with email')}</span>
          </div>

          <form onSubmit={handleSubmit} noValidate>
            <div className="login-field">
              <label className="login-label" htmlFor="login-email">
                {t('email', 'Email')}
              </label>
              <input
                id="login-email"
                type="email"
                className="login-input"
                placeholder="name@company.com"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                required
                autoComplete="email"
              />
            </div>

            <div className="login-field">
              <label className="login-label" htmlFor="login-password">
                {t('password', 'Password')}
              </label>
              <div className="login-input-wrap">
                <input
                  id="login-password"
                  type={showPassword ? 'text' : 'password'}
                  className="login-input"
                  placeholder={t('login_password_placeholder', 'Enter your password')}
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  required
                  autoComplete="current-password"
                />
                <button
                  type="button"
                  className="login-eye-btn"
                  onClick={() => setShowPassword((v) => !v)}
                  aria-label={showPassword ? t('hide_password', 'Hide password') : t('show_password', 'Show password')}
                >
                  {showPassword ? <EyeOffIcon /> : <EyeIcon />}
                </button>
              </div>
            </div>

            <div className="login-row-between">
              <label className="login-remember">
                <input
                  type="checkbox"
                  checked={rememberMe}
                  onChange={(e) => setRememberMe(e.target.checked)}
                />
                {t('remember_me', 'Remember me')}
              </label>
              <Link to="/forgot-password" className="login-forgot">
                {t('forgot_password', 'Forgot password?')}
              </Link>
            </div>

            {error && (
              <div className="login-error" role="alert">
                <span aria-hidden="true">!</span>
                {error}
              </div>
            )}

            <button type="submit" className="login-submit-btn" disabled={loading}>
              {loading ? <span className="login-spinner" aria-label={t('loading', 'Loading')} /> : t('login_action', 'Sign in')}
            </button>
          </form>

          <p className="login-signup-row">
            {t('no_account', 'New here?')}{' '}
            <Link to="/register">{t('create_free_account', 'Create a free account')}</Link>
          </p>

          <div className="login-trust-row">
            <span>{t('trust_ssl', '256-bit SSL')}</span>
            <span className="login-trust-dot" />
            <span>{t('trust_gdpr', 'GDPR compliant')}</span>
            <span className="login-trust-dot" />
            <span>{t('trust_spam', 'No spam')}</span>
          </div>
        </div>
      </aside>
    </div>
  );
}