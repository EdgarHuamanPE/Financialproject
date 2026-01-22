# 📊 Sistema de Ventas Financieros – Admin Panel

Panel administrativo desarrollado en **React** para la gestión de productos financieros, clientes y ventas.  
El proyecto implementa autenticación basada en token, consumo de API externa y una arquitectura modular orientada a escalabilidad.

---

## 🚀 Tecnologías utilizadas

- React + TypeScript
- React Router DOM
- Tailwind CSS
- Context API
- Fake Store API
- LocalStorage

---

## 🧠 Conceptos y hooks aplicados

Este proyecto fue construido aplicando buenas prácticas de React moderno:

### 🔹 useState

Utilizado para:
- Control de inputs (login, búsqueda)
- Estados locales de UI
- Manejo de datos temporales



## 📁 Estructura del proyecto

```text
src/
├─ app/
│  ├─ providers/        # Providers globales (Auth, Theme, etc.)
│  ├─ router/           # Configuración de rutas (react-router-dom)
│  └─ config/           # Configuración global y constantes
│
├─ features/
│  ├─ auth/             # Autenticación (login, logout, guards)
│  ├─ productos/        # Gestión de productos
│  ├─ clientes/         # Gestión de clientes
│  └─ ventas/           # Ventas financieras y reportes
│
├─ shared/
│  ├─ components/       # Componentes reutilizables (UI)
│  └─ hooks/            # Hooks reutilizables
│
├─ layouts/
│  └─ AdminLayout.tsx   # Layout del panel administrativo
|    
│-
├─ App.tsx              # Componente raíz
└─ main.tsx             # Punto de entrada de la aplicación
´´´