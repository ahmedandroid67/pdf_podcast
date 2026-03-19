import { useState } from 'react';
import { useTranslation } from 'react-i18next';
import { processPdfUpload, processPdfUrl } from '../api';
import DropZone from './DropZone';
import UrlInput from './UrlInput';
import PipelineStepper from './PipelineStepper';
import AudioPlayer from './AudioPlayer';
import LanguageSelector from './LanguageSelector';
import AudioModeSelector from './AudioModeSelector';
import './PodcastPipeline.css';

const STEP = { UPLOAD: 0, EXTRACT: 1, SUMMARIZE: 2, PODCAST: 3, AUDIO: 4 };
const STATUS = { IDLE: 'idle', LOADING: 'loading', ERROR: 'error', DONE: 'done' };

const HOW_STEPS = (t) => [
  { icon: '📄', title: t('how_step_1_title'), desc: t('how_step_1_desc') },
  { icon: '🔍', title: t('how_step_2_title'), desc: t('how_step_2_desc') },
  { icon: '🤖', title: t('how_step_3_title'), desc: t('how_step_3_desc') },
  { icon: '🎙️', title: t('how_step_4_title'), desc: t('how_step_4_desc') },
  { icon: '🎧', title: t('how_step_5_title'), desc: t('how_step_5_desc') },
];

