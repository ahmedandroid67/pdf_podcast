import { useTranslation } from 'react-i18next';

export default function AudioModeSelector({ mode, onChange, disabled }) {
  const { t, i18n } = useTranslation();
  const isRtl = i18n.language === 'ar';

  return (
    <div style={{ 
      display: 'flex', 
      flexDirection: 'column', 
      gap: '12px',
      marginTop: '24px',
      padding: '16px',
      background: 'rgba(99,102,241,0.04)',
      borderRadius: '12px',
      border: '1px solid var(--border)',
    }}>
      <div style={{ display: 'flex', gap: '8px' }}>
        {[
          { id: 'podcast', icon: '🎙️', title: t('mode_podcast'), desc: t('mode_podcast_desc') },
          { id: 'audiobook', icon: '📖', title: t('mode_audiobook'), desc: t('mode_audiobook_desc') }
        ].map((item) => (
          <button
            key={item.id}
            onClick={() => !disabled && onChange(item.id)}
            disabled={disabled}
            style={{
              flex: 1,
              padding: '12px',
              borderRadius: '10px',
              border: `2px solid ${mode === item.id ? 'var(--brand-from)' : 'transparent'}`,
              background: mode === item.id ? 'var(--surface)' : 'transparent',
              cursor: disabled ? 'not-allowed' : 'pointer',
              display: 'flex',
              flexDirection: 'column',
              alignItems: isRtl ? 'flex-end' : 'flex-start',
              textAlign: isRtl ? 'right' : 'left',
              gap: '4px',
              transition: 'all 0.2s ease',
              opacity: disabled ? 0.6 : 1,
              boxShadow: mode === item.id ? '0 4px 12px rgba(99,102,241,0.15)' : 'none',
            }}
            className={mode === item.id ? '' : 'hover-lift'}
          >
            <span style={{ fontSize: '1.2rem' }}>{item.icon}</span>
            <span style={{ fontWeight: 700, fontSize: '0.9rem', color: mode === item.id ? 'var(--text-1)' : 'var(--text-2)' }}>
              {item.title}
            </span>
            <span style={{ fontSize: '0.75rem', color: 'var(--text-3)', lineHeight: 1.3 }}>
              {item.desc}
            </span>
          </button>
        ))}
      </div>
    </div>
  );
}
