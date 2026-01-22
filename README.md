# 🚀 Financial Products Platform

El presente proyecto "Financial Products Platform" es parte de proyecto final del bootcamp
Desarollo web Fulltack con Java en Tecsup. Basicamente resulta un proyecto académico que
simula un entorno real de un sistema de banca / fintech aplicando buenas prácticas de
arquitectura, seguridad y organización de código.

La funcion principal del sistema es gestionar productos, clientes y ventas de productos
financieros.

## 📦 ESTRUCTURA DEL PROYECTO
El proyecto está organizado en dos grandes capas:
- **Backend**: Microservicios en **Java + Spring Boot**
- **Frontend**: React + Vite + Tailwind CSS
- **Autenticación**: Basada en **JWT** mediante un microservicio dedicado(Auth-service)

Cada microservicio es **independiente**, con su propia base de datos y responsabilidades bien definidas.

```
    project-root/
    ├── backend/
    │ ├── auth-service/
    │ ├── customer-service/
    │ ├── product-service/
    │ └── financial-origination-service/
    │
    ├── frontend/
    │ └── web-app/
    │
    ├── README.md
    └── .gitignore
```
## 🔙 Backend – Microservicios

Actualmente el backend está conformado por los siguientes microservicios:

### 1️⃣ Product Service

**Responsabilidad**

Gestiona la información de los productos financieros disponibles en el sistema.

**Funciones principales**

- Creación y mantenimiento de productos financieros
- Consulta de productos activos
- Gestión de atributos propios del producto (código, nombre, tipo, estado, etc.)

**Ejemplos de productos**

- Cuenta de ahorros
- Cuenta corriente
- Crédito personal
- Otros productos financieros

### 2️⃣ Customer Service

**Responsabilidad**

Administra la información de los clientes del sistema.

**Funciones principales**

- Registro de clientes
- Consulta por distintos criterios (ID y documento)
- Gestión de datos personales y estado del cliente

Este microservicio **no gestiona productos**, solo información del cliente.

### 3️⃣ Financial Origination Service

**Responsabilidad**

Gestiona la **originación de productos financieros**, es decir, la relación
entre **clientes y productos**.

Este microservicio actúa como el **núcleo del negocio**.

**Funciones principales**

- Asociación de clientes con productos financieros
- Registro de ventas u originaciones
- Validaciones de negocio previas a la originación
- Integración con:
    * Customer Service
    * Product Service

📌 Este servicio **no crea clientes ni productos**, solo los consume
desde los otros microservicios.

### 4️⃣ Auth Service

**Responsabilidad**

Encargado de la **autenticación y autorización** del sistema.

**Funciones principales**

- Login de usuarios
- Generación de **JWT**
- Gestión de roles y permisos
- Validación de tokens para el resto de microservicios

**Seguridad**

- Autenticación basada en JWT
- Roles propagados a los microservicios consumidores
- Integración con Spring Security

## 🔐 Seguridad y Autenticación

- El acceso a los endpoints está protegido mediante **Spring Security**
- Los microservicios confían en el **Auth Service** para validar el token
- Los roles del usuario se extraen desde el JWT


## 🧩 Comunicación entre Microservicios

- Arquitectura desacoplada
- Comunicación vía **REST APIs**
- Cada microservicio mantiene su propio modelo y base de datos

## 🏗️ Arquitectura del sistema backend


```
┌─────────────────────────────────────────────────────────────────────┐
│            ARQUITECTURA BACKEND COMPLETA (PROYECTO FINANCIERO)      │
└─────────────────────────────────────────────────────────────────────┘

             
     ┌────────────┐ ┌────────────┐ ┌────────────┐ ┌────────────────┐
     │ Auth       │ │ Customer   │ │ Product    │ │  Financial     │
     │ Service    │ │ Service    │ │ Service    │ │  Origination   │
     │ :9090      │ │ :9080      │ │ :9081      │ │  Service:9082  │
     └──────┬─────┘ └──────┬─────┘ └──────┬─────┘ └──────┬─────────┘
            │              │              │              │
            │              │              │              │
            ▼              ▼              ▼              ▼
       ┌────────┐     ┌──────────┐    ┌─────────┐    ┌────────────┐
       │ authdb │     │customerdb│    │productdb│    │financialdb │
       │ :5438  │     │ :5435    │    │ :5436   │    │ :5437      │
       └────────┘     └──────────┘    └─────────┘    └────────────┘

COMUNICACIÓN ENTRE MICROSERVICIOS:

- Financial Origination Service ──(HTTP)──► Customer Service
- Financial Origination Service ──(HTTP)──► Product Service
- Todos los microservicios extraen roles y permisos desde Auth Service mediante JWT
- API Gateway centraliza rutas y aplica seguridad a todos los servicios

```

