-- ============================================
-- SCRIPT DE DATOS DE PRUEBA PARA SISTEMA DE MUEBLERÍA
-- Base de datos: muebleria
-- ============================================

USE muebleria;

-- Desactivar verificaciones temporalmente
SET FOREIGN_KEY_CHECKS = 0;

-- ============================================
-- 1. ROLES
-- ============================================
INSERT INTO roles (nombre_rol) VALUES
('ADMIN'),
('CLIENTE'),
('VENDEDOR');

-- ============================================
-- 2. USUARIOS
-- ============================================
-- Nota: password_hash debería ser un hash real, pero para pruebas usamos texto simple
INSERT INTO usuarios (nombre, apellidos, password_hash, telefono, correo, activo, id_rol) VALUES
('Juan', 'Pérez García', '$2a$10$dummyHashForTesting123456', '5551234567', 'juan.perez@mail.com', 1, 1),
('María', 'López Rodríguez', '$2a$10$dummyHashForTesting123457', '5552345678', 'maria.lopez@mail.com', 1, 2),
('Carlos', 'Martínez Sánchez', '$2a$10$dummyHashForTesting123458', '5553456789', 'carlos.martinez@mail.com', 1, 2),
('Ana', 'González Fernández', '$2a$10$dummyHashForTesting123459', '5554567890', 'ana.gonzalez@mail.com', 1, 3),
('Pedro', 'Ramírez Torres', '$2a$10$dummyHashForTesting123460', '5555678901', 'pedro.ramirez@mail.com', 1, 2);

-- ============================================
-- 3. DIRECCIONES
-- ============================================
INSERT INTO direcciones (tipo_direccion, alias, direccion, ciudad, estado, municipio, codigo_postal, es_predeterminada, id_usuario) VALUES
('ENVIO_Y_FACTURACION', 'Casa', 'Av. Reforma 123, Col. Juárez', 'CDMX', 'Ciudad de México', 'Cuauhtémoc', '06600', 1, 2),
('ENVIO', 'Oficina', 'Calle Insurgentes 456, Col. Roma', 'CDMX', 'Ciudad de México', 'Cuauhtémoc', '06700', 0, 2),
('ENVIO_Y_FACTURACION', 'Casa', 'Av. Universidad 789, Col. Del Valle', 'CDMX', 'Ciudad de México', 'Benito Juárez', '03100', 1, 3),
('FACTURACION', 'Departamento', 'Calle Polanco 321, Col. Polanco', 'CDMX', 'Ciudad de México', 'Miguel Hidalgo', '11560', 0, 3),
('ENVIO_Y_FACTURACION', 'Casa', 'Av. Revolución 654, Col. San Ángel', 'CDMX', 'Ciudad de México', 'Álvaro Obregón', '01000', 1, 5);

-- ============================================
-- 4. CATEGORÍAS
-- ============================================
INSERT INTO categorias (nombre_categoria, activo, id_categoria_padre) VALUES
('Sala', 1, NULL),
('Comedor', 1, NULL),
('Recámara', 1, NULL),
('Oficina', 1, NULL),
('Sofás', 1, 1),
('Mesas de Centro', 1, 1),
('Mesas de Comedor', 1, 2),
('Sillas de Comedor', 1, 2),
('Camas', 1, 3),
('Buros', 1, 3),
('Escritorios', 1, 4),
('Sillas de Oficina', 1, 4);

-- ============================================
-- 5. PROVEEDORES
-- ============================================
INSERT INTO proveedores (nombre_empresa, nombre, telefono, correo, direccion, activo) VALUES
('Muebles del Norte SA de CV', 'Roberto Jiménez', '8181234567', 'contacto@mueblesnorte.com', 'Av. Constitución 100, Monterrey, NL', 1),
('Maderas Finas de México', 'Laura Méndez', '3331234567', 'ventas@maderasfinas.com', 'Calle Juárez 200, Guadalajara, JAL', 1),
('Diseño y Confort SA', 'Miguel Ángel Cruz', '5551112233', 'info@disenoconfort.com', 'Av. Insurgentes 300, CDMX', 1),
('Proveedora de Muebles Europeos', 'Diana Flores', '5552223344', 'europa@muebles.com', 'Calle Amsterdam 400, CDMX', 1);

-- ============================================
-- 6. PRODUCTOS
-- ============================================
INSERT INTO productos (producto, sku, descripcion, precio_actual, alto, ancho, profundidad, peso, activo, stock_disponible, id_categoria, id_proveedor) VALUES
('Sofá Moderno 3 Plazas', 'SOF-MOD-001', 'Sofá moderno de 3 plazas en tela gris con patas de madera', 8999.00, 85.00, 200.00, 90.00, 65.00, 1, 15, 5, 1),
('Mesa de Centro Minimalista', 'MES-CEN-001', 'Mesa de centro de madera con acabado nogal', 2499.00, 45.00, 120.00, 60.00, 25.00, 1, 20, 6, 2),
('Comedor 6 Sillas', 'COM-6S-001', 'Juego de comedor con mesa rectangular y 6 sillas tapizadas', 12500.00, 75.00, 180.00, 90.00, 85.00, 1, 8, 7, 3),
('Cama King Size', 'CAM-KIN-001', 'Cama king size con cabecera acolchada en tela beige', 15000.00, 120.00, 200.00, 220.00, 95.00, 1, 10, 9, 1),
('Buró Moderno', 'BUR-MOD-001', 'Buró de 2 cajones con acabado blanco mate', 1899.00, 55.00, 50.00, 40.00, 18.00, 1, 25, 10, 2),
('Escritorio Ejecutivo', 'ESC-EJE-001', 'Escritorio ejecutivo de madera con 3 cajones laterales', 7500.00, 75.00, 150.00, 70.00, 55.00, 1, 12, 11, 3),
('Silla Ergonómica Oficina', 'SIL-ERG-001', 'Silla ergonómica con soporte lumbar y apoyabrazos ajustables', 3200.00, 115.00, 65.00, 60.00, 22.00, 1, 30, 12, 4),
('Librero 5 Repisas', 'LIB-5R-001', 'Librero modular de 5 repisas en color roble', 2800.00, 180.00, 80.00, 30.00, 35.00, 1, 18, 4, 2),
('Sofá Seccional L', 'SOF-SEC-001', 'Sofá seccional en forma de L con chaise lounge', 18999.00, 90.00, 280.00, 160.00, 110.00, 1, 6, 5, 4),
('Mesa Comedor Extensible', 'MES-COM-EXT-001', 'Mesa de comedor extensible de 6 a 8 personas', 9500.00, 75.00, 160.00, 90.00, 68.00, 1, 10, 7, 1);

