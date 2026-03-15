/** Animated waveform bars used to indicate audio is playing */
export default function WaveformBars({ playing }) {
  const bars = [0.4, 0.7, 1, 0.6, 0.9, 0.5, 0.8, 1, 0.6, 0.4];

  return (
    <div
      style={{
        display: 'flex',
        alignItems: 'center',
        gap: '3px',
        height: '28px',
      }}
      aria-hidden="true"
    >
      {bars.map((scale, i) => (
        <span
          key={i}
          style={{
            display: 'block',
            width: '4px',
            height: `${scale * 100}%`,
            borderRadius: '3px',
            background: 'linear-gradient(180deg,#6366f1,#8b5cf6)',
            transformOrigin: 'bottom',
            animation: playing
              ? `waveBar ${0.5 + i * 0.08}s ease-in-out infinite alternate`
              : 'none',
            transform: playing ? undefined : `scaleY(${scale * 0.3})`,
            transition: 'transform 0.3s ease',
          }}
        />
      ))}
    </div>
  );
}
