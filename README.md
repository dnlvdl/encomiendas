# ENCOMIENDAS

Sistema de gestión de encomiendas desarrollado como microservicios con Spring Boot, conectados a SQL Server.

Repositorio: https://github.com/dnlvdl/encomiendas

## Estructura

| Carpeta | Descripción | Puerto | Base de datos |
|---|---|---|---|
| `pedidos-ms` | Gestión de pedidos: creación, seguimiento y cambio de estado | 8080 | `PedidosDB` |
| `logistica-ms` | Vehículos, rutas y asignaciones de pedidos | 8081 | `LogisticaDB` |

## Requisitos

- JDK 25 (Temurin 25 o similar)
- SQL Server (instancia local `VIDAL`)
- Git
- Postman (opcional, para probar los endpoints)

No es necesario instalar Maven: cada microservicio incluye el wrapper (`mvnw.cmd`).

## 1. Clonar el repositorio

```powershell
git clone https://github.com/dnlvdl/encomiendas.git
cd encomiendas
```

## 2. Crear las bases de datos

Crear en SQL Server las bases `PedidosDB` y `LogisticaDB` con sus tablas. Los scripts de creación están en la carpeta `sql/`.

## 3. Configurar credenciales

Las credenciales no están en el código. Cada microservicio las lee desde variables de entorno:

```powershell
$env:DB_USER = "<usuario de SQL Server>"
$env:DB_PASSWORD = "<clave de SQL Server>"
```

Estas variables deben definirse en la misma ventana de PowerShell donde se ejecuta la aplicación.

## 4. Compilar y ejecutar

Cada microservicio se compila y ejecuta por separado.

### pedidos-ms

```powershell
cd pedidos-ms
.\mvnw.cmd clean package
.\mvnw.cmd spring-boot:run
```

Cuando aparezca `Started PedidosMsApplication`, el servicio está disponible en `http://localhost:8080`.

### logistica-ms

En otra ventana de PowerShell (con sus propias variables de entorno):

```powershell
cd logistica-ms
.\mvnw.cmd clean package
.\mvnw.cmd spring-boot:run
```

Cuando aparezca `Started LogisticaMsApplication`, el servicio está disponible en `http://localhost:8081`.

### Ejecutar el .jar generado

Después de `clean package`, el archivo queda en `target/`:

```powershell
java -jar target\pedidos-ms-0.0.1-SNAPSHOT.jar
```

## 5. Probar los endpoints

Importar en Postman el archivo `pedidos-ms/postman/pedidos-ms.postman_collection.json`. Antes de ejecutar, ajustar las variables `pedidoId` y `codigoSeguimiento` con los valores que devuelve el `POST` de creación.

### Endpoints de pedidos

| Método | Ruta | Descripción | Respuesta exitosa |
|---|---|---|---|
| POST | `/api/v1/pedidos` | Crea un pedido | 201 |
| GET | `/api/v1/pedidos/seguimiento/{codigo}` | Consulta por código de seguimiento | 200 |
| GET | `/api/v1/pedidos?estado={estado}` | Lista pedidos por estado | 200 |
| PATCH | `/api/v1/pedidos/{id}/estado` | Avanza al siguiente estado | 200 |

Errores: 400 (datos inválidos), 404 (no existe), 409 (transición de estado no permitida).

## Estado del proyecto

- `pedidos-ms`: entidades, repositorios, servicio, controlador, validaciones y manejo de errores. Probado con la colección de Postman.
- `logistica-ms`: entidades (`Vehiculo`, `Ruta`, `Asignacion`) y repositorios conectados a `LogisticaDB`. Pendiente: endpoints.
- Pendiente: microservicios de trabajadores y autenticación.