-- ============================================
-- 7. PEDIDOS
-- ============================================
-- EstadoPedido puede ser: PENDIENTE, PROCESANDO, ENVIADO, ENTREGADO, CANCELADO
INSERT INTO pedidos (estado_pedido, total, costo_envio, numero_guia, id_usuario, id_direccion) VALUES
('ENTREGADO', 11498.00, 499.00, 'GUIA-2024-001', 2, 1),
('ENTREGADO', 15700.00, 700.00, 'GUIA-2024-002', 3, 3),
('ENVIADO', 20199.00, 699.00, 'GUIA-2024-003', 2, 2),
('PROCESANDO', 10700.00, 700.00, 'GUIA-2024-004', 5, 5),
('ENTREGADO', 3200.00, 0.00, 'GUIA-2024-005', 3, 3);

-- ============================================
-- 8. DETALLES DE PEDIDO
-- ============================================
INSERT INTO detalles_pedido (cantidad, precio_unitario, subtotal, id_producto, id_pedidos) VALUES
-- Pedido 1: Sofá + Mesa de Centro
(1, 8999.00, 8999.00, 1, 1),
(1, 2499.00, 2499.00, 2, 1),

-- Pedido 2: Cama King
(1, 15000.00, 15000.00, 4, 2),

-- Pedido 3: Sofá Seccional + Buró
(1, 18999.00, 18999.00, 9, 3),
(1, 1899.00, 1899.00, 5, 3),

-- Pedido 4: Escritorio + Silla Ergonómica
(1, 7500.00, 7500.00, 6, 4),
(1, 3200.00, 3200.00, 7, 4),

-- Pedido 5: Silla Ergonómica
(1, 3200.00, 3200.00, 7, 5);

-- ============================================
-- 9. FACTURAS (Algunas ya generadas)
-- ============================================
-- EstadoFactura puede ser: PENDIENTE, GENERADA, ENVIADA
INSERT INTO facturas (id_pedido, rfc, razon_social, subtotal, iva, total, estado_factura) VALUES
-- Factura generada para pedido 1
(1, 'XAXX010101000', 'María López Rodríguez', 9912.93, 1586.07, 11499.00, 'ENVIADA'),

-- Factura en proceso para pedido 2
(2, 'MEGA880101A23', 'Muebles El Gran Hogar SA de CV', 13534.48, 2165.52, 15700.00, 'GENERADA');

-- Los pedidos 3, 4 y 5 NO tienen factura aún (para poder probar la solicitud de facturas)

-- Reactivar verificaciones
SET FOREIGN_KEY_CHECKS = 1;

-- ============================================
-- CONSULTAS DE VERIFICACIÓN
-- ============================================
-- Descomentar estas líneas para verificar los datos insertados:

-- SELECT * FROM roles;
-- SELECT * FROM usuarios;
-- SELECT * FROM direcciones;
-- SELECT * FROM categorias;
-- SELECT * FROM proveedores;
-- SELECT * FROM productos;
-- SELECT * FROM pedidos;
-- SELECT * FROM detalles_pedido;
-- SELECT * FROM facturas;

-- Verificar relaciones importantes:
-- SELECT p.id_pedido, p.total, u.nombre, u.apellidos, d.direccion
-- FROM pedidos p
-- JOIN usuarios u ON p.id_usuario = u.id_usuario
-- JOIN direcciones d ON p.id_direccion = d.id_direccion;

-- Verificar facturas con información del pedido:
-- SELECT f.id_factura, f.rfc, f.razon_social, f.total, f.estado_factura, p.id_pedido, p.numero_guia
-- FROM facturas f
-- JOIN pedidos p ON f.id_pedido = p.id_pedido;

-- ============================================
-- NOTAS IMPORTANTES
-- ============================================
-- 1. Los pedidos 3, 4 y 5 NO tienen factura, úsalos para probar:
--    POST /api/facturas/solicitar
--
-- 2. Usuario con id_usuario = 2 (María López) tiene:
--    - Pedido 1 con factura ENVIADA
--    - Pedido 3 sin factura (para solicitar)
--
-- 3. Usuario con id_usuario = 3 (Carlos Martínez) tiene:
--    - Pedido 2 con factura GENERADA
--    - Pedido 5 sin factura (para solicitar)
--
-- 4. Usuario con id_usuario = 5 (Pedro Ramírez) tiene:
--    - Pedido 4 sin factura (para solicitar)
--
-- 5. Para Spring Boot con ddl-auto=create-drop:
--    - Las tablas se crean automáticamente al iniciar
--    - Este script debe ejecutarse DESPUÉS de iniciar la app
--    - O cambiar ddl-auto a "update" o "none" en application.properties
