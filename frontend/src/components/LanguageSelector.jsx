import { useTranslation } from 'react-i18next';

const languages = [
  { code: 'en', label: 'EN', icon: '🇺🇸' },
  { code: 'fr', label: 'FR', icon: '🇫🇷' },
  { code: 'ar', label: 'AR', icon: '🇲🇦' },
];

export default function LanguageSelector() {
  const { i18n } = useTranslation();

  return (
    <div style={{
      display: 'flex', gap: '4px',
      background: '#f3f4f6', padding: '3px', borderRadius: '10px',
      border: '1px solid #e5e7eb',
    }}>
      {languages.map((lang) => {
        const active = i18n.language === lang.code;
        return (
          <button
            key={lang.code}
            onClick={() => i18n.changeLanguage(lang.code)}
            style={{
              background: active ? '#fff' : 'transparent',
              color: active ? '#6366f1' : '#9ca3af',
              border: 'none',
              padding: '5px 10px',
              borderRadius: '7px',
              fontSize: '0.78rem',
              fontWeight: active ? 700 : 500,
              cursor: 'pointer',
              display: 'flex',
              alignItems: 'center',
              gap: '4px',
              transition: 'all 0.15s',
              boxShadow: active ? '0 1px 4px rgba(0,0,0,0.07)' : 'none',
              letterSpacing: '0.02em',
            }}
          >
            <span>{lang.icon}</span>
            <span>{lang.label}</span>
          </button>
        );
      })}
    </div>
  );
}
