/** Small numbered circle used in the progress stepper */
export default function StepBadge({ number, active, done }) {
  const base =
    'w-8 h-8 rounded-full flex items-center justify-center text-sm font-semibold shrink-0 transition-all duration-300 ';

  if (done)
    return (
      <span className={base} style={{ background: 'linear-gradient(135deg,#6366f1,#8b5cf6)', color: '#fff' }}>
        ✓
      </span>
    );
  if (active)
    return (
      <span
        className={base}
        style={{
          background: 'linear-gradient(135deg,#6366f1,#8b5cf6)',
          color: '#fff',
          boxShadow: '0 0 16px rgba(99,102,241,0.6)',
        }}
      >
        {number}
      </span>
    );
  return (
    <span className={base} style={{ background: 'rgba(99,102,241,0.12)', color: '#6b6b8a' }}>
      {number}
    </span>
  );
}
