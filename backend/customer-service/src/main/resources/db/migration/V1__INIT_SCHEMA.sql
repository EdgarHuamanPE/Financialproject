--tabla cliente
CREATE TABLE customers (
    id BIGSERIAL PRIMARY KEY,
    document_type VARCHAR(10) NOT NULL,
    document_number VARCHAR(20) UNIQUE NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(20),
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVO',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_customer_status CHECK (status IN ('ACTIVO', 'INACTIVO', 'SUSPENDIDO', 'CERRADO')),
    CONSTRAINT chk_customer_document_type CHECK (document_type IN ('DNI', 'RUC', 'CE', 'PAS'))
);

CREATE INDEX idx_customer_document_type ON customers(document_type);
CREATE INDEX idx_customer_status ON customers(status);
CREATE INDEX idx_customer_name ON customers(first_name, last_name);
CREATE INDEX idx_customer_email ON customers(email);



INSERT INTO customers (document_type, document_number, first_name, last_name, email, phone, status)
VALUES
-- Clientes naturales (DNI)
('DNI', '45896321', 'María', 'Gonzales', 'maria.gonzales@gmail.com', '987654321', 'ACTIVO'),
('DNI', '72648953', 'Juan', 'Huamán', 'juan.huaman@gmail.com', '998877665', 'ACTIVO'),
('DNI', '60421789', 'Carla', 'Ramírez', 'carla.ramirez@yahoo.com', '912345678', 'SUSPENDIDO'),

-- Cliente con carnet de extranjería (CE)
('CE', 'E1234567', 'John', 'Smith', 'john.smith@hotmail.com', '955667788', 'ACTIVO'),

-- Cliente con pasaporte (PAS)
('PAS', 'P9876543', 'Sofía', 'Martínez', 'sofia.martinez@gmail.com', '911223344', 'INACTIVO'),

-- Clientes con RUC (empresas)
('RUC', '20456789123', 'Inversiones Andinas', 'S.A.C.', 'contacto@andinas.com.pe', '014567890', 'ACTIVO'),
('RUC', '20678912345', 'Servicios del Sur', 'E.I.R.L.', 'ventas@servsur.com.pe', '016789123', 'CERRADO');