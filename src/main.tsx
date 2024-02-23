import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App'
import './index.css'

import { BrowserRouter, HashRouter } from 'react-router-dom'
import { AuthProvider } from './Context/Auth'
ReactDOM.createRoot(document.getElementById('root') as HTMLElement).render(
  <React.StrictMode>
    <AuthProvider>
      <BrowserRouter>
        <App />
      </BrowserRouter>
      
    </AuthProvider>
  </React.StrictMode>,
)
