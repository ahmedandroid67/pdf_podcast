import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { useAuth } from '../context/AuthContext';
import { API_BASE } from '../api';
import LanguageSelector from '../components/LanguageSelector';
import './Login.css';

/* ─── static data ───────────────────────────────────────────────────────────── */
const FEATURES = [
  { icon: '🎙️', key: 'feat_narration' },
  { icon: '⚡', key: 'feat_speed' },
  { icon: '🌍', key: 'feat_multi' },
  { icon: '🎚️', key: 'feat_config' },
];

const STATS = [
  { num: '120K+', key: 'stat_docs' },
  { num: '4.9★', key: 'stat_rating' },
  { num: '40+', key: 'stat_languages' },
];

const TESTIMONIAL = {
  key: "testimonial_text",
  nameKey: 'testimonial_name',
  roleKey: 'testimonial_role',
  initials: 'LM',
  avatarBg: 'linear-gradient(135deg,#a855f7,#6366f1)',
};

const PLANS = [
  {
    id: 'free',
    icon: '🆓',
    nameKey: 'plan_name_free',
    tagKey: 'plan_free_tag',
    price: '$0',
    periodKey: 'plan_period_forever',
    color: 'plan-free',
    highlight: false,
    features: [
      { icon: '📄', key: 'plan_feat_1_doc' },
      { icon: '📑', key: 'plan_feat_10_pages' },
      { icon: '📦', key: 'plan_feat_5_mb' },
      { icon: '🧠', key: 'plan_feat_basic_ai' },
      { icon: '🔇', key: 'plan_feat_no_audio' },
      { icon: '🗂️', key: 'plan_feat_3_jobs' },
      { icon: '⏳', key: 'plan_feat_24h' },
      { icon: '🐢', key: 'plan_feat_queue' },
    ],
    limitKey: 'plan_limit_free',
  },
  {
    id: 'basic',
    icon: '💡',
    nameKey: 'plan_name_basic',
    tagKey: 'plan_basic_tag',
    price: '$7',
    periodKey: 'plan_period_month',
    color: 'plan-basic',
    highlight: false,
    features: [
      { icon: '📄', key: 'plan_feat_10_docs' },
      { icon: '📑', key: 'plan_feat_50_pages' },
      { icon: '📦', key: 'plan_feat_15_mb' },
      { icon: '🎧', key: 'plan_feat_audio' },
      { icon: '🗂️', key: 'plan_feat_30_history' },
      { icon: '⏳', key: 'plan_feat_7d' },
      { icon: '⚡', key: 'plan_feat_fast' },
    ],
    limitKey: 'plan_limit_basic',
  },
  {
    id: 'pro',
    icon: '🚀',
    nameKey: 'plan_name_pro',
    tagKey: 'plan_pro_tag',
    price: '$19',
    periodKey: 'plan_period_month',
    color: 'plan-pro',
    highlight: true,
    features: [
      { icon: '📄', key: 'plan_feat_50_docs' },
      { icon: '📑', key: 'plan_feat_150_pages' },
      { icon: '📦', key: 'plan_feat_30_mb' },
      { icon: '🧠', key: 'plan_feat_all_ai' },
      { icon: '🎧', key: 'plan_feat_hq_audio' },
      { icon: '🌍', key: 'plan_feat_multi_priority' },
      { icon: '🗂️', key: 'plan_feat_unlimited_history' },
      { icon: '⏳', key: 'plan_feat_30d' },
      { icon: '⚡', key: 'plan_feat_priority' },
    ],
    limitKey: 'plan_limit_pro',
  },
  {
    id: 'business',
    icon: '🏢',
    nameKey: 'plan_name_business',
    tagKey: 'plan_business_tag',
    price: '$69',
    periodKey: 'plan_period_month',
    color: 'plan-business',
    highlight: false,
    features: [
      { icon: '📄', key: 'plan_feat_unlimited_docs' },
      { icon: '📑', key: 'plan_feat_300_pages' },
      { icon: '📦', key: 'plan_feat_100_mb' },
      { icon: '🧠', key: 'plan_feat_full_ai' },
      { icon: '🎙️', key: 'plan_feat_premium_voices' },
      { icon: '🧑‍🤝‍🧑', key: 'plan_feat_team' },
      { icon: '📊', key: 'plan_feat_analytics' },
      { icon: '🗂️', key: 'plan_feat_unlimited_storage' },
      { icon: '⚡', key: 'plan_feat_highest' },
    ],
    limitKey: 'plan_limit_custom',
  },
];

