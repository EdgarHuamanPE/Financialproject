# 🧩 Hexagonal Architecture - User Management API

Este proyecto implementa una **API REST** con arquitectura **Hexagonal (Ports & Adapters)** utilizando **Spring Boot**.  
Su objetivo es demostrar buenas prácticas de separación de responsabilidades, independencia de frameworks y fácil testeo.

---

## 🚀 Características principales

- Arquitectura **Hexagonal (Ports & Adapters)**
- Gestión de usuarios con CRUD básico
- Manejo de roles (`USER`, `ADMIN`, `MONITOR`)
- Validaciones de datos con `javax.validation`
- Excepciones personalizadas (`UserNotFoundException`, etc.)
- Pruebas unitarias con **JUnit 5** y **Mockito**

---

## 🧱 Estructura del proyecto

src/
├── main/
│ ├── java/com/tecsup/example/hexagonal/
│ │ ├── application/ # Casos de uso (servicios)
│ │ ├── domain/ # Modelos de dominio (entidades, lógica)
│ │ ├── infrastructure/
│ │ │ ├── adapter/
│ │ │ │ ├── input/rest/ # Controladores REST (entradas)
│ │ │ │ ├── output/persistence/ # Adaptadores de salida (DB)
│ │ │ └── config/ # Configuración de Beans, mappers, etc.
│ │ └── HexagonalApplication.java
│ └── resources/
│ ├── application.properties
│ └── data.sql (opcional)
└── test/
└── java/... # Pruebas unitarias e integración

## ⚙️ Requisitos previos

- **Java 17** o superior
- **Maven 3.9.6**
- **Spring Boot 3.5.6**
- Base de datos (Mysql)

## Probar en el navegador o Postman
    http://localhost:8080/api/users
