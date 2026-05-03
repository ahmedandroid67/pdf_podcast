import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { useAuth } from '../context/AuthContext';
import { API_BASE } from '../api';
import LanguageSelector from '../components/LanguageSelector';
import './Login.css';

const STATS = [
  { value: '120K+', key: 'stat_docs' },
  { value: '4.9+', key: 'stat_rating' },
  { value: '40+', key: 'stat_languages' },
];

const WORKFLOW = [
  {
    step: '01',
    title: 'Upload any PDF',
    copy: 'Drop reports, class notes, manuals, or research papers into one clean workspace.',
  },
  {
    step: '02',
    title: 'AI extracts the key ideas',
    copy: 'The pipeline summarizes, structures, and adapts the content into a listener-friendly script.',
  },
  {
    step: '03',
    title: 'Listen like a podcast',
    copy: 'Get a polished audio briefing with language, voice, and pacing controls.',
  },
];

const PREVIEW_POINTS = [
  'Narration ready in under 30 seconds',
  'Multi-language voice output',
  'Progress tracking while audio renders',
];

const PLANS = [
  { id: 'free', price: '$0', nameKey: 'plan_name_free' },
  { id: 'basic', price: '$7', nameKey: 'plan_name_basic' },
  { id: 'pro', price: '$19', nameKey: 'plan_name_pro' },
  { id: 'business', price: '$69', nameKey: 'plan_name_business' },
];

const TESTIMONIAL = {
  key: 'testimonial_text',
  nameKey: 'testimonial_name',
  roleKey: 'testimonial_role',
  initials: 'LM',
};

const EyeIcon = () => (
  <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
    <path d="M2 12s3-7 10-7 10 7 10 7-3 7-10 7-10-7-10-7Z" />
    <circle cx="12" cy="12" r="3" />
  </svg>
);

