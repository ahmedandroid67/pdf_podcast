import { useState, useEffect, useRef } from 'react';
import { useTranslation } from 'react-i18next';
import { processPdfUpload, processPdfUrl, fetchJobProgress, fetchJobResult } from '../api';
import DropZone from './DropZone';
import UrlInput from './UrlInput';
import PipelineStepper from './PipelineStepper';
import AudioPlayer from './AudioPlayer';
import LanguageSelector from './LanguageSelector';
import AudioModeSelector from './AudioModeSelector';
import PodcastLanguageSelector from './PodcastLanguageSelector';
import ThemeToggle from './ThemeToggle';
import './PodcastPipeline.css';

const STEP = { UPLOAD: 0, EXTRACT: 1, SUMMARIZE: 2, PODCAST: 3, AUDIO: 4 };
const STATUS = { IDLE: 'idle', LOADING: 'loading', ERROR: 'error', DONE: 'done' };

const STEP_MAP = {
  queued:      STEP.EXTRACT,
  extracting:  STEP.EXTRACT,
  summarizing: STEP.SUMMARIZE,
  scripting:   STEP.PODCAST,
  audio:       STEP.AUDIO,
  done:        STEP.AUDIO,
};

const HOW_STEPS = (t) => [
  { icon: '📄', title: t('how_step_1_title'), desc: t('how_step_1_desc') },
  { icon: '🔍', title: t('how_step_2_title'), desc: t('how_step_2_desc') },
  { icon: '🤖', title: t('how_step_3_title'), desc: t('how_step_3_desc') },
  { icon: '🎙️', title: t('how_step_4_title'), desc: t('how_step_4_desc') },
  { icon: '🎧', title: t('how_step_5_title'), desc: t('how_step_5_desc') },
];

