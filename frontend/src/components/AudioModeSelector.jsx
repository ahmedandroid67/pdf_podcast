import { useTranslation } from 'react-i18next';

export default function AudioModeSelector({ mode, onChange, disabled }) {
  const { t, i18n } = useTranslation();
  const isRtl = i18n.language === 'ar';

  return (
    <div style={{
      display: 'flex',
      flexDirection: 'column',
      gap: '10px',
      marginTop: '18px',
      padding: '14px',
      background: '#f9fafb',
      borderRadius: '12px',
      border: '1.5px solid #e5e7eb',
    }}>
      <div style={{ display: 'flex', gap: '8px' }}>
        {[
          { id: 'podcast',   icon: '🎙️', title: t('mode_podcast'),   desc: t('mode_podcast_desc') },
          { id: 'audiobook', icon: '📖', title: t('mode_audiobook'), desc: t('mode_audiobook_desc') },
        ].map((item) => (
          <button
            key={item.id}
            onClick={() => !disabled && onChange(item.id)}
            disabled={disabled}
            style={{
              flex: 1,
              padding: '12px',
              borderRadius: '10px',
              border: `2px solid ${mode === item.id ? '#6366f1' : '#e5e7eb'}`,
              background: mode === item.id ? '#fff' : 'transparent',
              cursor: disabled ? 'not-allowed' : 'pointer',
              display: 'flex',
              flexDirection: 'column',
              alignItems: isRtl ? 'flex-end' : 'flex-start',
              textAlign: isRtl ? 'right' : 'left',
              gap: '4px',
              transition: 'all 0.2s ease',
              opacity: disabled ? 0.55 : 1,
              boxShadow: mode === item.id
                ? '0 2px 12px rgba(99,102,241,0.14)'
                : 'none',
            }}
          >
            <span style={{ fontSize: '1.15rem' }}>{item.icon}</span>
            <span style={{
              fontWeight: 700, fontSize: '0.875rem',
              color: mode === item.id ? '#6366f1' : '#4b5563',
            }}>
              {item.title}
            </span>
            <span style={{ fontSize: '0.72rem', color: '#9ca3af', lineHeight: 1.4, fontWeight: 500 }}>
              {item.desc}
            </span>
          </button>
        ))}
      </div>
    </div>
  );
}
