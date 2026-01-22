-- ============================================================================
-- V1__CREATE_TABLES.sql
-- Description: Initial database schema - Creates base users table
-- Database: PostgreSQL
-- ============================================================================

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    lastname VARCHAR(100),
    email VARCHAR(150) NOT NULL,
    apellido_paterno VARCHAR(100) NOT NULL,
    apellido_materno VARCHAR(100) NOT NULL,
    edad INTEGER NOT NULL,
    dni VARCHAR(8) NOT NULL,
    telefono VARCHAR(12) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    version INTEGER DEFAULT 0
);

-- Comment on table
COMMENT ON TABLE users IS 'User accounts for hexagonal architecture';
