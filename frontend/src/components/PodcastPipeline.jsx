import { useState } from 'react';
import { useTranslation } from 'react-i18next';
import { processPdfUpload, processPdfUrl } from '../api';
import DropZone from './DropZone';
import UrlInput from './UrlInput';
import PipelineStepper from './PipelineStepper';
import AudioPlayer from './AudioPlayer';
import LanguageSelector from './LanguageSelector';
import AudioModeSelector from './AudioModeSelector';

const STEP = { UPLOAD: 0, EXTRACT: 1, SUMMARIZE: 2, PODCAST: 3, AUDIO: 4 };
const STATUS = { IDLE: 'idle', LOADING: 'loading', ERROR: 'error', DONE: 'done' };

export default function PodcastPipeline({ onJobCreated, embedded = false }) {
  const { t, i18n } = useTranslation();
  const [tab, setTab] = useState('file');
  const [step, setStep] = useState(STEP.UPLOAD);
  const [status, setStatus] = useState(STATUS.IDLE);
  const [error, setError] = useState('');

  const [selectedFile, setSelectedFile] = useState(null);
  const [extractedText, setExtractedText] = useState('');
  const [summary, setSummary] = useState('');
  const [script, setScript] = useState('');
  const [audioUrl, setAudioUrl] = useState('');
  const [processingMode, setProcessingMode] = useState('podcast');

  const busy = status === STATUS.LOADING;

  const reset = () => {
    setStep(STEP.UPLOAD);
    setStatus(STATUS.IDLE);
    setError('');
    setSelectedFile(null);
    setExtractedText('');
    setSummary('');
    setScript('');
    setAudioUrl('');
  };

  const runProcess = async (source) => {
    setStatus(STATUS.LOADING);
    setError('');
    setStep(STEP.EXTRACT);
    try {
      let data;
      if (source.type === 'file') {
        data = await processPdfUpload(source.file, i18n.language, processingMode);
      } else {
        data = await processPdfUrl(source.url, i18n.language, processingMode);
      }
      setExtractedText(data.text);
      setSummary(data.summary);
      setScript(data.script);
      setAudioUrl(data.audio_url);
      setStep(STEP.AUDIO);
      setStatus(STATUS.DONE);
      if (data.job_id && onJobCreated) {
        onJobCreated(data.job_id);
      }
    } catch (e) {
      setError(t(e.message) || e.message);
      setStatus(STATUS.ERROR);
      setStep(STEP.UPLOAD);
    }
  };

  const handleFile = (file) => {
    setSelectedFile(file);
    runProcess({ type: 'file', file });
  };

  const handleUrl = (url) => {
    runProcess({ type: 'url', url });
  };

  const mainContent = (
    <main style={{ flex: 1, maxWidth: '780px', width: '100%', margin: '0 auto', padding: '48px 20px 80px' }}>
      <div style={{ textAlign: 'center', marginBottom: '48px' }}>
        <h1
          className="gradient-text fade-up"
          style={{ fontFamily: "'Outfit',sans-serif", fontWeight: 800, fontSize: 'clamp(2rem,5vw,3rem)', lineHeight: 1.15, marginBottom: '16px' }}
        >
          {t('hero_title')}
        </h1>
        <p
          className="fade-up"
          style={{ color: 'var(--text-2)', fontSize: '1.05rem', lineHeight: 1.6, animationDelay: '0.1s' }}
        >
          {t('hero_subtitle')}
        </p>
      </div>

      {status !== STATUS.IDLE && (
        <div style={{ marginBottom: '32px' }}>
          <div className="card fade-up" style={{ padding: '24px 16px', marginBottom: status === STATUS.ERROR ? '16px' : '0' }}>
            <PipelineStepper currentStep={step} />
          </div>

          {status === STATUS.ERROR && (
            <div
              className="fade-up"
              style={{
                padding: '16px 20px',
                background: 'rgba(239, 68, 68, 0.08)',
                border: '1px solid rgba(239, 68, 68, 0.2)',
                borderRadius: '12px',
                color: '#fca5a5',
                fontSize: '0.95rem',
                lineHeight: '1.5',
                display: 'flex',
                alignItems: 'center',
                gap: '12px',
                boxShadow: '0 4px 15px rgba(239, 68, 68, 0.1)'
              }}
              role="alert"
            >
              <span style={{ fontSize: '1.4rem' }}>⚠️</span>
              <div>
                <p style={{ fontWeight: 600, color: '#f87171', marginBottom: '2px' }}>
                  {t('error_processing_file')}
                </p>
                <p style={{ fontSize: '0.88rem', opacity: 0.9 }}>
                  {error}
                </p>
              </div>
            </div>
          )}
        </div>
      )}

      {status !== STATUS.DONE && (
        <div className="card fade-up" style={{ marginBottom: '28px', animationDelay: '0.15s' }}>
          <div style={{ display: 'flex', gap: '8px', marginBottom: '24px' }}>
            {['file', 'url'].map((tabItem) => (
              <button
                key={tabItem}
                id={`tab-${tabItem}`}
                onClick={() => {
                  if (busy) return;
                  setTab(tabItem);
                  if (tabItem === 'file') {
                    document.getElementById('pdf-file-input')?.click();
                  }
                }}
                className={tab === tabItem ? 'btn-primary' : 'btn-secondary'}
                style={{ flex: 1 }}
                disabled={busy}
              >
                {tabItem === 'file' ? t('tab_file') : t('tab_url')}
              </button>
            ))}
          </div>

          {tab === 'file' ? (
            <DropZone onFile={handleFile} disabled={busy} />
          ) : (
            <UrlInput onUrl={handleUrl} disabled={busy} />
          )}

          <AudioModeSelector
            mode={processingMode}
            onChange={setProcessingMode}
            disabled={busy}
          />

          {!busy && status !== STATUS.ERROR && (
            <p style={{ marginTop: '16px', fontSize: '0.78rem', color: 'var(--text-3)', textAlign: 'center' }}>
              {t('tip')}
              <br />
              {t('test_file_hint')}
            </p>
          )}

          {busy && (
            <div
              style={{
                marginTop: '24px',
                display: 'flex',
                alignItems: 'center',
                gap: '14px',
                padding: '16px 20px',
                background: 'rgba(99,102,241,0.08)',
                borderRadius: '10px',
                border: '1px solid rgba(99,102,241,0.2)',
              }}
            >
              <div className="spinner" />
              <div>
                <p style={{ fontWeight: 600, fontSize: '0.92rem' }}>
                  {step === STEP.EXTRACT && t('status_extracting')}
                  {step === STEP.SUMMARIZE && t('status_summarizing')}
                  {step === STEP.PODCAST && t('status_generating')}
                </p>
                <p style={{ color: 'var(--text-3)', fontSize: '0.8rem', marginTop: '2px' }}>
                  {t('status_time_hint')}
                </p>
              </div>
            </div>
          )}
        </div>
      )}

      {status === STATUS.DONE && (
        <div style={{ display: 'flex', flexDirection: 'column', gap: '20px' }}>
          <AudioPlayer audioUrl={audioUrl} script={script} />

          <div className="card fade-up" style={{ animationDelay: '0.15s' }}>
            <h2
              style={{ fontFamily: "'Outfit',sans-serif", fontWeight: 700, fontSize: '1rem', marginBottom: '12px', color: 'var(--text-1)' }}
            >
              {t('results_summary')}
            </h2>
            <p style={{ color: 'var(--text-2)', fontSize: '0.88rem', lineHeight: 1.75, whiteSpace: 'pre-wrap' }}>
              {summary}
            </p>
          </div>

          <div className="card fade-up" style={{ animationDelay: '0.25s' }}>
            <details>
              <summary
                style={{ cursor: 'pointer', fontWeight: 600, fontSize: '0.9rem', color: 'var(--text-2)', userSelect: 'none', outline: 'none' }}
              >
                {t('results_raw_text', { count: extractedText.length })}
              </summary>
              <div
                style={{
                  marginTop: '12px',
                  maxHeight: '300px',
                  overflowY: 'auto',
                  fontSize: '0.8rem',
                  color: 'var(--text-3)',
                  lineHeight: 1.65,
                  whiteSpace: 'pre-wrap',
                  background: 'var(--surface-3)',
                  padding: '14px',
                  borderRadius: '8px',
                }}
              >
                {extractedText}
              </div>
            </details>
          </div>

          <div style={{ textAlign: 'center', marginTop: '8px' }}>
            <button id="start-over-btn" className="btn-secondary" onClick={reset}>
              {t('results_process_another')}
            </button>
          </div>
        </div>
      )}

      {status === STATUS.IDLE && (
        <div className="fade-up" style={{ animationDelay: '0.25s' }}>
          <h2
            style={{ textAlign: 'center', fontFamily: "'Outfit',sans-serif", fontWeight: 700, fontSize: '1.1rem', color: 'var(--text-2)', marginBottom: '20px' }}
          >
            {t('how_it_works')}
          </h2>
          <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit,minmax(160px,1fr))', gap: '16px' }}>
            {[
              { icon: '📄', title: t('how_step_1_title'), desc: t('how_step_1_desc') },
              { icon: '🔍', title: t('how_step_2_title'), desc: t('how_step_2_desc') },
              { icon: '🤖', title: t('how_step_3_title'), desc: t('how_step_3_desc') },
              { icon: '🎙️', title: t('how_step_4_title'), desc: t('how_step_4_desc') },
              { icon: '🎧', title: t('how_step_5_title'), desc: t('how_step_5_desc') },
            ].map((item, i) => (
              <div
                key={i}
                className="card"
                style={{ textAlign: 'center', padding: '20px 16px', animationDelay: `${i * 0.07}s` }}
              >
                <div style={{ fontSize: '2rem', marginBottom: '10px' }}>{item.icon}</div>
                <p style={{ fontWeight: 600, fontSize: '0.88rem', marginBottom: '4px' }}>{item.title}</p>
                <p style={{ color: 'var(--text-3)', fontSize: '0.78rem' }}>{item.desc}</p>
              </div>
            ))}
          </div>
        </div>
      )}
    </main>
  );

  if (embedded) {
    return (
      <div style={{ width: '100%' }}>
        {mainContent}
      </div>
    );
  }

  return (
    <div
      style={{
        minHeight: '100vh',
        display: 'flex',
        flexDirection: 'column',
        background: 'radial-gradient(ellipse at 20% 0%, rgba(99,102,241,0.12) 0%, transparent 60%), var(--surface)',
      }}
    >
      <header
        className="glass"
        style={{
          position: 'sticky',
          top: 0,
          zIndex: 50,
          padding: '14px 32px',
          display: 'flex',
          alignItems: 'center',
          gap: '12px',
          borderRadius: 0,
          borderTop: 'none',
          borderLeft: 'none',
          borderRight: 'none',
        }}
      >
        <span style={{ fontSize: '1.6rem' }} aria-hidden="true">🎙️</span>
        <span
          style={{ fontFamily: "'Outfit',sans-serif", fontWeight: 800, fontSize: '1.2rem' }}
          className="gradient-text"
        >
          {t('app_name')}
        </span>
        <LanguageSelector />
      </header>

      {mainContent}

      <footer style={{ textAlign: 'center', padding: '20px', color: 'var(--text-3)', fontSize: '0.78rem', borderTop: '1px solid var(--border)' }}>
        {t('footer')}
      </footer>
    </div>
  );
}
