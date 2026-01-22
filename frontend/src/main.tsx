import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './style.css'
import App from './App.tsx'
import {AuthProvider} from "@/app/providers/AuthProvider.tsx";
import {AdminProvider} from "@/app/providers/ModulesProvider.tsx";

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <AuthProvider>
        <AdminProvider>
            <App />
        </AdminProvider>
    </AuthProvider>
  </StrictMode>,
)
