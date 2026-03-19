import { useEffect } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';

export default function AuthCallback() {
  const [searchParams] = useSearchParams();
  const navigate = useNavigate();

  useEffect(() => {
    const token = searchParams.get('token');
    if (token) {
      localStorage.setItem('auth_token', token);
      // Force a full page reload to reinitialize AuthContext with the new token
      window.location.replace('/');
    } else {
      navigate('/login?error=auth_failed');
    }
  }, [searchParams, navigate]);

  return (
    <div style={{ display: 'grid', placeItems: 'center', height: '100vh', flexDirection: 'column', gap: '16px' }}>
      <div className="spinner"></div>
      <p style={{ color: 'var(--text-2)' }}>Authenticating with Google...</p>
    </div>
  );
}