## 📊 Modelo de datos

### Diagrama Entidad-Relación
```
┌─────────────────────────────┐
│          CUSTOMERS          │
├─────────────────────────────┤
│ PK  id                      │
│     document_type           │
│     document_number (UNIQUE)│
│     first_name              │
│     last_name               │
│     email                   │
│     phone                   │
│     status                  │
│     created_at              │
│     updated_at              │
└─────────────┬───────────────┘
              │ 1
              │
              │ N
              ▼
┌─────────────────────────────┐
│     CUSTOMER_PRODUCTS       │
├─────────────────────────────┤
│ PK  id                      │
│ FK  customer_id             │
│ FK  product_id              │
│     account_number (UNIQUE) │
│     balance                 │
│     status                  │
│     start_date              │
│     end_date                │
│     contract_number         │
│     channel_origin          │
│     created_at              │
│     updated_at              │
└─────────────┬───────────────┘
              │ N
              │
              │ 1
              ▼
┌─────────────────────────────┐
│          PRODUCTS           │
├─────────────────────────────┤
│ PK  id                      │
│     code (UNIQUE)           │
│     name                    │
│     type                    │
│     category                │
│     currency                │
│     interest_rate           │
│     description             │
│     status                  │
│     created_at              │
│     updated_at              │
└─────────────────────────────┘


┌─────────────────────────────┐
│            ROLES            │
├─────────────────────────────┤
│ PK  id                      │
│     name (UNIQUE)           │
│     description             │
│     created_at              │
└─────────────┬───────────────┘
              │ 1
              │
              │ N
              ▼
┌─────────────────────────────┐
│            USERS            │
├─────────────────────────────┤
│ PK  id                      │
│ FK  role_id                 │
│     name                    │
│     lastname                │
│     apellido_paterno        │
│     apellido_materno        │
│     email (UNIQUE)          │
│     password                │
│     dni                     │
│     telefono                │
│     provider                │
│     google_id               │
│     enabled                 │
│     last_login              │
│     created_at              │
│     updated_at              │
│     version                 │
└─────────────────────────────┘

```
### Tabla: products

| Campo | Tipo | Restricciones | Descripción |
|-------|------|---------------|-------------|
| id | BIGSERIAL | PRIMARY KEY | ID único del producto |
| code | VARCHAR(20) | UNIQUE, NOT NULL | Código del producto financiero |
| name | VARCHAR(100) | NOT NULL | Nombre del producto |
| type | VARCHAR(50) | NOT NULL | Tipo de producto financiero |
| category | VARCHAR(50) | NULL | Categoría del producto |
| currency | VARCHAR(3) | NULL | Moneda (PEN, USD) |
| interest_rate | DECIMAL(5,2) | NULL | Tasa de interés |
| description | VARCHAR(255) | NULL | Descripción del producto |
| status | VARCHAR(20) | NOT NULL, DEFAULT 'ACTIVO' | Estado del producto |
| created_at | TIMESTAMP | NOT NULL, DEFAULT NOW() | Fecha de creación |
| updated_at | TIMESTAMP | NOT NULL, DEFAULT NOW() | Fecha de actualización |

**Estados válidos:** `ACTIVO`, `INACTIVO`, `SUSPENDIDO`, `CERRADO`


### Tabla: customers

| Campo | Tipo | Restricciones | Descripción |
|-------|------|---------------|-------------|
| id | BIGSERIAL | PRIMARY KEY | ID único del cliente |
| document_type | VARCHAR(10) | NOT NULL | Tipo de documento |
| document_number | VARCHAR(20) | UNIQUE, NOT NULL | Número de documento |
| first_name | VARCHAR(50) | NOT NULL | Nombres del cliente |
| last_name | VARCHAR(50) | NOT NULL | Apellidos del cliente |
| email | VARCHAR(100) | NULL | Correo electrónico |
| phone | VARCHAR(20) | NULL | Teléfono |
| status | VARCHAR(20) | NOT NULL, DEFAULT 'ACTIVO' | Estado del cliente |
| created_at | TIMESTAMP | NOT NULL, DEFAULT NOW() | Fecha de creación |
| updated_at | TIMESTAMP | NOT NULL, DEFAULT NOW() | Fecha de actualización |

**Estados válidos:** `ACTIVO`, `INACTIVO`, `SUSPENDIDO`, `CERRADO`

### Tabla: customer_products

