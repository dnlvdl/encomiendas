# ENCOMIENDAS

Sistema de gestión de encomiendas desarrollado como microservicios con Spring Boot, conectados a SQL Server.

Repositorio: https://github.com/dnlvdl/encomiendas

## Estructura

| Carpeta | Descripción | Puerto | Base de datos |
|---|---|---|---|
| `pedidos-ms` | Gestión de pedidos: creación, seguimiento, cambio de estado y eliminación | 8080 | `PedidosDB` |
| `logistica-ms` | Vehículos, rutas y asignaciones de pedidos | 8081 | `LogisticaDB` |
| `trabajadores-ms` | Trabajadores: conductores y administrativos | 8082 | `TrabajadorDB` |

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

Crear en SQL Server las bases `PedidosDB`, `LogisticaDB` y `TrabajadorDB` con sus tablas. Los scripts de creación están en la carpeta `sql/`:

- `01_PedidosDB.sql`
- `02_LogisticaDB.sql`
- `03_datos_prueba_PedidosDB.sql` (datos de prueba, opcional)
- `04_TrabajadorDB.sql`

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

### trabajadores-ms

En otra ventana de PowerShell (con sus propias variables de entorno):

```powershell
cd trabajadores-ms
.\mvnw.cmd clean package
.\mvnw.cmd spring-boot:run
```

Cuando aparezca `Started TrabajadoresMsApplication`, el servicio está disponible en `http://localhost:8082`.

### Ejecutar el .jar generado

Después de `clean package`, el archivo queda en `target/`:

```powershell
java -jar target\pedidos-ms-0.0.1-SNAPSHOT.jar
```

## 5. Probar los endpoints

Importar en Postman las colecciones de cada microservicio:

- `pedidos-ms/postman/pedidos-ms.postman_collection.json`
- `logistica-ms/postman/logistica-ms.postman_collection.json`
- `trabajadores-ms/postman/trabajadores-ms.postman_collection.json`

Antes de ejecutar, ajustar las variables (`pedidoId`, `codigoSeguimiento`, `vehiculoId`, `rutaId`, `trabajadorId`) con los valores que devuelven los `POST` de creación.

### Endpoints de pedidos

| Método | Ruta | Descripción | Respuesta exitosa |
|---|---|---|---|
| POST | `/api/v1/pedidos` | Crea un pedido | 201 |
| GET | `/api/v1/pedidos/seguimiento/{codigo}` | Consulta por código de seguimiento | 200 |
| GET | `/api/v1/pedidos?estado={estado}` | Lista pedidos por estado | 200 |
| PATCH | `/api/v1/pedidos/{id}/estado` | Avanza al siguiente estado | 200 |
| DELETE | `/api/v1/pedidos/{id}` | Elimina un pedido (solo en `PENDIENTE_PAGO`) | 204 |

Errores: 400 (datos inválidos), 404 (no existe), 409 (transición de estado no permitida o eliminación no permitida).

### Endpoints de logística

| Método | Ruta | Descripción | Respuesta exitosa |
|---|---|---|---|
| POST | `/api/v1/vehiculos` | Registra un vehículo (patente de 6 caracteres) | 201 |
| GET | `/api/v1/vehiculos` | Lista los vehículos | 200 |
| GET | `/api/v1/vehiculos/{id}` | Consulta un vehículo | 200 |
| PATCH | `/api/v1/vehiculos/{id}/estado` | Cambia el estado (`DISPONIBLE` o `EN_MANTENCION`) | 200 |
| POST | `/api/v1/rutas` | Crea una ruta | 201 |
| GET | `/api/v1/rutas` | Lista las rutas | 200 |
| POST | `/api/v1/asignaciones` | Asigna un pedido a un vehículo y una ruta | 201 |
| GET | `/api/v1/asignaciones` | Lista las asignaciones | 200 |

Reglas de la asignación:
- El vehículo debe estar `DISPONIBLE` y no tener otra asignación activa.
- El peso del pedido no puede superar la capacidad del vehículo.
- Un pedido no puede tener dos asignaciones activas.

Errores: 400 (datos inválidos o peso excedido), 404 (vehículo, ruta o pedido no existe), 409 (vehículo no disponible o pedido ya asignado).

**Nota de diseño:** `logistica-ms` no consulta a `pedidos-ms` para conocer el peso del pedido. Por eso el peso se envía en el cuerpo de la asignación. Una versión futura debería obtenerlo mediante una llamada HTTP entre servicios.

### Endpoints de trabajadores

| Método | Ruta | Descripción | Respuesta exitosa |
|---|---|---|---|
| POST | `/api/v1/trabajadores` | Crea un trabajador | 201 |
| GET | `/api/v1/trabajadores` | Lista los trabajadores | 200 |
| GET | `/api/v1/trabajadores/{id}` | Consulta un trabajador | 200 |
| PATCH | `/api/v1/trabajadores/{id}/activo` | Invierte el estado activo (sin body) | 200 |
| DELETE | `/api/v1/trabajadores/{id}` | Borra el trabajador de la tabla | 204 |

Reglas:
- `cargoId = 1` corresponde a conductor, que debe tener número de licencia. `cargoId = 2` corresponde a administrativo, que no la necesita. La licencia de un administrativo se guarda vacía aunque se envíe.
- El rut no puede repetirse.

Errores: 400 (datos inválidos o conductor sin licencia), 404 (no existe), 409 (rut repetido).

## Estado del proyecto

- `pedidos-ms`: completo. Entidades, repositorios, servicio, controlador, validaciones, manejo de errores y colección de Postman.
- `logistica-ms`: completo en vehículos, rutas y asignaciones, con colección de Postman.
- `trabajadores-ms`: completo en trabajadores, con colección de Postman.
- Pendiente: microservicio de autenticación (`AutenticacionDB`).
