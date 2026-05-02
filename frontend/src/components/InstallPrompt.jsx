import { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';

export default function InstallPrompt() {
  const { t } = useTranslation();
  const [deferredPrompt, setDeferredPrompt] = useState(null);
  const [show, setShow] = useState(false);
  const [dismissed, setDismissed] = useState(false);

  useEffect(() => {
    if (sessionStorage.getItem('pwa-dismissed')) return;

    const isStandalone =
      window.matchMedia('(display-mode: standalone)').matches ||
      window.navigator.standalone === true;
    if (isStandalone) return;

    const handler = (e) => {
      e.preventDefault();
      setDeferredPrompt(e);
      setShow(true);
    };

    window.addEventListener('beforeinstallprompt', handler);
    return () => window.removeEventListener('beforeinstallprompt', handler);
  }, []);

  const handleInstall = async () => {
    if (!deferredPrompt) return;
    deferredPrompt.prompt();
    const { outcome } = await deferredPrompt.userChoice;
    setDeferredPrompt(null);
    setShow(false);
    if (outcome === 'accepted') sessionStorage.setItem('pwa-dismissed', '1');
  };

  const handleDismiss = () => {
    setShow(false);
    setDismissed(true);
    sessionStorage.setItem('pwa-dismissed', '1');
  };

  if (!show || dismissed) return null;

  return (
    <div style={{
      position: 'fixed',
      bottom: '20px',
      left: '50%',
      transform: 'translateX(-50%)',
      zIndex: 9999,
      width: 'min(360px, calc(100vw - 32px))',
      background: 'white',
      border: '1.5px solid #e5e7eb',
      borderRadius: '18px',
      boxShadow: '0 8px 32px rgba(99,102,241,0.18), 0 2px 8px rgba(0,0,0,0.08)',
      padding: '18px 20px',
      display: 'flex',
      alignItems: 'flex-start',
      gap: '14px',
      animation: 'slideUp 0.35s cubic-bezier(0.16,1,0.3,1) both',
      fontFamily: "'Plus Jakarta Sans', system-ui, sans-serif",
    }}>
      <style>{`
        @keyframes slideUp {
          from { opacity: 0; transform: translateX(-50%) translateY(24px); }
          to   { opacity: 1; transform: translateX(-50%) translateY(0); }
        }
      `}</style>

      <div style={{
        width: '44px', height: '44px', flexShrink: 0,
        background: 'linear-gradient(135deg, #6366f1, #818cf8)',
        borderRadius: '12px',
        display: 'flex', alignItems: 'center', justifyContent: 'center',
        fontSize: '22px',
        boxShadow: '0 3px 10px rgba(99,102,241,0.28)',
      }}>
        🎙️
      </div>

      <div style={{ flex: 1, minWidth: 0 }}>
        <p style={{ fontWeight: 800, fontSize: '0.9rem', color: '#111827', marginBottom: '3px', letterSpacing: '-0.01em' }}>
          {t('pwa_install_title', 'Add to Home Screen')}
        </p>
        <p style={{ fontSize: '0.78rem', color: '#6b7280', lineHeight: 1.5, marginBottom: '12px', fontWeight: 500 }}>
          {t('pwa_install_desc', 'Install PdfParser for a faster, app-like experience — no browser needed.')}
        </p>
        <div style={{ display: 'flex', gap: '8px' }}>
          <button
            onClick={handleInstall}
            style={{
              flex: 1,
              height: '36px',
              background: 'linear-gradient(135deg, #6366f1, #4f46e5)',
              border: 'none',
              borderRadius: '10px',
              color: 'white',
              fontFamily: 'inherit',
              fontSize: '0.82rem',
              fontWeight: 700,
              cursor: 'pointer',
              boxShadow: '0 3px 10px rgba(99,102,241,0.28)',
              transition: 'transform 0.15s',
            }}
            onMouseEnter={e => e.currentTarget.style.transform = 'translateY(-1px)'}
            onMouseLeave={e => e.currentTarget.style.transform = 'translateY(0)'}
          >
            {t('pwa_install_btn', 'Install')}
          </button>
          <button
            onClick={handleDismiss}
            style={{
              height: '36px',
              padding: '0 14px',
              background: '#f3f4f6',
              border: 'none',
              borderRadius: '10px',
              color: '#6b7280',
              fontFamily: 'inherit',
              fontSize: '0.82rem',
              fontWeight: 600,
              cursor: 'pointer',
              transition: 'background 0.15s',
            }}
            onMouseEnter={e => e.currentTarget.style.background = '#e5e7eb'}
            onMouseLeave={e => e.currentTarget.style.background = '#f3f4f6'}
          >
            {t('pwa_install_later', 'Not now')}
          </button>
        </div>
      </div>

      <button
        onClick={handleDismiss}
        aria-label="Close"
        style={{
          position: 'absolute', top: '12px', right: '12px',
          width: '24px', height: '24px',
          background: '#f3f4f6', border: 'none', borderRadius: '50%',
          color: '#9ca3af', fontSize: '14px', cursor: 'pointer',
          display: 'flex', alignItems: 'center', justifyContent: 'center',
        }}
      >
        ×
      </button>
    </div>
  );
}
