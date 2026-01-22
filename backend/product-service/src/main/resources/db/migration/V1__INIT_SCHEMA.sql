-- tabla producto
CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(20) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(50) NOT NULL,
    category VARCHAR(50),
    currency VARCHAR(3),
    interest_rate DECIMAL(5,2),
    description VARCHAR(255),
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVO',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_product_status CHECK (status IN ('ACTIVO', 'INACTIVO', 'SUSPENDIDO', 'CERRADO'))
);

CREATE INDEX idx_products_created_at ON products(created_at DESC);
CREATE INDEX idx_products_type ON products(type);
CREATE INDEX idx_products_category ON products(category);
CREATE INDEX idx_products_type_category ON products(type, category);
CREATE INDEX idx_products_status ON products(status);

-- Cuentas de ahorro y corrientes
INSERT INTO products  (code, name, type, category, currency, interest_rate, description, status)
VALUES
('SAVINGS_BASIC', 'Cuenta de Ahorros Clásica', 'AHORRO', 'Cuenta básica', 'PEN', 0.50, 'Cuenta de ahorros sin costo de mantenimiento.', 'ACTIVO'),
('SAVINGS_PLUS', 'Cuenta de Ahorros Plus', 'AHORRO', 'Cuenta premium', 'PEN', 1.00, 'Cuenta con mayor tasa por saldo promedio superior a S/ 5,000.', 'ACTIVO'),
('CURRENT_ACCOUNT', 'Cuenta Corriente', 'AHORRO', 'Empresarial', 'PEN', 0.00, 'Cuenta para operaciones empresariales con chequera.', 'ACTIVO');

-- Tarjetas de crédito
INSERT INTO products (code, name, type, category, currency, interest_rate, description, status)
VALUES
('CREDIT_CLASSIC', 'Tarjeta de Crédito Clásica', 'CRÉDITO', 'Tarjeta consumo', 'PEN', 45.00, 'Tarjeta con línea de crédito personal y pagos mensuales.', 'ACTIVO'),
('CREDIT_GOLD', 'Tarjeta de Crédito Gold', 'CRÉDITO', 'Tarjeta consumo', 'USD', 39.00, 'Tarjeta con beneficios internacionales y acumulación de puntos.', 'ACTIVO'),
('CREDIT_BUSINESS', 'Tarjeta Empresarial', 'CRÉDITO', 'Tarjeta empresarial', 'PEN', 35.00, 'Tarjeta para compras de negocios con control de gastos.', 'ACTIVO');

-- Préstamos
INSERT INTO products (code, name, type, category, currency, interest_rate, description, status)
VALUES
('LOAN_PERSONAL', 'Préstamo Personal', 'CRÉDITO', 'Consumo', 'PEN', 12.50, 'Crédito para necesidades personales con cuotas mensuales fijas.', 'ACTIVO'),
('LOAN_VEHICLE', 'Préstamo Vehicular', 'CRÉDITO', 'Automotriz', 'PEN', 9.50, 'Financiamiento para compra de autos nuevos o usados.', 'ACTIVO'),
('LOAN_MORTGAGE', 'Crédito Hipotecario', 'CRÉDITO', 'Vivienda', 'PEN', 8.00, 'Financiamiento para compra o construcción de vivienda.', 'ACTIVO');

-- Depósitos e inversiones
INSERT INTO products (code, name, type, category, currency, interest_rate, description, status)
VALUES
('FIXED_TERM', 'Depósito a Plazo Fijo', 'INVERSIÓN', 'Plazo fijo', 'PEN', 6.00, 'Depósito con tasa fija según plazo contratado.', 'ACTIVO'),
('MUTUAL_FUND', 'Fondo Mutuo Conservador', 'INVERSIÓN', 'Fondo mutuo', 'PEN', 0.00, 'Inversión colectiva con perfil conservador.', 'ACTIVO');

-- Seguros
INSERT INTO products (code, name, type, category, currency, interest_rate, description, status)
VALUES
('INSURANCE_LIFE', 'Seguro de Vida', 'SEGURO', 'Vida individual', 'PEN', NULL, 'Protección económica ante fallecimiento del asegurado.', 'ACTIVO'),
('INSURANCE_CARD', 'Seguro contra Fraude en Tarjeta', 'SEGURO', 'Tarjeta', 'PEN', NULL, 'Cobertura ante robos o fraudes con tarjeta.', 'ACTIVO');
