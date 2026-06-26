# DokiPOS 🌸

DokiPOS es un sistema de punto de venta (POS) nativo para escritorio desarrollado en Java Swing. El proyecto está diseñado bajo la arquitectura **Modelo-Vista-Controlador (MVC)**, garantizando un control sólido sobre las transacciones de venta, el inventario y la seguridad de los usuarios.

## ¿Cómo está hecho?
Esta aplicación de escritorio está construida utilizando las siguientes tecnologías:
* **Java SE (Swing y AWT):** Para el desarrollo de la interfaz gráfica de usuario (GUI), utilizando una combinación de `View` para pantallas informativas y `Form` para la captura activa de transacciones.
* **MySQL:** Como sistema de gestión de base de datos relacional para garantizar la persistencia de toda la información comercial y operativa.
* **JDBC (Java Database Connectivity):** Para gestionar el puente de datos mediante el patrón de diseño **Singleton**, asegurando una única conexión eficiente hacia el servidor.
* **SHA-256 (Cryptography):** Implementación nativa de seguridad utilizando `MessageDigest` para el cifrado y hasheo de contraseñas, evitando el almacenamiento de credenciales en texto plano.

## La Base de Datos
La base de datos se llama `dokipos_db` y utiliza una estructura relacional de tipo **Maestro-Detalle** distribuida en 4 tablas principales:

### 1. Tabla: `usuarios`
Almacena las credenciales y los niveles de acceso del personal de la tienda.
* `id` **(INT, PRIMARY KEY, AUTO_INCREMENT):** Identificador único del usuario.
* `username` **(VARCHAR(100), UNIQUE, NOT NULL):** Nombre de usuario único para el inicio de sesión.
* `password` **(VARCHAR(255), NOT NULL):** Contraseña protegida mediante un hash seguro de 64 caracteres (SHA-256).
* `nombre` **(VARCHAR(150), NOT NULL):** Nombre completo de la persona.
* `rol` **(VARCHAR(50), NOT NULL):** Nivel de privilegios dentro del sistema (`ADMIN` o `CAJERO`).

### 2. Tabla: `productos`
Actúa como el catálogo e inventario general de todos los artículos disponibles para la venta.
* `id` **(INT, PRIMARY KEY, AUTO_INCREMENT):** Identificador interno del producto.
* `codigo_barras` **(VARCHAR(50), UNIQUE, NOT NULL):** Código de barras o clave única comercial del artículo.
* `nombre` **(VARCHAR(150), NOT NULL):** Descripción o nombre del producto.
* `precio_venta` **(DECIMAL(10,2), NOT NULL):** Precio final para el consumidor.
* `stock` **(INT, NOT NULL, DEFAULT 0):** Cantidad de existencias físicas disponibles en el almacén.

### 3. Tabla: `ventas` (El Maestro)
Registra de forma global el encabezado de cada ticket de compra finalizado.
* `id` **(INT, PRIMARY KEY, AUTO_INCREMENT):** Número de ticket o folio de la transacción.
* `fecha_hora` **(TIMESTAMP, DEFAULT CURRENT_TIMESTAMP):** Fecha y hora exacta en la que se procesó el cobro.
* `total` **(DECIMAL(10,2), NOT NULL):** Monto económico total de la venta.
* `id_usuario` **(INT, FOREIGN KEY):** Relación con el `id` de la tabla `usuarios` para auditar qué cajero operó la transacción.

### 4. Tabla: `detalles_ventas` (El Detalle)
Desglosa renglón por renglón los artículos adquiridos dentro de un mismo ticket, controlando la relación muchos a muchos entre ventas y productos.
* `id` **(INT, PRIMARY KEY, AUTO_INCREMENT):** Identificador único del renglón de detalle.
* `id_venta` **(INT, FOREIGN KEY, ON DELETE CASCADE):** Vinculación al ticket maestro en la tabla `ventas`.
* `id_producto` **(INT, FOREIGN KEY):** Vinculación al artículo comprado en la tabla `productos`.
* `cantidad` **(INT, NOT NULL):** Número de piezas vendidas de este artículo.
* `subtotal` **(DECIMAL(10,2), NOT NULL):** El resultado financiero de multiplicar la cantidad por el precio de venta del artículo.
* 
