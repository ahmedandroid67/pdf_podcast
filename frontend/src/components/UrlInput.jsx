import { useState } from 'react';

export default function UrlInput({ onUrl, disabled }) {
  const [value, setValue] = useState('');

  const submit = () => {
    const trimmed = value.trim();
    if (!trimmed) return;
    try {
      new URL(trimmed); // validate
    } catch {
      alert('Please enter a valid URL.');
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
        placeholder="https://example.com/document.pdf"
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
        Load URL
      </button>
    </div>
  );
}
