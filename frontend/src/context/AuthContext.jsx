import { createContext, useContext, useEffect, useMemo, useState } from 'react';
import { fetchMe, loginUser, registerUser } from '../api';

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const [token, setToken] = useState(() => localStorage.getItem('auth_token'));
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  const syncToken = (newToken) => {
    if (newToken) {
      localStorage.setItem('auth_token', newToken);
    } else {
      localStorage.removeItem('auth_token');
    }
    setToken(newToken);
  };

  const login = async (email, password) => {
    const data = await loginUser(email, password);
    syncToken(data.access_token);
    const me = await fetchMe();
    setUser(me);
    return me;
  };

  const register = async (email, password) => {
    await registerUser(email, password);
    return login(email, password);
  };

  const logout = () => {
    syncToken(null);
    setUser(null);
  };

  useEffect(() => {
    const load = async () => {
      if (!token) {
        setLoading(false);
        return;
      }
      try {
        const me = await fetchMe();
        setUser(me);
      } catch {
        syncToken(null);
        setUser(null);
      } finally {
        setLoading(false);
      }
    };
    load();
  }, [token]);

  const value = useMemo(
    () => ({ token, user, loading, login, register, logout }),
    [token, user, loading]
  );

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export function useAuth() {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth must be used within AuthProvider');
  }
  return context;
}
