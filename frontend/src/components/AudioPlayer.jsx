import { useRef, useState, useEffect } from 'react';
import WaveformBars from './WaveformBars';

export default function AudioPlayer({ audioUrl, script }) {
  const audioRef = useRef(null);
  const [playing, setPlaying] = useState(false);
  const [progress, setProgress] = useState(0);
  const [duration, setDuration] = useState(0);
  const [currentTime, setCurrentTime] = useState(0);

  useEffect(() => {
    // Reset when URL changes
    setPlaying(false);
    setProgress(0);
    setCurrentTime(0);
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
            Podcast Episode
          </p>
          <p style={{ color: 'var(--text-3)', fontSize: '0.8rem' }}>AI-Generated Narration</p>
        </div>
        <div style={{ marginLeft: 'auto' }}>
          <WaveformBars playing={playing} />
        </div>
      </div>

      {/* Native audio (hidden) */}
      <audio
        ref={audioRef}
        src={audioUrl}
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
          aria-label="Rewind 10 seconds"
        >
          ⏪ 10s
        </button>

        {/* Play / Pause */}
        <button
          id="play-pause-btn"
          className="btn-primary"
          style={{ padding: '12px 32px', fontSize: '1.1rem' }}
          onClick={toggle}
          aria-label={playing ? 'Pause podcast' : 'Play podcast'}
        >
          {playing ? '⏸ Pause' : '▶ Play'}
        </button>

        {/* Forward 10s */}
        <button
          id="forward-btn"
          className="btn-secondary"
          style={{ padding: '10px 14px' }}
          onClick={() => { if (audioRef.current) audioRef.current.currentTime += 10; }}
          aria-label="Forward 10 seconds"
        >
          10s ⏩
        </button>
      </div>

      {/* Download link */}
      <a
        id="download-audio-link"
        href={audioUrl}
        download
        style={{ textAlign: 'center', fontSize: '0.85rem', color: 'var(--brand-from)', textDecoration: 'none' }}
      >
        ⬇ Download MP3
      </a>

      {/* Podcast script */}
      {script && (
        <details style={{ marginTop: '4px' }}>
          <summary
            style={{ cursor: 'pointer', color: 'var(--text-2)', fontSize: '0.88rem', userSelect: 'none', outline: 'none' }}
          >
            📜 View podcast script
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