export default function PodcastPipeline({ onJobCreated, embedded = false }) {
  const { t, i18n } = useTranslation();

  const [tab, setTab]                   = useState('file');
  const [step, setStep]                 = useState(STEP.UPLOAD);
  const [status, setStatus]             = useState(STATUS.IDLE);
  const [error, setError]               = useState('');
  const [extractedText, setExtractedText] = useState('');
  const [summary, setSummary]           = useState('');
  const [script, setScript]             = useState('');
  const [audioUrl, setAudioUrl]         = useState('');
  const [processingMode, setProcessingMode] = useState('podcast');
  const [podcastLang, setPodcastLang]   = useState('en');
  const [progressLabel, setProgressLabel] = useState('');

  const pollingRef = useRef(null);
  const busy = status === STATUS.LOADING;

  const reset = () => {
    stopPolling();
    setStep(STEP.UPLOAD);
    setStatus(STATUS.IDLE);
    setError('');
    setExtractedText('');
    setSummary('');
    setScript('');
    setAudioUrl('');
    setProgressLabel('');
  };

  const stopPolling = () => {
    if (pollingRef.current) {
      clearInterval(pollingRef.current);
      pollingRef.current = null;
    }
  };

  const stepLabelFromKey = (key) => {
    const map = {
      queued:      t('status_queued',     'Queued — waiting to start…'),
      extracting:  t('status_extracting'),
      summarizing: t('status_summarizing'),
      scripting:   t('status_scripting',  'Writing podcast script…'),
      audio:       t('status_generating'),
      done:        t('status_done',       'Finishing up…'),
    };
    return map[key] || t('status_extracting');
  };

  const POLL_INTERVAL_MS = 2500;
  const MAX_POLL_ATTEMPTS = 240; // ~10 minutes

  const failPolling = (message) => {
    stopPolling();
    setError(message);
    setStatus(STATUS.ERROR);
    setStep(STEP.UPLOAD);
  };

  const startPolling = (jobId) => {
    let attempts = 0;
    let consecutiveErrors = 0;

    pollingRef.current = setInterval(async () => {
      attempts += 1;
      if (attempts > MAX_POLL_ATTEMPTS) {
        failPolling(t('error_timed_out', 'This is taking longer than expected. Please try again.'));
        return;
      }

      try {
        const prog = await fetchJobProgress(jobId);
        consecutiveErrors = 0;
        const pStep = STEP_MAP[prog.step] ?? STEP.EXTRACT;
        setStep(pStep);
        setProgressLabel(stepLabelFromKey(prog.step));

        const isDone   = prog.status === 'completed' || prog.step === 'done';
        const isFailed = prog.status === 'failed'    || prog.step === 'failed';

        if (isDone) {
          stopPolling();
          try {
            const result = await fetchJobResult(jobId);
            setExtractedText(result.text  || '');
            setSummary(result.summary     || '');
            setScript(result.script       || '');
            setAudioUrl(result.audio_url  || '');
            setStep(STEP.AUDIO);
            setStatus(STATUS.DONE);
            if (onJobCreated) onJobCreated(jobId);
          } catch (e) {
            failPolling(e.message);
          }
        } else if (isFailed) {
          // The failed-job response body carries the real reason (e.g. "scanned
          // PDF", "Gemini quota exceeded") as its error detail — surface it.
          try {
            await fetchJobResult(jobId);
            failPolling(t('error_processing_file', 'Processing failed.'));
          } catch (e) {
            failPolling(e.message || t('error_processing_file', 'Processing failed.'));
          }
        }
      } catch (e) {
        if (e.status === 401 || e.status === 403) {
          failPolling(t('error_session_expired', 'Your session expired. Please log in again.'));
          return;
        }
        consecutiveErrors += 1;
        if (consecutiveErrors >= 5) {
          failPolling(e.message || t('error_processing_file', 'Processing failed.'));
        }
        // otherwise treat as a transient network blip and keep polling
      }
    }, POLL_INTERVAL_MS);
  };

  useEffect(() => () => stopPolling(), []);

  const runProcess = async (source) => {
    setStatus(STATUS.LOADING);
    setError('');
    setStep(STEP.EXTRACT);
    setProgressLabel(t('status_extracting'));
    try {
      const data = source.type === 'file'
        ? await processPdfUpload(source.file, podcastLang, processingMode)
        : await processPdfUrl(source.url, podcastLang, processingMode);

      if (data.job_id) {
        startPolling(data.job_id);
      } else {
        setExtractedText(data.text    || '');
        setSummary(data.summary       || '');
        setScript(data.script         || '');
        setAudioUrl(data.audio_url    || '');
        setStep(STEP.AUDIO);
        setStatus(STATUS.DONE);
        if (onJobCreated) onJobCreated(data.job_id);
      }
    } catch (e) {
      setError(t(e.message) || e.message);
      setStatus(STATUS.ERROR);
      setStep(STEP.UPLOAD);
    }
  };

  const handleFile = (file) => runProcess({ type: 'file', file });
  const handleUrl  = (url)  => runProcess({ type: 'url',  url  });

  const stepLabel = () => progressLabel || t('status_extracting');

  const mainContent = (
    <main className="pp-main">

      <div className="pp-hero fade-in pp-d1">
        <div className="pp-hero-badge">
          <span className="pp-badge-dot" />
          AI-Powered · Instant · Multi-language
        </div>
        <h1 className="pp-hero-title">{t('hero_title')}</h1>
        <p className="pp-hero-sub">{t('hero_subtitle')}</p>
      </div>

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

      {status !== STATUS.DONE && (
        <div className="pp-upload-card fade-in pp-d2">

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

          <div className="pp-input-zone">
            {tab === 'file'
              ? <DropZone onFile={handleFile} disabled={busy} />
              : <UrlInput onUrl={handleUrl}   disabled={busy} />
            }
          </div>

          <div className="pp-mode-row">
            <AudioModeSelector mode={processingMode} onChange={setProcessingMode} disabled={busy} />
          </div>

          <PodcastLanguageSelector lang={podcastLang} onChange={setPodcastLang} disabled={busy} />

          {!busy && status !== STATUS.ERROR && (
            <p className="pp-hint">{t('tip')}</p>
          )}

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

      {status === STATUS.DONE && (
        <div className="pp-results fade-in pp-d1">

          <div className="pp-result-block">
            <AudioPlayer audioUrl={audioUrl} script={script} />
          </div>

          {summary && (
            <div className="pp-result-block pp-summary-card fade-in pp-d2">
              <div className="pp-result-header">
                <span className="pp-result-icon">📝</span>
                <h2 className="pp-result-title">{t('results_summary')}</h2>
              </div>
              <p className="pp-summary-text">{summary}</p>
            </div>
          )}

          {extractedText && (
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
          )}

          <div className="pp-start-over">
            <button className="pp-start-over-btn" onClick={reset}>
              ↺ {t('results_process_another')}
            </button>
          </div>
        </div>
      )}

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

  if (embedded) return <div className="pp-embedded">{mainContent}</div>;

  return (
    <div className="pp-page">
      <div className="pp-bg-mesh" aria-hidden="true" />
      <div className="pp-bg-grain" aria-hidden="true" />

      <header className="pp-header">
        <div className="pp-header-inner">
          <div className="pp-logo">
            <div className="pp-logo-icon">🎙️</div>
            <span className="pp-logo-name">Pdf<em>Parser</em></span>
          </div>
          <div className="pp-header-right">
            <ThemeToggle />
            <LanguageSelector />
          </div>
        </div>
      </header>

      {mainContent}

      <footer className="pp-footer">
        <p>{t('footer')}</p>
      </footer>
    </div>
  );
}