/* ─── component ─────────────────────────────────────────────────────────────── */
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
      const data = source.type === 'file'
        ? await processPdfUpload(source.file, i18n.language, processingMode)
        : await processPdfUrl(source.url, i18n.language, processingMode);

      setExtractedText(data.text);
      setSummary(data.summary);
      setScript(data.script);
      setAudioUrl(data.audio_url);
      setStep(STEP.AUDIO);
      setStatus(STATUS.DONE);
      if (data.job_id && onJobCreated) onJobCreated(data.job_id);
    } catch (e) {
      setError(t(e.message) || e.message);
      setStatus(STATUS.ERROR);
      setStep(STEP.UPLOAD);
    }
  };

  const handleFile = (file) => { setSelectedFile(file); runProcess({ type: 'file', file }); };
  const handleUrl = (url) => runProcess({ type: 'url', url });

  /* step label helper */
  const stepLabel = () => {
    if (step === STEP.EXTRACT) return t('status_extracting');
    if (step === STEP.SUMMARIZE) return t('status_summarizing');
    if (step === STEP.PODCAST) return t('status_generating');
    return '';
  };

  /* ── main content ── */
  const mainContent = (
    <main className="pp-main">

      {/* ── hero ── */}
      <div className="pp-hero fade-in pp-d1">
        <div className="pp-hero-badge">
          <span className="pp-badge-dot" />
          AI-Powered · Instant · Multi-language
        </div>
        <h1 className="pp-hero-title">
          {t('hero_title')}
        </h1>
        <p className="pp-hero-sub">{t('hero_subtitle')}</p>
      </div>

      {/* ── pipeline stepper (visible when processing) ── */}
      {status !== STATUS.IDLE && (
        <div className="pp-stepper-wrap fade-in pp-d1">
          <div className="pp-stepper-card">
            <PipelineStepper currentStep={step} />
          </div>

          {status === STATUS.ERROR && (
            <div className="pp-error-banner" role="alert">
              <span className="pp-error-icon">⚠️</span>
              <div>
                <p className="pp-error-title">{t('error_processing_file')}</p>
                <p className="pp-error-msg">{error}</p>
              </div>
            </div>
          )}
        </div>
      )}

      {/* ── upload card ── */}
      {status !== STATUS.DONE && (
        <div className="pp-upload-card fade-in pp-d2">

          {/* tab switcher */}
          <div className="pp-tabs">
            {['file', 'url'].map((tabItem) => (
              <button
                key={tabItem}
                className={`pp-tab ${tab === tabItem ? 'pp-tab-active' : ''}`}
                disabled={busy}
                onClick={() => {
                  if (busy) return;
                  setTab(tabItem);
                  if (tabItem === 'file') document.getElementById('pdf-file-input')?.click();
                }}
              >
                {tabItem === 'file' ? '📂 ' + t('tab_file') : '🔗 ' + t('tab_url')}
              </button>
            ))}
          </div>

          {/* input zone */}
          <div className="pp-input-zone">
            {tab === 'file'
              ? <DropZone onFile={handleFile} disabled={busy} />
              : <UrlInput onUrl={handleUrl} disabled={busy} />
            }
          </div>

          {/* mode selector */}
          <div className="pp-mode-row">
            <AudioModeSelector mode={processingMode} onChange={setProcessingMode} disabled={busy} />
          </div>

          {/* hint */}
          {!busy && status !== STATUS.ERROR && (
            <p className="pp-hint">
              {t('tip')} &nbsp;·&nbsp; {t('test_file_hint')}
            </p>
          )}

          {/* loading state */}
          {busy && (
            <div className="pp-loading-bar">
              <div className="pp-loading-spinner" />
              <div>
                <p className="pp-loading-label">{stepLabel()}</p>
                <p className="pp-loading-sub">{t('status_time_hint')}</p>
              </div>
            </div>
          )}
        </div>
      )}

      {/* ── results ── */}
      {status === STATUS.DONE && (
        <div className="pp-results fade-in pp-d1">

          {/* audio player */}
          <div className="pp-result-block">
            <AudioPlayer audioUrl={audioUrl} script={script} />
          </div>

          {/* summary */}
          <div className="pp-result-block pp-summary-card fade-in pp-d2">
            <div className="pp-result-header">
              <span className="pp-result-icon">📝</span>
              <h2 className="pp-result-title">{t('results_summary')}</h2>
            </div>
            <p className="pp-summary-text">{summary}</p>
          </div>

          {/* raw text */}
          <div className="pp-result-block pp-raw-card fade-in pp-d3">
            <details>
              <summary className="pp-raw-summary">
                <span className="pp-result-icon">📄</span>
                {t('results_raw_text', { count: extractedText.length })}
                <span className="pp-raw-chevron">▾</span>
              </summary>
              <div className="pp-raw-content">{extractedText}</div>
            </details>
          </div>

          {/* start over */}
          <div className="pp-start-over">
            <button className="pp-start-over-btn" onClick={reset}>
              ↺ {t('results_process_another')}
            </button>
          </div>
        </div>
      )}

      {/* ── how it works ── */}
      {status === STATUS.IDLE && (
        <div className="pp-how fade-in pp-d3">
          <div className="pp-how-header">
            <div className="pp-how-line" />
            <h2 className="pp-how-title">{t('how_it_works')}</h2>
            <div className="pp-how-line" />
          </div>
          <div className="pp-how-steps">
            {HOW_STEPS(t).map((item, i) => (
              <div key={i} className="pp-how-step" style={{ animationDelay: `${i * 0.08}s` }}>
                <div className="pp-how-step-num">{i + 1}</div>
                <div className="pp-how-step-icon">{item.icon}</div>
                <p className="pp-how-step-title">{item.title}</p>
                <p className="pp-how-step-desc">{item.desc}</p>
                {i < 4 && <div className="pp-how-arrow">→</div>}
              </div>
            ))}
          </div>
        </div>
      )}

    </main>
  );

  /* embedded mode */
  if (embedded) return <div className="pp-embedded">{mainContent}</div>;

  /* full page mode */
  return (
    <div className="pp-page">

      {/* ambient bg */}
      <div className="pp-bg-mesh" aria-hidden="true" />
      <div className="pp-bg-grain" aria-hidden="true" />

      {/* header */}
      <header className="pp-header">
        <div className="pp-header-inner">
          <div className="pp-logo">
            <div className="pp-logo-icon">🎙️</div>
            <span className="pp-logo-name">
              Pdf<em>Parser</em>
            </span>
          </div>
          <div className="pp-header-right">
            <LanguageSelector />
          </div>
        </div>
      </header>

      {mainContent}

      {/* footer */}
      <footer className="pp-footer">
        <p>{t('footer')}</p>
      </footer>
    </div>
  );
}