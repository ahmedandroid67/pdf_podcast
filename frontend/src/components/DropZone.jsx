import { useRef, useState } from 'react';

const ACCEPTED = 'application/pdf';

export default function DropZone({ onFile, disabled }) {
  const inputRef = useRef(null);
  const [dragging, setDragging] = useState(false);

  const handleFile = (file) => {
    if (!file) return;
    if (file.type !== ACCEPTED && !file.name.endsWith('.pdf')) {
      alert('Please select a PDF file.');
      return;
    }
    onFile(file);
  };

  const onDrop = (e) => {
    e.preventDefault();
    setDragging(false);
    handleFile(e.dataTransfer.files[0]);
  };

  return (
    <div
      id="drop-zone"
      role="button"
      tabIndex={0}
      aria-label="Upload PDF by clicking or dragging"
      onClick={() => !disabled && inputRef.current?.click()}
      onKeyDown={(e) => e.key === 'Enter' && !disabled && inputRef.current?.click()}
      onDragOver={(e) => { e.preventDefault(); if (!disabled) setDragging(true); }}
      onDragLeave={() => setDragging(false)}
      onDrop={!disabled ? onDrop : undefined}
      style={{
        border: `2px dashed ${dragging ? 'var(--brand-from)' : 'var(--border)'}`,
        borderRadius: 'var(--radius)',
        padding: '40px 24px',
        textAlign: 'center',
        cursor: disabled ? 'not-allowed' : 'pointer',
        background: dragging ? 'rgba(99,102,241,0.07)' : 'var(--surface-3)',
        transition: 'border-color 0.2s, background 0.2s',
        opacity: disabled ? 0.5 : 1,
      }}
    >
      {/* Icon */}
      <div style={{ fontSize: '2.6rem', marginBottom: '12px', lineHeight: 1 }}>📄</div>
      <p style={{ color: 'var(--text-1)', fontWeight: 600, marginBottom: '4px' }}>
        Drop your PDF here
      </p>
      <p style={{ color: 'var(--text-3)', fontSize: '0.85rem' }}>
        or <span style={{ color: 'var(--brand-from)', textDecoration: 'underline' }}>browse files</span>
      </p>
      <input
        ref={inputRef}
        id="pdf-file-input"
        type="file"
        accept=".pdf,application/pdf"
        style={{ display: 'none' }}
        onChange={(e) => handleFile(e.target.files[0])}
        disabled={disabled}
      />
    </div>
  );
}
