import { useTranslation } from 'react-i18next';

const languages = [
  { code: 'en', label: 'English', icon: '🇺🇸' },
  { code: 'fr', label: 'Français', icon: '🇫🇷' },
  { code: 'ar', label: 'العربية', icon: '🇲🇦' },
];

export default function LanguageSelector() {
  const { i18n } = useTranslation();

  return (
    <div style={{ display: 'flex', gap: '8px', marginLeft: 'auto' }}>
      {languages.map((lang) => (
        <button
          key={lang.code}
          onClick={() => i18n.changeLanguage(lang.code)}
          style={{
            background: i18n.language === lang.code ? 'var(--brand-from)' : 'var(--surface-3)',
            color: i18n.language === lang.code ? '#fff' : 'var(--text-2)',
            border: '1px solid var(--border)',
            padding: '6px 12px',
            borderRadius: '20px',
            fontSize: '0.85rem',
            fontWeight: 600,
            cursor: 'pointer',
            display: 'flex',
            alignItems: 'center',
            gap: '6px',
            transition: 'all 0.2s',
          }}
          className="hover-lift"
        >
          <span>{lang.icon}</span>
          <span style={{ display: 'none' }} className="lang-label">{lang.label}</span>
          <span>{lang.code.toUpperCase()}</span>
        </button>
      ))}
    </div>
  );
}
