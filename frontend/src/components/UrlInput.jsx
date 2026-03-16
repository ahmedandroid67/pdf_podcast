import { useState } from 'react';
import { useTranslation } from 'react-i18next';

export default function UrlInput({ onUrl, disabled }) {
  const { t } = useTranslation();
  const [value, setValue] = useState('');

  const submit = () => {
    const trimmed = value.trim();
    if (!trimmed) return;
    try {
      new URL(trimmed); // validate
    } catch {
      alert(t('error_valid_url'));
      return;
    }
    onUrl(trimmed);
  };

  return (
    <div style={{ display: 'flex', gap: '10px' }}>
      <input
        id="pdf-url-input"
        type="url"
        className="input-field"
        placeholder={t('url_placeholder')}
        value={value}
        onChange={(e) => setValue(e.target.value)}
        onKeyDown={(e) => e.key === 'Enter' && !disabled && submit()}
        disabled={disabled}
        aria-label="PDF URL"
      />
      <button
        id="load-url-btn"
        className="btn-primary"
        onClick={submit}
        disabled={disabled || !value.trim()}
        style={{ whiteSpace: 'nowrap' }}
      >
        {t('url_button')}
      </button>
    </div>
  );
}
