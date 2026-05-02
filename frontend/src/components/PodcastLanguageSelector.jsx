import { useTranslation } from 'react-i18next';

const LANGS = [
  { code: 'en', label: 'English',    flag: '🇺🇸' },
  { code: 'fr', label: 'Français',   flag: '🇫🇷' },
  { code: 'ar', label: 'العربية',    flag: '🇸🇦' },
  { code: 'es', label: 'Español',    flag: '🇪🇸' },
  { code: 'de', label: 'Deutsch',    flag: '🇩🇪' },
  { code: 'pt', label: 'Português',  flag: '🇧🇷' },
  { code: 'it', label: 'Italiano',   flag: '🇮🇹' },
  { code: 'zh', label: '中文',        flag: '🇨🇳' },
  { code: 'ja', label: '日本語',      flag: '🇯🇵' },
  { code: 'ko', label: '한국어',      flag: '🇰🇷' },
  { code: 'ru', label: 'Русский',    flag: '🇷🇺' },
  { code: 'nl', label: 'Nederlands', flag: '🇳🇱' },
  { code: 'tr', label: 'Türkçe',     flag: '🇹🇷' },
  { code: 'pl', label: 'Polski',     flag: '🇵🇱' },
  { code: 'hi', label: 'हिन्दी',     flag: '🇮🇳' },
  { code: 'vi', label: 'Tiếng Việt', flag: '🇻🇳' },
];

export default function PodcastLanguageSelector({ lang, onChange, disabled }) {
  const { t } = useTranslation();

  return (
    <div style={{
      marginTop: '14px',
      padding: '14px',
      background: '#f9fafb',
      borderRadius: '12px',
      border: '1.5px solid #e5e7eb',
    }}>
      <p style={{
        fontSize: '0.75rem', fontWeight: 700,
        color: '#6b7280', marginBottom: '10px',
        textTransform: 'uppercase', letterSpacing: '0.06em',
      }}>
        🌍 {t('podcast_language', 'Podcast Language')}
      </p>
      <div style={{
        display: 'flex', flexWrap: 'wrap', gap: '6px',
      }}>
        {LANGS.map((l) => {
          const active = lang === l.code;
          return (
            <button
              key={l.code}
              onClick={() => !disabled && onChange(l.code)}
              disabled={disabled}
              title={l.label}
              style={{
                display: 'flex', alignItems: 'center', gap: '5px',
                padding: '5px 10px', borderRadius: '8px',
                border: `1.5px solid ${active ? '#6366f1' : '#e5e7eb'}`,
                background: active ? '#eef2ff' : '#fff',
                color: active ? '#6366f1' : '#6b7280',
                fontSize: '0.78rem', fontWeight: active ? 700 : 500,
                cursor: disabled ? 'not-allowed' : 'pointer',
                opacity: disabled ? 0.55 : 1,
                transition: 'all 0.15s',
                boxShadow: active ? '0 1px 4px rgba(99,102,241,0.15)' : 'none',
              }}
            >
              <span>{l.flag}</span>
              <span>{l.label}</span>
            </button>
          );
        })}
      </div>
    </div>
  );
}
