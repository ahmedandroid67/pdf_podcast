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
    // Reset when URL changes
    setPlaying(false);
    setProgress(0);
    setCurrentTime(0);
  }, [audioUrl]);

  useEffect(() => {
    let active = true;
    let objectUrl = '';

    const loadAudio = async () => {
      if (!audioUrl) {
        setResolvedUrl('');
        return;
      }
      if (!audioUrl.startsWith('/api/audio')) {
        setResolvedUrl(audioUrl);
        return;
      }
      try {
        const token = localStorage.getItem('auth_token');
        const res = await fetch(`${API_BASE}${audioUrl}`, {
          headers: token ? { Authorization: `Bearer ${token}` } : {},
        });
        if (!res.ok) {
          throw new Error('Failed to load audio');
        }
        const blob = await res.blob();
        objectUrl = URL.createObjectURL(blob);
        if (active) {
          setResolvedUrl(objectUrl);
        }
      } catch {
        if (active) {
          setResolvedUrl('');
        }
      }
    };

    loadAudio();

    return () => {
      active = false;
      if (objectUrl) {
        URL.revokeObjectURL(objectUrl);
      }
    };
  }, [audioUrl]);

  const toggle = () => {
    if (!audioRef.current) return;
    if (playing) {
      audioRef.current.pause();
    } else {
      audioRef.current.play();
    }
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
    <div className="card fade-up" style={{ display: 'flex', flexDirection: 'column', gap: '20px' }}>
      {/* Header */}
      <div style={{ display: 'flex', alignItems: 'center', gap: '12px' }}>
        <div
          style={{
            width: '44px', height: '44px',
            borderRadius: '50%',
            background: 'linear-gradient(135deg,#6366f1,#8b5cf6)',
            display: 'flex', alignItems: 'center', justifyContent: 'center',
            fontSize: '1.2rem',
            boxShadow: '0 0 20px rgba(99,102,241,0.4)',
          }}
        >
          🎙️
        </div>
        <div>
          <p style={{ fontFamily: "'Outfit',sans-serif", fontWeight: 700, fontSize: '1rem' }}>
            {t('audio_episode')}
          </p>
          <p style={{ color: 'var(--text-3)', fontSize: '0.8rem' }}>{t('audio_narration')}</p>
        </div>
        <div style={{ marginLeft: 'auto' }}>
          <WaveformBars playing={playing} />
        </div>
      </div>

      {/* Native audio (hidden) */}
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

      {/* Progress bar */}
      <div>
        <input
          id="audio-seek"
          type="range"
          min="0"
          max="100"
          step="0.1"
          value={progress}
          onChange={onSeek}
          aria-label="Audio progress"
          style={{
            width: '100%',
            accentColor: 'var(--brand-from)',
            cursor: 'pointer',
          }}
        />
        <div style={{ display: 'flex', justifyContent: 'space-between', fontSize: '0.78rem', color: 'var(--text-3)', marginTop: '4px' }}>
          <span>{fmt(currentTime)}</span>
          <span>{fmt(duration)}</span>
        </div>
      </div>

      {/* Controls */}
      <div style={{ display: 'flex', alignItems: 'center', gap: '12px', justifyContent: 'center' }}>
        {/* Rewind 10s */}
        <button
          id="rewind-btn"
          className="btn-secondary"
          style={{ padding: '10px 14px' }}
          onClick={() => { if (audioRef.current) audioRef.current.currentTime -= 10; }}
          aria-label={t('audio_rewind')}
        >
          {t('audio_rewind')}
        </button>

        {/* Play / Pause */}
        <button
          id="play-pause-btn"
          className="btn-primary"
          style={{ padding: '12px 32px', fontSize: '1.1rem' }}
          onClick={toggle}
          aria-label={playing ? t('audio_pause') : t('audio_play')}
        >
          {playing ? t('audio_pause') : t('audio_play')}
        </button>

        {/* Forward 10s */}
        <button
          id="forward-btn"
          className="btn-secondary"
          style={{ padding: '10px 14px' }}
          onClick={() => { if (audioRef.current) audioRef.current.currentTime += 10; }}
          aria-label={t('audio_forward')}
        >
          {t('audio_forward')}
        </button>
      </div>

      {/* Download link */}
      <a
        id="download-audio-link"
        href={resolvedUrl}
        download
        style={{ textAlign: 'center', fontSize: '0.85rem', color: 'var(--brand-from)', textDecoration: 'none' }}
      >
        {t('audio_download')}
      </a>

      {/* Podcast script */}
      {script && (
        <details style={{ marginTop: '4px' }}>
          <summary
            style={{ cursor: 'pointer', color: 'var(--text-2)', fontSize: '0.88rem', userSelect: 'none', outline: 'none' }}
          >
            {t('audio_view_script')}
          </summary>
          <div
            style={{
              marginTop: '12px',
              padding: '16px',
              background: 'var(--surface-3)',
              borderRadius: '10px',
              fontSize: '0.88rem',
              lineHeight: 1.7,
              color: 'var(--text-2)',
              whiteSpace: 'pre-wrap',
              maxHeight: '320px',
              overflowY: 'auto',
            }}
          >
            {script}
          </div>
        </details>
      )}
    </div>
  );
}
