-- =====================================================================
-- LogisticaDB: estructura del microservicio logistica-ms
-- Ejecutar en SQL Server (SSMS). Crea la base y todas sus tablas.
-- Nota: pedidoId, conductorId y comunaId son IDs de otras bases,
-- por eso no tienen clave foránea.
-- =====================================================================
IF DB_ID(N'LogisticaDB') IS NULL
    CREATE DATABASE LogisticaDB;
GO

USE LogisticaDB;
GO

CREATE TABLE vehiculo (
    id          BIGINT IDENTITY(1,1) PRIMARY KEY,
    patente     VARCHAR(6) NOT NULL UNIQUE,
    capacidadKg DECIMAL(7,2) NOT NULL,
    estado      VARCHAR(20) NOT NULL,
    CONSTRAINT ck_vehiculo_patente CHECK (patente NOT LIKE '%[^A-Z0-9]%'),
    CONSTRAINT ck_vehiculo_capacidad CHECK (capacidadKg > 0),
    CONSTRAINT ck_vehiculo_estado CHECK (estado IN ('DISPONIBLE', 'EN_MANTENCION'))
);

CREATE TABLE ruta (
    id       BIGINT IDENTITY(1,1) PRIMARY KEY,
    nombre   NVARCHAR(100) NOT NULL UNIQUE,
    comunaId BIGINT NOT NULL
);
CREATE INDEX ix_ruta_comuna ON ruta(comunaId);

CREATE TABLE asignacion (
    id               BIGINT IDENTITY(1,1) PRIMARY KEY,
    pedidoId         BIGINT NOT NULL,
    vehiculoId       BIGINT NOT NULL,
    rutaId           BIGINT NOT NULL,
    conductorId      BIGINT NOT NULL,
    fechaAsignacion  DATETIME2 NOT NULL DEFAULT SYSDATETIME(),
    estado           VARCHAR(20) NOT NULL,
    CONSTRAINT fk_asignacion_vehiculo FOREIGN KEY (vehiculoId) REFERENCES vehiculo(id),
    CONSTRAINT fk_asignacion_ruta FOREIGN KEY (rutaId) REFERENCES ruta(id),
    CONSTRAINT ck_asignacion_estado CHECK (estado IN ('ACTIVA', 'FINALIZADA', 'CANCELADA'))
);

-- Un pedido solo puede tener una asignación activa a la vez.
CREATE UNIQUE INDEX ux_asignacion_pedido_activa
    ON asignacion(pedidoId)
    WHERE estado = 'ACTIVA';
CREATE INDEX ix_asignacion_vehiculo ON asignacion(vehiculoId);
CREATE INDEX ix_asignacion_ruta ON asignacion(rutaId);
GO
