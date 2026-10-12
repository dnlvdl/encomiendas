-- Base de datos del microservicio trabajadores-ms (puerto 8082)
-- Esquema real de la tabla trabajador. Si la tabla ya existe, no volver a ejecutar el CREATE.
IF DB_ID('TrabajadorDB') IS NULL CREATE DATABASE TrabajadorDB;
GO

USE TrabajadorDB;
GO

CREATE TABLE trabajador (
    id              BIGINT IDENTITY(1,1) PRIMARY KEY,
    rut             VARCHAR(8)    NOT NULL UNIQUE,
    dv              VARCHAR(1)    NOT NULL,
    primerNombre    NVARCHAR(100) NOT NULL,
    segundoNombre   NVARCHAR(100) NULL,
    primerApellido  NVARCHAR(100) NOT NULL,
    segundoApellido NVARCHAR(100) NOT NULL,
    celular         VARCHAR(9)    NOT NULL,
    correo          NVARCHAR(150) NOT NULL,
    cargoId         BIGINT        NOT NULL,
    activo          BIT           NOT NULL,
    fechaIngreso    DATE          NOT NULL,
    licencia        VARCHAR(20)   NULL
);
GO
