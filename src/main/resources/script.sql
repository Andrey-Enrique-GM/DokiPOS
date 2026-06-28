/**
 * Author:  Andrey
 * Created: 27 jun 2026
 */

-- Crear la base de datos si no existe
CREATE DATABASE IF NOT EXISTS dokipos_db;
USE dokipos_db;

-- Eliminar tablas en orden inverso (por si necesitas resetear el script)
DROP TABLE IF EXISTS detalles_ventas;
DROP TABLE IF EXISTS ventas;
DROP TABLE IF EXISTS productos;
DROP TABLE IF EXISTS usuarios;

-- ====================================================================
-- TABLA: USUARIOS
-- ====================================================================
CREATE TABLE usuarios (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL, -- Soporta SHA-256 perfectamente
    nombre VARCHAR(150) NOT NULL,
    rol VARCHAR(50) NOT NULL -- ADMIN o CAJERO
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ====================================================================
-- TABLA: PRODUCTOS
-- ====================================================================
CREATE TABLE productos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    codigo_barras VARCHAR(50) UNIQUE NOT NULL,
    nombre VARCHAR(150) NOT NULL,
    precio_venta DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ====================================================================
-- TABLA: VENTAS (El Maestro)
-- ====================================================================
CREATE TABLE ventas (
    id INT PRIMARY KEY AUTO_INCREMENT,
    fecha_hora TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total DECIMAL(10,2) NOT NULL,
    id_usuario INT,
    CONSTRAINT fk_ventas_usuarios 
        FOREIGN KEY (id_usuario) REFERENCES usuarios(id)
        ON DELETE SET NULL -- Si se borra un usuario, la venta no se pierde
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ====================================================================
-- TABLA: DETALLES_VENTAS (El Detalle)
-- ====================================================================
CREATE TABLE detalles_ventas (
    id INT PRIMARY KEY AUTO_INCREMENT,
    id_venta INT NOT NULL,
    id_producto INT,
    cantidad INT NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    CONSTRAINT fk_detalles_ventas 
        FOREIGN KEY (id_venta) REFERENCES ventas(id)
        ON DELETE CASCADE, -- Si se borra el ticket maestro, se borran sus renglones automaticamente
    CONSTRAINT fk_detalles_productos 
        FOREIGN KEY (id_producto) REFERENCES productos(id)
        ON DELETE RESTRICT -- Impide borrar un producto si ya tiene ventas registradas
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- ====================================================================
-- INSERCIONES DE PRUEBA (Opcional)
-- ====================================================================
-- NOTA: Las contraseñas aqui estan hasheadas, para iniciar sesion, escribelas en texto plano
-- username: Monika     password: admin123
-- username: Sayori      password: cajero123
INSERT INTO usuarios (username, password, nombre, rol) VALUES 
('Monika', 'admin123', 'Monika President', 'ADMIN'),
('Sayori', 'caja123', 'Sayori Bun', 'CAJERO');

INSERT INTO productos (codigo_barras, nombre, precio_venta, stock) VALUES 
('7501055300073', 'Pizza Familiar Pepperoni', 120.00, 30),
('7501031311680', 'Refresco de Cola 600ml', 18.50, 50),
('7501000111228', 'Papas Fritas Crujientes 100g', 22.00, 15);