/* ─── icons ─────────────────────────────────────────────────────────────────── */
const EyeIcon = () => (
  <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
    <path d="M2 12s3-7 10-7 10 7 10 7-3 7-10 7-10-7-10-7Z" /><circle cx="12" cy="12" r="3" />
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

/* ─── component ─────────────────────────────────────────────────────────────── */
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
  const [activePlan, setActivePlan] = useState('pro');

  const handleGoogleLogin = () => { window.location.href = `${API_BASE}/auth/google`; };

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
      <div className="lp-mesh" aria-hidden="true" />
      <div className="lp-grain" aria-hidden="true" />

      {/* ══════════════ LEFT PANEL ══════════════ */}
      <div className="lp-left lp-pricing-side">

        {/* ── brand ── */}
        <div className="lp-brand fade-in d100">
          <div className="lp-brand-icon">🎙️</div>
          <div className="lp-brand-name">Pdf<em>Parser</em></div>
        </div>

        {/* ── hero headline ── */}
        <h1 className="lp-headline fade-in d200">
          {t('hero_headline_1', 'Your docs,')}<br /><em>{t('hero_headline_2', 'spoken aloud.')}</em>
        </h1>
        <p className="lp-sub fade-in d300">
          {t('hero_description', 'Drop any document. Our AI extracts the key insights and narrates them as a polished, podcast-style audio — in seconds.')}
        </p>

        {/* ── stats ── */}
        <div className="lp-stats fade-in d300">
          {STATS.map((s) => (
            <div key={s.key} className="lp-stat">
              <div className="lp-stat-num">{s.num}</div>
              <div className="lp-stat-label">{t(s.key)}</div>
            </div>
          ))}
        </div>

        {/* ── features ── */}
        <div className="lp-features fade-in d400">
          {FEATURES.map((f) => (
            <div key={f.key} className="lp-feat">
              <div className="lp-feat-icon">{f.icon}</div>
              <span>{t(f.key)}</span>
            </div>
          ))}
        </div>

        {/* ── testimonial ── */}
        <div className="lp-testimonial fade-in d500">
          <p className="lp-testimonial-text">{t(TESTIMONIAL.key)}</p>
          <div className="lp-testimonial-author">
            <div className="lp-avatar" style={{ background: TESTIMONIAL.avatarBg }}>
              {TESTIMONIAL.initials}
            </div>
            <div>
              <div className="lp-author-name">{t(TESTIMONIAL.nameKey)}</div>
              <div className="lp-author-role">{t(TESTIMONIAL.roleKey)}</div>
            </div>
          </div>
        </div>

        {/* ══ PRICING SECTION ══ */}
        <div className="lp-pricing-block fade-in d500">

          <div className="lp-section-divider">
            <span>💼 {t('choose_plan', 'Choose your plan')}</span>
          </div>

          <div className="lp-pricing-header">
            <h2 className="lp-pricing-title">
              {t('pricing_title_1', 'Simple,')}<br /><em>{t('pricing_title_2', 'honest pricing.')}</em>
            </h2>
            <p className="lp-pricing-sub">
              {t('pricing_subtitle', 'Start free. Upgrade when you need more power. Cancel any time.')}
            </p>
          </div>

          <div className="lp-plans">
            {PLANS.map((plan) => (
              <button
                key={plan.id}
                className={[
                  'lp-plan-card',
                  plan.color,
                  plan.highlight ? 'lp-plan-highlight' : '',
                  activePlan === plan.id ? 'lp-plan-active' : '',
                ].join(' ')}
                onClick={() => setActivePlan(plan.id)}
              >
                {/* card header */}
                <div className="lp-plan-top">
                  <div className="lp-plan-left">
                    <span className="lp-plan-icon">{plan.icon}</span>
                    <div>
                      <div className="lp-plan-name">{t(plan.nameKey)}</div>
                      <div className="lp-plan-tag">{t(plan.tagKey)}</div>
                    </div>
                  </div>
                  <div className="lp-plan-price-wrap">
                    <span className="lp-plan-price">{plan.price}</span>
                    <span className="lp-plan-period">{t(plan.periodKey)}</span>
                  </div>
                </div>

                {/* expandable features */}
                <div className={`lp-plan-features ${activePlan === plan.id ? 'lp-plan-features-open' : ''}`}>
                  <div className="lp-plan-features-inner">
                    {plan.features.map((f) => (
                      <div key={f.key} className="lp-plan-feat">
                        <span className="lp-plan-feat-icon">{f.icon}</span>
                        <span>{t(f.key)}</span>
                      </div>
                    ))}
                    <div className="lp-plan-limit">
                      <span>🔢</span> {t(plan.limitKey)}
                    </div>
                  </div>
                </div>

                {activePlan === plan.id && <div className="lp-plan-selected-bar" />}
              </button>
            ))}
          </div>

          <p className="lp-pricing-note">
            ✅ {t('pricing_note_free', 'No credit card required for Free')} &nbsp;·&nbsp; 🔒 {t('pricing_note_cancel', 'Cancel anytime')}
          </p>

        </div>
        {/* ══ END PRICING ══ */}

      </div>
      {/* ══════════════ END LEFT ══════════════ */}

      {/* ══════════════ RIGHT PANEL ══════════════ */}
      <div className="lp-right">
        <div className="lp-form-wrap">

          <div style={{ position: 'absolute', top: '20px', right: '20px' }}>
             <LanguageSelector />
          </div>

          <div className="lp-form-tag fade-in d200">
            <div className="lp-form-tag-dot" />
            {t('secure_login', 'Secure login')}
          </div>

          <h2 className="lp-form-title fade-in d300">
            {t('login_title_1', 'Welcome')}<br /><em>{t('login_title_2', 'back.')}</em>
          </h2>
          <p className="lp-form-subtitle fade-in d400">
            {t('login_subtitle', 'Sign in to continue your work.')}
          </p>

          <button className="lp-google-btn fade-in d400" onClick={handleGoogleLogin}>
            <GoogleIcon />
            {t('continue_google', 'Continue with Google')}
          </button>

          <div className="lp-divider fade-in d400">
            <span>{t('continue_email', 'or continue with email')}</span>
          </div>

          <form onSubmit={handleSubmit}>

            <div className="lp-field fade-in d500">
              <label className="lp-label" htmlFor="lp-email">
                {t('email', 'Email')}
              </label>
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
              <label className="lp-label" htmlFor="lp-pw">
                {t('password', 'Password')}
              </label>
              <div className="lp-input-wrap">
                <input
                  id="lp-pw"
                  type={showPassword ? 'text' : 'password'}
                  className="lp-input"
                  placeholder="••••••••••"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  required
                  autoComplete="current-password"
                />
                <button
                  type="button"
                  className="lp-input-icon"
                  onClick={() => setShowPassword((v) => !v)}
                  aria-label={showPassword ? 'Hide password' : 'Show password'}
                >
                  {showPassword ? <EyeOffIcon /> : <EyeIcon />}
                </button>
              </div>
            </div>

            <div className="lp-bottom-row fade-in d600">
              <label className="lp-remember">
                <input
                  type="checkbox"
                  checked={rememberMe}
                  onChange={(e) => setRememberMe(e.target.checked)}
                />
                {t('remember_me', 'Remember me')}
              </label>
              <Link to="#" className="lp-forgot">
                {t('forgot_password', 'Forgot password?')}
              </Link>
            </div>

            {error && (
              <div className="lp-error">
                <span>⚠</span> {error}
              </div>
            )}

            <button type="submit" className="lp-submit fade-in d700" disabled={loading}>
              {loading ? <span className="lp-spinner" /> : t('login_action', 'Sign in →')}
            </button>

          </form>

          <p className="lp-signup fade-in d700">
            {t('no_account', 'New here?')}{' '}
            <Link to="/register">{t('create_free_account', 'Create a free account')}</Link>
          </p>

          <div className="lp-trust fade-in d700">
            <span className="lp-trust-item">🔒 {t('trust_ssl', '256-bit SSL')}</span>
            <span className="lp-trust-dot" />
            <span className="lp-trust-item">🛡️ {t('trust_gdpr', 'GDPR compliant')}</span>
            <span className="lp-trust-dot" />
            <span className="lp-trust-item">🚫 {t('trust_spam', 'No spam, ever')}</span>
          </div>

        </div>
      </div>
      {/* ══════════════ END RIGHT ══════════════ */}

    </div>
  );
}