| Campo | Tipo | Restricciones | Descripción |
|-------|------|---------------|-------------|
| id | BIGSERIAL | PRIMARY KEY | ID único del contrato |
| customer_id | BIGINT | NOT NULL | ID del cliente (ref. externa) |
| product_id | BIGINT | NOT NULL | ID del producto (ref. externa) |
| account_number | VARCHAR(30) | UNIQUE, NOT NULL | Número de cuenta |
| start_date | TIMESTAMP | NOT NULL, DEFAULT NOW() | Fecha de inicio |
| end_date | TIMESTAMP | NULL | Fecha de cierre |
| status | VARCHAR(20) | NOT NULL, DEFAULT 'ACTIVO' | Estado del contrato |
| balance | DECIMAL(18,2) | NULL | Saldo del producto |
| contract_number | VARCHAR(30) | NULL | Número de contrato |
| channel_origin | VARCHAR(50) | NULL | Canal de origen |
| created_at | TIMESTAMP | NOT NULL, DEFAULT NOW() | Fecha de creación |
| updated_at | TIMESTAMP | NOT NULL, DEFAULT NOW() | Fecha de actualización |

**Estados válidos:** `ACTIVO`, `INACTIVO`, `SUSPENDIDO`, `CERRADO`

### Tabla: users

| Campo | Tipo | Restricciones | Descripción |
|-------|------|---------------|-------------|
| id | BIGSERIAL | PRIMARY KEY | ID único del usuario |
| name | VARCHAR(100) | NOT NULL | Nombre |
| lastname | VARCHAR(100) | NULL | Apellido |
| apellido_paterno | VARCHAR(100) | NOT NULL | Apellido paterno |
| apellido_materno | VARCHAR(100) | NOT NULL | Apellido materno |
| email | VARCHAR(150) | UNIQUE, NOT NULL | Correo electrónico |
| password | VARCHAR(100) | NOT NULL | Contraseña cifrada |
| edad | INTEGER | NOT NULL | Edad |
| dni | VARCHAR(8) | NOT NULL | Documento de identidad |
| telefono | VARCHAR(12) | NOT NULL | Teléfono |
| provider | VARCHAR(10) | NOT NULL, DEFAULT 'LOCAL' | Proveedor de autenticación |
| google_id | VARCHAR(255) | UNIQUE | ID OAuth |
| enabled | BOOLEAN | NOT NULL, DEFAULT TRUE | Usuario habilitado |
| role_id | BIGINT | NOT NULL | Rol asignado |
| last_login | TIMESTAMP | NULL | Último acceso |
| version | INTEGER | DEFAULT 0 | Control de versión |
| created_at | TIMESTAMP | NOT NULL, DEFAULT NOW() | Fecha de creación |
| updated_at | TIMESTAMP | NOT NULL, DEFAULT NOW() | Fecha de actualización |

### Tabla: roles

| Campo | Tipo | Restricciones | Descripción |
|-------|------|---------------|-------------|
| id | BIGSERIAL | PRIMARY KEY | ID único del rol |
| name | VARCHAR(50) | UNIQUE, NOT NULL | Nombre del rol |
| description | VARCHAR(255) | NULL | Descripción |
| created_at | TIMESTAMP | NOT NULL, DEFAULT NOW() | Fecha de creación |

## 🛠️ Tecnologías Backend

- Java 17+
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Security
- JWT
- PostgreSQL
- Maven
- Docker *( en progreso)*

## 🎨 Frontend

El frontend es una **Single Page Application (SPA)** que consume los microservicios del
backend mediante **APIs REST**.

### 🖥️ Funcionalidades

- Login y autenticación de usuarios
- Gestión de sesión mediante JWT
- Visualización de clientes
- Visualización de productos financieros
- Originación de productos para clientes
- Control de acceso según roles

### 🧩 Arquitectura Frontend

- Arquitectura basada en componentes
- Separación por features
- Manejo de rutas protegidas
- Consumo de APIs mediante HTTP cli
- JavaScript (ES6+)
- React Router
- Fetch API

## 🔄 Comunicación Backend – Frontend

- Comunicación vía **REST APIs**
- Autenticación mediante JWT
- Envío del token en headers (`Authorization: Bearer <token>`)
- Manejo de errores y estados HTTP

### 🛠️ Tecnologías Frontend

- React
- Vite
- Tailwind CSS
- Comunicación vía **REST APIs**
- Autenticación mediante JWT
- Envío del token en headers (`Authorization: Bearer <token>`)
- Manejo de errores y estados HTTP

## 🚀 Ejecución del Proyecto

### Backend
Cada microservicio se ejecuta de forma independiente:

```bash
    cd backend/auth-service
    mvn spring-boot:run
```

(Repetir para cada microservicio)

### Frontend
```bash
    cd frontend/web-app
    npm install
    npm run dev
```