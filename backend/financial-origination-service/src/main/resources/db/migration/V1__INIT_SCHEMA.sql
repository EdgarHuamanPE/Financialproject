--tabla transaction customer_products
CREATE TABLE customer_products
 (
    id BIGSERIAL PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    account_number VARCHAR(30) UNIQUE NOT NULL,
    start_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    end_date TIMESTAMP,
    status VARCHAR(20) DEFAULT 'ACTIVO' NOT NULL,
    balance DECIMAL(18,2),
    contract_number VARCHAR(30),
    channel_origin VARCHAR(50),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_customer_products_status CHECK (status IN ('ACTIVO', 'INACTIVO', 'SUSPENDIDO', 'CERRADO')),
    CONSTRAINT chk_customer_products_channel_origin CHECK (channel_origin IN ('AGENCIA','APP_MOVIL','CALL_CENTER','WEB') OR channel_origin IS NULL)
);


CREATE INDEX idx_cp_customer_id ON customer_products
(customer_id);
CREATE INDEX idx_cp_product_id ON customer_products
(product_id);
CREATE INDEX idx_cp_customer_status ON customer_products
(customer_id, status);
CREATE INDEX idx_cp_status ON customer_products
(status);
CREATE INDEX idx_cp_channel_origin ON customer_products
(channel_origin);


INSERT INTO customer_products

(customer_id, product_id, account_number, start_date, end_date, status, balance , contract_number, channel_origin)
VALUES
-- Juan Pérez - Cuenta de Ahorros
(1, 1, '001-12345678', '2025-12-10', NULL, 'ACTIVO', 3500.75, 'CTR-20220510-01', 'WEB'),

-- Juan Pérez - Tarjeta de Crédito
(1, 4, '4111-1234-5678-9010', '2025-12-15', NULL, 'ACTIVO', -1200.00,'CTR-20230315-02', 'WEB'),

-- María López - Depósito a Plazo Fijo
(2, 5, 'DPF-202309-001', '2025-12-01', NULL, 'ACTIVO', 10000.00,'CTR-20230901-03', 'CALL_CENTER'),

-- Carlos Gómez - Crédito Personal
(3, 3, 'CR-2022-8899', '2025-12-20', NULL, 'ACTIVO', -5000.00,'CTR-20220220-04', 'APP_MOVIL'),

-- Carlos Gómez - Cuenta Corriente
(3, 2, '002-99887766', '2025-12-01', NULL, 'ACTIVO', 2500.00,'CTR-20211201-05', 'AGENCIA');

