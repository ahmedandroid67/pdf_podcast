import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { registerSW } from 'virtual:pwa-register'
import './index.css'
import './i18n';
import App from './App.jsx'
import { AuthProvider } from './context/AuthContext.jsx';

registerSW({
  onNeedRefresh() {},
  onOfflineReady() {
    console.log('PdfParser is ready to work offline!');
  },
});

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <AuthProvider>
      <App />
    </AuthProvider>
  </StrictMode>,
)
