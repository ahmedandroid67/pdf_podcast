import { useRef, useState, useEffect } from 'react';
import { API_BASE } from '../api';
import { useTranslation } from 'react-i18next';
import WaveformBars from './WaveformBars';

export default function AudioPlayer({ audioUrl, script }) {
  const { t } = useTranslation();
  const audioRef = useRef(null);
  const [playing, setPlaying] = useState(false);
  const [progress, setProgress] = useState(0);
  const [duration, setDuration] = useState(0);
  const [currentTime, setCurrentTime] = useState(0);
  const [resolvedUrl, setResolvedUrl] = useState('');

  useEffect(() => {
    setPlaying(false);
    setProgress(0);
    setCurrentTime(0);
  }, [audioUrl]);

  useEffect(() => {
    let active = true;
    let objectUrl = '';

    const loadAudio = async () => {
      if (!audioUrl) { setResolvedUrl(''); return; }
      if (!audioUrl.startsWith('/api/audio')) { setResolvedUrl(audioUrl); return; }
      try {
        const token = localStorage.getItem('auth_token');
        const res = await fetch(`${API_BASE}${audioUrl}`, {
          headers: token ? { Authorization: `Bearer ${token}` } : {},
        });
        if (!res.ok) throw new Error('Failed to load audio');
        const blob = await res.blob();
        objectUrl = URL.createObjectURL(blob);
        if (active) setResolvedUrl(objectUrl);
      } catch {
        if (active) setResolvedUrl('');
      }
    };

    loadAudio();
    return () => {
      active = false;
      if (objectUrl) URL.revokeObjectURL(objectUrl);
    };
  }, [audioUrl]);

  const toggle = () => {
    if (!audioRef.current) return;
    if (playing) audioRef.current.pause();
    else audioRef.current.play();
  };

  const onTimeUpdate = () => {
    if (!audioRef.current) return;
    const ct = audioRef.current.currentTime;
    const dur = audioRef.current.duration || 1;
    setCurrentTime(ct);
    setProgress((ct / dur) * 100);
  };

  const onSeek = (e) => {
    if (!audioRef.current) return;
    const pct = Number(e.target.value);
    audioRef.current.currentTime = (pct / 100) * (audioRef.current.duration || 0);
    setProgress(pct);
  };

  const fmt = (s) => `${Math.floor(s / 60)}:${String(Math.floor(s % 60)).padStart(2, '0')}`;

  return (
    <div style={{
      background: '#fff', border: '1.5px solid #e5e7eb', borderRadius: '16px',
      padding: '24px', display: 'flex', flexDirection: 'column', gap: '20px',
      boxShadow: '0 2px 12px rgba(0,0,0,0.05)',
    }}>
      {/* Header */}
      <div style={{ display: 'flex', alignItems: 'center', gap: '14px' }}>
        <div style={{
          width: '46px', height: '46px', borderRadius: '12px',
          background: 'linear-gradient(135deg, #6366f1, #818cf8)',
          display: 'flex', alignItems: 'center', justifyContent: 'center',
          fontSize: '1.2rem',
          boxShadow: '0 4px 14px rgba(99,102,241,0.28)',
          flexShrink: 0,
        }}>
          🎙️
        </div>
        <div style={{ flex: 1, minWidth: 0 }}>
          <p style={{
            fontFamily: "'Plus Jakarta Sans', system-ui, sans-serif",
            fontWeight: 700, fontSize: '0.95rem', color: '#111827', marginBottom: '2px',
          }}>
            {t('audio_episode')}
          </p>
          <p style={{ color: '#9ca3af', fontSize: '0.78rem', fontWeight: 500 }}>{t('audio_narration')}</p>
        </div>
        <WaveformBars playing={playing} />
      </div>

      {/* Hidden audio */}
      <audio
        ref={audioRef}
        src={resolvedUrl}
        onPlay={() => setPlaying(true)}
        onPause={() => setPlaying(false)}
        onEnded={() => { setPlaying(false); setProgress(0); setCurrentTime(0); }}
        onTimeUpdate={onTimeUpdate}
        onLoadedMetadata={() => setDuration(audioRef.current?.duration || 0)}
        preload="metadata"
      />

      {/* Progress */}
      <div>
        <div style={{ position: 'relative', height: '5px', background: '#e5e7eb', borderRadius: '99px', overflow: 'hidden', marginBottom: '6px' }}>
          <div style={{
            position: 'absolute', left: 0, top: 0, height: '100%',
            width: `${progress}%`,
            background: 'linear-gradient(90deg, #6366f1, #818cf8)',
            borderRadius: '99px',
            transition: 'width 0.1s linear',
          }} />
        </div>
        <input
          id="audio-seek"
          type="range"
          min="0" max="100" step="0.1"
          value={progress}
          onChange={onSeek}
          aria-label="Audio progress"
          style={{
            width: '100%', cursor: 'pointer',
            accentColor: '#6366f1', position: 'absolute',
            opacity: 0, height: '5px', marginTop: '-11px',
          }}
        />
        <div style={{ display: 'flex', justifyContent: 'space-between', fontSize: '0.75rem', color: '#9ca3af', fontWeight: 500 }}>
          <span>{fmt(currentTime)}</span>
          <span>{fmt(duration)}</span>
        </div>
      </div>

      {/* Controls */}
      <div style={{ display: 'flex', alignItems: 'center', gap: '10px', justifyContent: 'center' }}>
        <button
          id="rewind-btn"
          className="btn-secondary"
          style={{ padding: '9px 14px', fontSize: '0.82rem' }}
          onClick={() => { if (audioRef.current) audioRef.current.currentTime -= 10; }}
          aria-label={t('audio_rewind')}
        >
          {t('audio_rewind')}
        </button>

        <button
          id="play-pause-btn"
          className="btn-primary"
          style={{ padding: '11px 32px', fontSize: '1rem', minWidth: '110px' }}
          onClick={toggle}
          aria-label={playing ? t('audio_pause') : t('audio_play')}
        >
          {playing ? t('audio_pause') : t('audio_play')}
        </button>

        <button
          id="forward-btn"
          className="btn-secondary"
          style={{ padding: '9px 14px', fontSize: '0.82rem' }}
          onClick={() => { if (audioRef.current) audioRef.current.currentTime += 10; }}
          aria-label={t('audio_forward')}
        >
          {t('audio_forward')}
        </button>
      </div>

      {/* Download */}
      <a
        id="download-audio-link"
        href={resolvedUrl}
        download
        style={{
          textAlign: 'center', fontSize: '0.82rem',
          color: '#6366f1', textDecoration: 'none', fontWeight: 600,
          padding: '8px', borderRadius: '8px', transition: 'background 0.15s',
        }}
        onMouseEnter={e => e.currentTarget.style.background = '#eef2ff'}
        onMouseLeave={e => e.currentTarget.style.background = 'transparent'}
      >
        ↓ {t('audio_download')}
      </a>

      {/* Script toggle */}
      {script && (
        <details style={{ marginTop: '4px' }}>
          <summary style={{
            cursor: 'pointer', color: '#6b7280', fontSize: '0.85rem',
            fontWeight: 600, userSelect: 'none', outline: 'none', listStyle: 'none',
          }}>
            {t('audio_view_script')} ▾
          </summary>
          <div style={{
            marginTop: '12px', padding: '16px',
            background: '#f9fafb', borderRadius: '10px', border: '1px solid #e5e7eb',
            fontSize: '0.85rem', lineHeight: 1.75, color: '#4b5563',
            whiteSpace: 'pre-wrap', maxHeight: '300px', overflowY: 'auto',
          }}>
            {script}
          </div>
        </details>
      )}
    </div>
  );
}
