import { useTranslation } from 'react-i18next';

/** Visual progress stepper shown across the top of the pipeline */
const STEPS = [
  { key: 'step_upload' },
  { key: 'step_extract' },
  { key: 'step_summarize' },
  { key: 'step_podcast' },
  { key: 'step_audio' },
];

export default function PipelineStepper({ currentStep }) {
  const { t } = useTranslation();

  return (
    <div
      role="list"
      style={{
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        gap: 0,
        flexWrap: 'wrap',
        rowGap: '12px',
      }}
    >
      {STEPS.map((step, i) => {
        const done = i < currentStep;
        const active = i === currentStep;
        return (
          <div key={i} role="listitem" style={{ display: 'flex', alignItems: 'center', gap: 0 }}>
            {/* Node */}
            <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', gap: '6px' }}>
              <div
                style={{
                  width: '36px', height: '36px',
                  borderRadius: '50%',
                  display: 'flex', alignItems: 'center', justifyContent: 'center',
                  fontWeight: 700, fontSize: '0.85rem',
                  background: done || active
                    ? 'linear-gradient(135deg,#6366f1,#8b5cf6)'
                    : 'rgba(99,102,241,0.1)',
                  color: done || active ? '#fff' : 'var(--text-3)',
                  boxShadow: active ? '0 0 16px rgba(99,102,241,0.55)' : 'none',
                  transition: 'all 0.3s ease',
                  border: active ? '2px solid rgba(139,92,246,0.6)' : '2px solid transparent',
                }}
              >
                {done ? '✓' : i + 1}
              </div>
              <span
                style={{
                  fontSize: '0.7rem',
                  color: active ? 'var(--text-1)' : done ? 'var(--brand-from)' : 'var(--text-3)',
                  fontWeight: active ? 600 : 400,
                  whiteSpace: 'nowrap',
                  transition: 'color 0.3s',
                }}
              >
                {t(step.key)}
              </span>
            </div>

            {/* Connector line */}
            {i < STEPS.length - 1 && (
              <div
                style={{
                  width: '48px',
                  height: '2px',
                  background: done
                    ? 'linear-gradient(90deg,#6366f1,#8b5cf6)'
                    : 'rgba(99,102,241,0.15)',
                  marginBottom: '22px',
                  transition: 'background 0.4s ease',
                }}
              />
            )}
          </div>
        );
      })}
    </div>
  );
}
