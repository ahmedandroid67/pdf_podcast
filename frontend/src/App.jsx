import { useState } from 'react';
import './index.css';
import { uploadPdf, fetchPdfUrl, summarizeText, generatePodcast } from './api';
import DropZone from './components/DropZone';
import UrlInput from './components/UrlInput';
import PipelineStepper from './components/PipelineStepper';
import AudioPlayer from './components/AudioPlayer';

// Step indices
const STEP = { UPLOAD: 0, EXTRACT: 1, SUMMARIZE: 2, PODCAST: 3, AUDIO: 4 };

const STATUS = { IDLE: 'idle', LOADING: 'loading', ERROR: 'error', DONE: 'done' };

export default function App() {
  const [tab, setTab] = useState('file'); // 'file' | 'url'
  const [step, setStep] = useState(STEP.UPLOAD);
  const [status, setStatus] = useState(STATUS.IDLE);
  const [error, setError] = useState('');

  const [selectedFile, setSelectedFile] = useState(null);
  const [extractedText, setExtractedText] = useState('');
  const [summary, setSummary] = useState('');
  const [script, setScript] = useState('');
  const [audioUrl, setAudioUrl] = useState('');

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

  // ── Step 1: extract text ─────────────────────────────────────────────
  const runExtract = async (source) => {
    setStatus(STATUS.LOADING);
    setError('');
    setStep(STEP.EXTRACT);
    try {
      let data;
      if (source.type === 'file') {
        data = await uploadPdf(source.file);
      } else {
        data = await fetchPdfUrl(source.url);
      }
      setExtractedText(data.text);
      setStep(STEP.SUMMARIZE);
      await runSummarize(data.text);
    } catch (e) {
      setError(e.message);
      setStatus(STATUS.ERROR);
      setStep(STEP.UPLOAD);
    }
  };

  // ── Step 2: summarize ────────────────────────────────────────────────
  const runSummarize = async (text) => {
    setStep(STEP.SUMMARIZE);
    try {
      const data = await summarizeText(text);
      setSummary(data.summary);
      setStep(STEP.PODCAST);
      await runPodcast(data.summary);
    } catch (e) {
      setError(e.message);
      setStatus(STATUS.ERROR);
    }
  };

  // ── Step 3: podcast + audio ──────────────────────────────────────────
  const runPodcast = async (sum) => {
    setStep(STEP.PODCAST);
    try {
      const data = await generatePodcast(sum);
      setScript(data.script);
      setAudioUrl(data.audio_url);
      setStep(STEP.AUDIO);
      setStatus(STATUS.DONE);
    } catch (e) {
      setError(e.message);
      setStatus(STATUS.ERROR);
    }
  };

  const handleFile = (file) => {
    setSelectedFile(file);
    runExtract({ type: 'file', file });
  };

  const handleUrl = (url) => {
    runExtract({ type: 'url', url });
  };

  return (
    <div
      style={{
        minHeight: '100vh',
        display: 'flex',
        flexDirection: 'column',
        background: 'radial-gradient(ellipse at 20% 0%, rgba(99,102,241,0.12) 0%, transparent 60%), var(--surface)',
      }}
    >
      {/* ── Navbar ─────────────────────────────────────────────────────── */}
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
          PodcastPDF
        </span>
        <span
          style={{
            marginLeft: 'auto',
            fontSize: '0.75rem',
            padding: '3px 10px',
            borderRadius: '99px',
            background: 'rgba(99,102,241,0.15)',
            color: 'var(--brand-from)',
            fontWeight: 600,
            letterSpacing: '0.04em',
          }}
        >
          AI-Powered
        </span>
      </header>

      {/* ── Hero ───────────────────────────────────────────────────────── */}
      <main style={{ flex: 1, maxWidth: '780px', width: '100%', margin: '0 auto', padding: '48px 20px 80px' }}>

        {/* Title */}
        <div style={{ textAlign: 'center', marginBottom: '48px' }}>
          <h1
            className="gradient-text fade-up"
            style={{ fontFamily: "'Outfit',sans-serif", fontWeight: 800, fontSize: 'clamp(2rem,5vw,3rem)', lineHeight: 1.15, marginBottom: '16px' }}
          >
            Turn Any PDF into a Podcast
          </h1>
          <p
            className="fade-up"
            style={{ color: 'var(--text-2)', fontSize: '1.05rem', lineHeight: 1.6, animationDelay: '0.1s' }}
          >
            Upload a PDF or paste a URL — our AI will extract, summarize, and narrate your document as an audio podcast.
          </p>
        </div>

        {/* Stepper */}
        {status !== STATUS.IDLE && (
          <div className="card fade-up" style={{ marginBottom: '32px', padding: '24px 16px' }}>
            <PipelineStepper currentStep={step} />
          </div>
        )}

        {/* ── Input card (only shown when not done) ─────────────────── */}
        {status !== STATUS.DONE && (
          <div className="card fade-up" style={{ marginBottom: '28px', animationDelay: '0.15s' }}>
            {/* Tabs */}
            <div style={{ display: 'flex', gap: '8px', marginBottom: '24px' }}>
              {['file', 'url'].map((t) => (
                <button
                  key={t}
                  id={`tab-${t}`}
                  onClick={() => {
                    if (busy) return;
                    setTab(t);
                    // If clicking the file tab, trigger the file picker
                    if (t === 'file') {
                      document.getElementById('pdf-file-input')?.click();
                    }
                  }}
                  className={tab === t ? 'btn-primary' : 'btn-secondary'}
                  style={{ flex: 1 }}
                  disabled={busy}
                >
                  {t === 'file' ? '📄 Upload File' : '🔗 Paste URL'}
                </button>
              ))}
            </div>

            {tab === 'file' ? (
              <DropZone onFile={handleFile} disabled={busy} />
            ) : (
              <UrlInput onUrl={handleUrl} disabled={busy} />
            )}

            {/* Troubleshooting Tip */}
            {!busy && status !== STATUS.ERROR && (
              <p style={{ marginTop: '16px', fontSize: '0.78rem', color: 'var(--text-3)', textAlign: 'center' }}>
                💡 Tip: Use a digital PDF for best results. Scanned documents (images) are not yet supported.
                <br />
                Need a test file? Try the <strong>sample_document.pdf</strong> in the project folder.
              </p>
            )}

            {/* Loading indicator */}
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
                    {step === STEP.EXTRACT && 'Extracting text from PDF…'}
                    {step === STEP.SUMMARIZE && 'Summarizing with AI…'}
                    {step === STEP.PODCAST && 'Writing podcast script & generating audio…'}
                  </p>
                  <p style={{ color: 'var(--text-3)', fontSize: '0.8rem', marginTop: '2px' }}>
                    This may take 20–60 seconds
                  </p>
                </div>
              </div>
            )}

            {/* Error */}
            {status === STATUS.ERROR && (
              <div
                style={{
                  marginTop: '20px',
                  padding: '14px 18px',
                  background: 'rgba(239,68,68,0.1)',
                  border: '1px solid rgba(239,68,68,0.3)',
                  borderRadius: '10px',
                  color: '#f87171',
                  fontSize: '0.88rem',
                }}
                role="alert"
              >
                ⚠️ {error}
              </div>
            )}
          </div>
        )}

        {/* ── Results ────────────────────────────────────────────────── */}
        {status === STATUS.DONE && (
          <div style={{ display: 'flex', flexDirection: 'column', gap: '20px' }}>
            {/* Audio player */}
            <AudioPlayer audioUrl={audioUrl} script={script} />

            {/* Summary card */}
            <div className="card fade-up" style={{ animationDelay: '0.15s' }}>
              <h2
                style={{ fontFamily: "'Outfit',sans-serif", fontWeight: 700, fontSize: '1rem', marginBottom: '12px', color: 'var(--text-1)' }}
              >
                📋 Document Summary
              </h2>
              <p style={{ color: 'var(--text-2)', fontSize: '0.88rem', lineHeight: 1.75, whiteSpace: 'pre-wrap' }}>
                {summary}
              </p>
            </div>

            {/* Extracted text accordion */}
            <div className="card fade-up" style={{ animationDelay: '0.25s' }}>
              <details>
                <summary
                  style={{ cursor: 'pointer', fontWeight: 600, fontSize: '0.9rem', color: 'var(--text-2)', userSelect: 'none', outline: 'none' }}
                >
                  📄 Raw extracted text ({extractedText.length.toLocaleString()} chars)
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

            {/* Start over */}
            <div style={{ textAlign: 'center', marginTop: '8px' }}>
              <button id="start-over-btn" className="btn-secondary" onClick={reset}>
                ↩ Process Another PDF
              </button>
            </div>
          </div>
        )}

        {/* ── How it works (shown on idle) ──────────────────────────── */}
        {status === STATUS.IDLE && (
          <div className="fade-up" style={{ animationDelay: '0.25s' }}>
            <h2
              style={{ textAlign: 'center', fontFamily: "'Outfit',sans-serif", fontWeight: 700, fontSize: '1.1rem', color: 'var(--text-2)', marginBottom: '20px' }}
            >
              How it works
            </h2>
            <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit,minmax(160px,1fr))', gap: '16px' }}>
              {[
                { icon: '📄', title: 'Upload PDF', desc: 'Drop a file or paste a URL' },
                { icon: '🔍', title: 'Extract', desc: 'Text is pulled from every page' },
                { icon: '🤖', title: 'Summarize', desc: 'GPT condenses key insights' },
                { icon: '🎙️', title: 'Narrate', desc: 'Script written for podcasting' },
                { icon: '🎧', title: 'Listen', desc: 'Audio generated & ready to play' },
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

      {/* ── Footer ─────────────────────────────────────────────────────── */}
      <footer style={{ textAlign: 'center', padding: '20px', color: 'var(--text-3)', fontSize: '0.78rem', borderTop: '1px solid var(--border)' }}>
        PodcastPDF &mdash; Powered by OpenAI &amp; FastAPI
      </footer>
    </div>
  );
}
