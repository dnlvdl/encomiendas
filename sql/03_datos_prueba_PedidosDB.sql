-- =====================================================================
-- Datos de prueba para PedidosDB (ejecutar DESPUÉS de 01_PedidosDB.sql).
-- Deja: personas con id 1 y 2, sucursal id 1, direcciones id 1 y 2.
-- Ejecutar una sola vez.
-- =====================================================================
USE PedidosDB;
GO

INSERT INTO region (nombre) VALUES (N'Metropolitana');

INSERT INTO comuna (regionId, nombre)
SELECT id, N'Santiago' FROM region WHERE nombre = N'Metropolitana';

-- Dirección de la sucursal (id 1)
INSERT INTO direccion (comunaId, ciudad, calle, numero, complemento)
SELECT c.id, N'Santiago', N'Av. Libertador Bernardo O''Higgins', N'1234', NULL
FROM comuna c WHERE c.nombre = N'Santiago';

INSERT INTO sucursal (nombre, direccionId)
SELECT N'Sucursal Centro', d.id
FROM direccion d WHERE d.calle = N'Av. Libertador Bernardo O''Higgins';

-- Personas: remitente (id 1) y destinatario (id 2)
INSERT INTO persona (rut, dv, primerNombre, segundoNombre, primerApellido, segundoApellido, celular, correo)
VALUES
    ('12345678', '5', N'Ana', NULL, N'Pérez', N'Soto', '912345678', 'ana@test.cl'),
    ('98765432', '1', N'Luis', NULL, N'Gómez', N'Rojas', '987654321', 'luis@test.cl');

-- Dirección de destino (id 2)
INSERT INTO direccion (comunaId, ciudad, calle, numero, complemento)
SELECT c.id, N'Santiago', N'Calle Destino', N'55', N'Depto 3B'
FROM comuna c WHERE c.nombre = N'Santiago';
GO
