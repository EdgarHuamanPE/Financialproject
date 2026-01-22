-- ============================================================================
-- V5__INSERT_DATA.sql
-- Description: Insert initial seed data for development and testing
-- ============================================================================

-- Insert sample users with USER role
INSERT INTO users (name, lastname,apellido_paterno,apellido_materno, email,password,edad,dni ,telefono,provider, enabled, role_id, created_at) VALUES
('David', 'Galvez', 'galvez','carranza','david.galvez@credibank.com','$2a$12$q1VENtQWeb5TNiIrQ6GDd.kJlMURdWSSE1CrMaXQoUFnuRrQ5SImu',30,'45656456','51999999999' ,'LOCAL', TRUE, 1, NOW()),
('Rocio', 'Chavez', 'chavez','barzola','rocio.chavez@credibank.com','$2a$12$A51JOtQffI/FRVy1ClZC/ukwB7h/z9ocFcITcf4IqpFP17j13eXfC',25, '43626456','51999999999','LOCAL', TRUE, 1, NOW()),
('Monitor', 'Monitor', 'monitor','monitor','monitor@credibank.com','$2a$12$f0v.2WaQ/u3ju0y68v76DuvHx.wZZ2oV0D6cd9bfMxgK4nTvEv/qa',18,'40656453','51999999999','LOCAL', TRUE, 3, NOW());

-- Insert admin user with hashed password (admin123)
INSERT INTO users (name, lastname,apellido_paterno,apellido_materno, email,edad, dni,telefono,password, provider, enabled, role_id, created_at)
VALUES (
    'Admin',
    'User',
    'paterno',
    'materno',
    'admin@hexagonal-demo.com',
    12,
    '45656456',
    '51999999999',
    '$2a$12$gBpsIP1vjx4scbpkKgh8w.LA2n0zOie4S86mSJ6D/ByjKdAInZOG2',
    'LOCAL',
    TRUE,
    2,
    CURRENT_TIMESTAMP
);