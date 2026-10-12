-- =====================================================================
-- PedidosDB: estructura del microservicio pedidos-ms
-- Ejecutar en SQL Server (SSMS). Crea la base y todas sus tablas.
-- =====================================================================
IF DB_ID(N'PedidosDB') IS NULL
    CREATE DATABASE PedidosDB;
GO

USE PedidosDB;
GO

-- ---------- Geografía ----------
CREATE TABLE region (
    id      BIGINT IDENTITY(1,1) PRIMARY KEY,
    nombre  NVARCHAR(60) NOT NULL UNIQUE
);

CREATE TABLE comuna (
    id        BIGINT IDENTITY(1,1) PRIMARY KEY,
    regionId  BIGINT NOT NULL,
    nombre    NVARCHAR(60) NOT NULL,
    CONSTRAINT fk_comuna_region FOREIGN KEY (regionId) REFERENCES region(id),
    CONSTRAINT uq_comuna_region_nombre UNIQUE (regionId, nombre)
);
CREATE INDEX ix_comuna_region ON comuna(regionId);

CREATE TABLE direccion (
    id           BIGINT IDENTITY(1,1) PRIMARY KEY,
    comunaId     BIGINT NOT NULL,
    ciudad       NVARCHAR(60) NOT NULL,
    calle        NVARCHAR(100) NOT NULL,
    numero       VARCHAR(10) NOT NULL,
    complemento  NVARCHAR(100) NULL,
    CONSTRAINT fk_direccion_comuna FOREIGN KEY (comunaId) REFERENCES comuna(id)
);
CREATE INDEX ix_direccion_comuna ON direccion(comunaId);

-- ---------- Sucursales ----------
CREATE TABLE sucursal (
    id           BIGINT IDENTITY(1,1) PRIMARY KEY,
    nombre       NVARCHAR(100) NOT NULL UNIQUE,
    direccionId  BIGINT NOT NULL UNIQUE,
    CONSTRAINT fk_sucursal_direccion FOREIGN KEY (direccionId) REFERENCES direccion(id)
);

-- ---------- Personas (remitentes y destinatarios) ----------
CREATE TABLE persona (
    id               BIGINT IDENTITY(1,1) PRIMARY KEY,
    rut              VARCHAR(8) NOT NULL UNIQUE,
    dv               VARCHAR(1) NOT NULL,
    primerNombre     NVARCHAR(100) NOT NULL,
    segundoNombre    NVARCHAR(100) NULL,
    primerApellido   NVARCHAR(100) NOT NULL,
    segundoApellido  NVARCHAR(100) NOT NULL,
    celular          VARCHAR(9) NOT NULL,
    correo           NVARCHAR(150) NOT NULL,
    CONSTRAINT ck_persona_rut CHECK (rut NOT LIKE '%[^0-9]%'),
    CONSTRAINT ck_persona_dv CHECK (dv LIKE '[0-9K]'),
    CONSTRAINT ck_persona_celular CHECK (celular LIKE '9[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]')
);

-- ---------- Pedidos ----------
CREATE TABLE pedido (
    id                   BIGINT IDENTITY(1,1) PRIMARY KEY,
    codigoSeguimiento    VARCHAR(20) NOT NULL UNIQUE,
    remitenteId          BIGINT NOT NULL,
    destinatarioId       BIGINT NOT NULL,
    sucursalId           BIGINT NOT NULL,
    destinoDireccionId   BIGINT NOT NULL,
    tamano               VARCHAR(10) NOT NULL,
    pesoKg               DECIMAL(5,2) NOT NULL,
    esFragil             BIT NOT NULL,
    esRigido             BIT NOT NULL,
    esDelicado           BIT NOT NULL,
    declaracionAceptada  BIT NOT NULL,
    estado               VARCHAR(20) NOT NULL DEFAULT 'PENDIENTE_PAGO',
    calificacion         TINYINT NULL,
    creadoEn             DATETIME2 NOT NULL DEFAULT SYSDATETIME(),
    CONSTRAINT fk_pedido_remitente FOREIGN KEY (remitenteId) REFERENCES persona(id),
    CONSTRAINT fk_pedido_destinatario FOREIGN KEY (destinatarioId) REFERENCES persona(id),
    CONSTRAINT fk_pedido_sucursal FOREIGN KEY (sucursalId) REFERENCES sucursal(id),
    CONSTRAINT fk_pedido_destino FOREIGN KEY (destinoDireccionId) REFERENCES direccion(id),
    CONSTRAINT ck_pedido_tamano CHECK (tamano IN ('PEQUENO', 'MEDIANO', 'GRANDE')),
    CONSTRAINT ck_pedido_peso CHECK (pesoKg > 0 AND pesoKg <= 50),
    CONSTRAINT ck_pedido_declaracion CHECK (declaracionAceptada = 1),
    CONSTRAINT ck_pedido_estado CHECK (estado IN ('PENDIENTE_PAGO', 'PAGADO', 'ASIGNADO', 'EN_TRANSITO', 'ENTREGADO')),
    CONSTRAINT ck_pedido_calificacion CHECK (
        calificacion IS NULL OR (calificacion BETWEEN 1 AND 5 AND estado = 'ENTREGADO')
    )
);
CREATE INDEX ix_pedido_remitente ON pedido(remitenteId);
CREATE INDEX ix_pedido_destinatario ON pedido(destinatarioId);
CREATE INDEX ix_pedido_estado ON pedido(estado);
CREATE INDEX ix_pedido_sucursal ON pedido(sucursalId);
CREATE INDEX ix_pedido_destino ON pedido(destinoDireccionId);

-- ---------- Historial de estados ----------
CREATE TABLE historial_estado (
    id           BIGINT IDENTITY(1,1) PRIMARY KEY,
    pedidoId     BIGINT NOT NULL,
    estado       VARCHAR(20) NOT NULL,
    fecha        DATETIME2 NOT NULL,
    observacion  NVARCHAR(200) NULL,
    CONSTRAINT fk_historial_pedido FOREIGN KEY (pedidoId) REFERENCES pedido(id) ON DELETE CASCADE
);
CREATE INDEX ix_historial_pedido ON historial_estado(pedidoId);
GO