const EyeOffIcon = () => (
  <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
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

const AudioBars = () => (
  <div className="lp-audio-bars" aria-hidden="true">
    <span />
    <span />
    <span />
    <span />
    <span />
    <span />
    <span />
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

  const handleSubmit = async (e) => {
    e.preventDefault();
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
    <div className="lp-root">
      <div className="lp-background" aria-hidden="true">
        <div className="lp-orb lp-orb-one" />
        <div className="lp-orb lp-orb-two" />
        <div className="lp-grid-glow" />
      </div>

      <section className="lp-hero">
        <div className="lp-topbar fade-in d100">
          <div className="lp-brand">
            <div className="lp-brand-mark">P</div>
            <div className="lp-brand-copy">
              <span className="lp-brand-name">PdfParser</span>
              <span className="lp-brand-tag">Audio-first document summaries</span>
            </div>
          </div>
          <div className="lp-hero-chip">Trusted by researchers, operators, and students</div>
        </div>

        <div className="lp-hero-copy fade-in d200">
          <p className="lp-eyebrow">Turn reading time into listening time</p>
          <h1 className="lp-headline">
            Dense documents, <span>clear audio briefings.</span>
          </h1>
          <p className="lp-sub">
            Upload a PDF and get a polished spoken summary with structure, pacing, and multilingual narration built in.
          </p>
        </div>

        <div className="lp-hero-main">
          <div className="lp-preview-card fade-in d300">
            <div className="lp-preview-top">
              <div>
                <p className="lp-preview-label">Live product preview</p>
                <h2>Weekly AI research digest.pdf</h2>
              </div>
              <div className="lp-preview-status">Processing</div>
            </div>

            <div className="lp-preview-body">
              <div className="lp-doc-card">
                <div className="lp-doc-icon">PDF</div>
                <div>
                  <p className="lp-doc-title">Document uploaded</p>
                  <p className="lp-doc-meta">84 pages • English • Academic paper</p>
                </div>
              </div>

              <div className="lp-flow-row">
                <div className="lp-flow-node lp-flow-node-active">
                  <span>1</span>
                  <strong>Extract</strong>
                </div>
                <div className="lp-flow-line" />
                <div className="lp-flow-node lp-flow-node-active">
                  <span>2</span>
                  <strong>Summarize</strong>
                </div>
                <div className="lp-flow-line" />
                <div className="lp-flow-node">
                  <span>3</span>
                  <strong>Narrate</strong>
                </div>
              </div>

              <div className="lp-wave-card">
                <div className="lp-wave-copy">
                  <p className="lp-wave-label">Podcast output</p>
                  <h3>Executive audio brief</h3>
                  <span>11 min listen • warm voice • 1.0x speed</span>
                </div>
                <AudioBars />
              </div>

              <div className="lp-preview-points">
                {PREVIEW_POINTS.map((item) => (
                  <div key={item} className="lp-point">
                    <span className="lp-point-dot" />
                    {item}
                  </div>
                ))}
              </div>
            </div>
          </div>

          <div className="lp-side-stack">
            <div className="lp-stats-card fade-in d400">
              {STATS.map((stat) => (
                <div key={stat.key} className="lp-stat">
                  <strong>{stat.value}</strong>
                  <span>{t(stat.key)}</span>
                </div>
              ))}
            </div>

            <div className="lp-workflow-card fade-in d500">
              <p className="lp-section-kicker">How it works</p>
              <div className="lp-workflow-list">
                {WORKFLOW.map((item) => (
                  <div key={item.step} className="lp-workflow-item">
                    <div className="lp-workflow-step">{item.step}</div>
                    <div>
                      <h3>{item.title}</h3>
                      <p>{item.copy}</p>
                    </div>
                  </div>
                ))}
              </div>
            </div>

            <div className="lp-quote-card fade-in d600">
              <p className="lp-quote-text">{t(TESTIMONIAL.key)}</p>
              <div className="lp-quote-author">
                <div className="lp-avatar">{TESTIMONIAL.initials}</div>
                <div>
                  <strong>{t(TESTIMONIAL.nameKey)}</strong>
                  <span>{t(TESTIMONIAL.roleKey)}</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>

      <aside className="lp-auth-shell">
        <div className="lp-lang-pos">
          <LanguageSelector />
        </div>

        <div className="lp-auth-card">
          <div className="lp-form-tag fade-in d100">
            <div className="lp-form-tag-dot" />
            {t('secure_login', 'Secure login')}
          </div>

          <div className="fade-in d200">
            <h2 className="lp-form-title">
              {t('login_title_1', 'Welcome')} <span>{t('login_title_2', 'back.')}</span>
            </h2>
            <p className="lp-form-subtitle">
              {t('login_subtitle', 'Sign in to continue your podcast summaries.')}
            </p>
          </div>

          <button className="lp-google-btn fade-in d300" onClick={handleGoogleLogin}>
            <GoogleIcon />
            {t('continue_google', 'Continue with Google')}
          </button>

          <div className="lp-divider fade-in d400">
            <span>{t('continue_email', 'or continue with email')}</span>
          </div>

          <form onSubmit={handleSubmit}>
            <div className="lp-field fade-in d400">
              <label className="lp-label" htmlFor="lp-email">{t('email', 'Email')}</label>
              <div className="lp-input-wrap">
                <input
                  id="lp-email"
                  type="email"
                  className="lp-input"
                  placeholder="name@company.com"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  required
                  autoComplete="email"
                />
              </div>
            </div>

            <div className="lp-field fade-in d500">
              <label className="lp-label" htmlFor="lp-pw">{t('password', 'Password')}</label>
              <div className="lp-input-wrap">
                <input
                  id="lp-pw"
                  type={showPassword ? 'text' : 'password'}
                  className="lp-input"
                  placeholder="Enter your password"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  required
                  autoComplete="current-password"
                />
                <button
                  type="button"
                  className="lp-input-icon"
                  onClick={() => setShowPassword((value) => !value)}
                  aria-label={showPassword ? 'Hide password' : 'Show password'}
                >
                  {showPassword ? <EyeOffIcon /> : <EyeIcon />}
                </button>
              </div>
            </div>

            <div className="lp-bottom-row fade-in d500">
              <label className="lp-remember">
                <input type="checkbox" checked={rememberMe} onChange={(e) => setRememberMe(e.target.checked)} />
                {t('remember_me', 'Remember me')}
              </label>
              <Link to="#" className="lp-forgot">{t('forgot_password', 'Forgot password?')}</Link>
            </div>

            {error ? <div className="lp-error"><span>!</span>{error}</div> : null}

            <button type="submit" className="lp-submit fade-in d600" disabled={loading}>
              {loading ? <span className="lp-spinner" /> : t('login_action', 'Sign in')}
            </button>
          </form>

          <p className="lp-signup fade-in d700">
            {t('no_account', 'New here?')}{' '}
            <Link to="/register">{t('create_free_account', 'Create a free account')}</Link>
          </p>

          <div className="lp-trust fade-in d700">
            <span className="lp-trust-item">256-bit SSL</span>
            <span className="lp-trust-dot" />
            <span className="lp-trust-item">GDPR compliant</span>
            <span className="lp-trust-dot" />
            <span className="lp-trust-item">No spam</span>
          </div>
        </div>

        <div className="lp-plan-teaser fade-in d700">
          <div className="lp-plan-copy">
            <p className="lp-section-kicker">Flexible pricing</p>
            <h3>Start free, upgrade when the workflow clicks.</h3>
            <span>Paid plans begin at $7/month and scale up to team usage.</span>
          </div>
          <div className="lp-plan-pills">
            {PLANS.map((plan) => (
              <div key={plan.id} className={`lp-plan-pill${plan.id === 'pro' ? ' lp-plan-pill-featured' : ''}`}>
                <strong>{plan.price}</strong>
                <span>{t(plan.nameKey)}</span>
              </div>
            ))}
          </div>
        </div>
      </aside>
    </div>
  );
}
