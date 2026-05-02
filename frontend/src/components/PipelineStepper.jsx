import { useTranslation } from 'react-i18next';

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
            <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', gap: '6px' }}>
              <div
                style={{
                  width: '36px', height: '36px',
                  borderRadius: '50%',
                  display: 'flex', alignItems: 'center', justifyContent: 'center',
                  fontWeight: 700, fontSize: '0.85rem',
                  background: done
                    ? 'linear-gradient(135deg,#6366f1,#818cf8)'
                    : active
                    ? '#fff'
                    : '#f3f4f6',
                  color: done ? '#fff' : active ? '#6366f1' : '#9ca3af',
                  boxShadow: active
                    ? '0 0 0 3px rgba(99,102,241,0.18), 0 2px 8px rgba(99,102,241,0.2)'
                    : done
                    ? '0 2px 8px rgba(99,102,241,0.2)'
                    : 'none',
                  transition: 'all 0.3s ease',
                  border: active
                    ? '2px solid #6366f1'
                    : done
                    ? '2px solid transparent'
                    : '2px solid #e5e7eb',
                }}
              >
                {done ? '✓' : i + 1}
              </div>
              <span
                style={{
                  fontSize: '0.68rem',
                  color: active ? '#6366f1' : done ? '#4b5563' : '#9ca3af',
                  fontWeight: active ? 700 : done ? 600 : 400,
                  whiteSpace: 'nowrap',
                  transition: 'color 0.3s',
                }}
              >
                {t(step.key)}
              </span>
            </div>

            {i < STEPS.length - 1 && (
              <div
                style={{
                  width: '40px',
                  height: '2px',
                  background: done
                    ? 'linear-gradient(90deg,#6366f1,#818cf8)'
                    : '#e5e7eb',
                  marginBottom: '22px',
                  transition: 'background 0.4s ease',
                  borderRadius: '2px',
                }}
              />
            )}
          </div>
        );
      })}
    </div>
  );
}
