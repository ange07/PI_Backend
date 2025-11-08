# GUÍA COMPLETA DE POSTMAN - SISTEMA DE FACTURACIÓN

## CONFIGURACIÓN INICIAL

### URL Base
```
http://localhost:8080
```

### Endpoints de Facturación
```
Base: http://localhost:8080/api/facturas
```

---

## PASOS PARA INICIAR

### 1. Iniciar la Aplicación Spring Boot

```bash
# Opción 1: Con Maven
./mvnw spring-boot:run

# Opción 2: Con Gradle
./gradlew bootRun

# Opción 3: Desde IntelliJ/Eclipse
# Run -> Run 'MuebleriaApplication'
```

### 2. Cargar Datos de Prueba

**IMPORTANTE**: Debido a que `spring.jpa.hibernate.ddl-auto=create-drop`, las tablas se crean vacías al iniciar. Tienes 2 opciones:

**Opción A - Mantener create-drop (recomendado para desarrollo):**
1. Inicia la aplicación (esto crea las tablas vacías)
2. Abre MySQL Workbench o consola MySQL
3. Ejecuta el script: `datos_prueba.sql`

```bash
mysql -u root -p
# Ingresa password: root123
source /home/user/Prueba_Backend/datos_prueba.sql
```

**Opción B - Cambiar a update (los datos persisten):**
1. Edita `src/main/resources/application.properties`
2. Cambia: `spring.jpa.hibernate.ddl-auto=update`
3. Reinicia la aplicación
4. Ejecuta el script SQL solo una vez

---

## ENDPOINTS DISPONIBLES

### 📋 ENDPOINTS PARA CLIENTES

#### 1. SOLICITAR FACTURA PARA UN PEDIDO
```
POST http://localhost:8080/api/facturas/solicitar
```

**Headers:**
```
Content-Type: application/json
```

**Body (JSON):**
```json
{
  "idPedido": 3,
  "rfc": "PEDR850615HDF",
  "razonSocial": "Pedro Ramírez Torres"
}
```

**Respuesta Exitosa (201 Created):**
```json
{
  "idFactura": 3,
  "idPedido": 3,
  "rfc": "PEDR850615HDF",
  "razonSocial": "Pedro Ramírez Torres",
  "subtotal": 17412.07,
  "iva": 2786.93,
  "total": 20199.00,
  "estadoFactura": "PENDIENTE",
  "fechaEmision": "2024-11-08T10:30:00",
  "fechaActualizacion": "2024-11-08T10:30:00"
}
```

**Casos de Error:**
```json
// Si el pedido no existe
{
  "error": "Pedido no encontrado"
}

// Si ya existe factura para ese pedido
{
  "error": "Ya existe una factura para este pedido"
}

// Si RFC está vacío
{
  "error": "RFC y Razón Social son obligatorios"
}
```

**Pedidos disponibles para solicitar factura:**
- Pedido 3 (Usuario: María López, id=2)
- Pedido 4 (Usuario: Pedro Ramírez, id=5)
- Pedido 5 (Usuario: Carlos Martínez, id=3)

---

#### 2. OBTENER FACTURAS DE UN USUARIO
```
GET http://localhost:8080/api/facturas/usuario/2
```

**Respuesta Exitosa (200 OK):**
```json
[
  {
    "idFactura": 1,
    "idPedido": 1,
    "rfc": "XAXX010101000",
    "razonSocial": "María López Rodríguez",
    "subtotal": 9912.93,
    "iva": 1586.07,
    "total": 11499.00,
    "estadoFactura": "ENVIADA",
    "fechaEmision": "2024-11-01T09:00:00",
    "fechaActualizacion": "2024-11-03T14:30:00"
  }
]
```

**Prueba con estos usuarios:**
- Usuario 2 (María López) - Tiene 1 factura
- Usuario 3 (Carlos Martínez) - Tiene 1 factura
- Usuario 5 (Pedro Ramírez) - No tiene facturas aún

---

#### 3. OBTENER FACTURA DE UN PEDIDO ESPECÍFICO
```
GET http://localhost:8080/api/facturas/pedido/1
```

**Respuesta Exitosa (200 OK):**
```json
{
  "idFactura": 1,
  "idPedido": 1,
  "rfc": "XAXX010101000",
  "razonSocial": "María López Rodríguez",
  "subtotal": 9912.93,
  "iva": 1586.07,
  "total": 11499.00,
  "estadoFactura": "ENVIADA",
  "fechaEmision": "2024-11-01T09:00:00",
  "fechaActualizacion": "2024-11-03T14:30:00"
}
```

**Pedidos con factura:**
- Pedido 1 - Factura ENVIADA
- Pedido 2 - Factura GENERADA

**Pedidos sin factura:**
- Pedido 3, 4, 5 - Sin factura (devuelve error 404)

---

### 🔧 ENDPOINTS PARA ADMINISTRADORES

#### 4. VER TODAS LAS FACTURAS (ADMIN)
```
GET http://localhost:8080/api/facturas/admin/todas
```

**Respuesta Exitosa (200 OK):**
```json
[
  {
    "idFactura": 1,
    "idPedido": 1,
    "rfc": "XAXX010101000",
    "razonSocial": "María López Rodríguez",
    "subtotal": 9912.93,
    "iva": 1586.07,
    "total": 11499.00,
    "estadoFactura": "ENVIADA",
    "fechaEmision": "2024-11-01T09:00:00",
    "fechaActualizacion": "2024-11-03T14:30:00"
  },
  {
    "idFactura": 2,
    "idPedido": 2,
    "rfc": "MEGA880101A23",
    "razonSocial": "Muebles El Gran Hogar SA de CV",
    "subtotal": 13534.48,
    "iva": 2165.52,
    "total": 15700.00,
    "estadoFactura": "GENERADA",
    "fechaEmision": "2024-11-02T11:00:00",
    "fechaActualizacion": "2024-11-02T16:00:00"
  }
]
```

---

#### 5. VER FACTURAS PENDIENTES (ADMIN)
```
GET http://localhost:8080/api/facturas/admin/pendientes
```

Devuelve solo facturas con `estado_factura = 'PENDIENTE'`

---

#### 6. VER FACTURAS GENERADAS (ADMIN)
```
GET http://localhost:8080/api/facturas/admin/generadas
```

Devuelve solo facturas con `estado_factura = 'GENERADA'`

**Ejemplo de respuesta:**
```json
[
  {
    "idFactura": 2,
    "idPedido": 2,
    "rfc": "MEGA880101A23",
    "razonSocial": "Muebles El Gran Hogar SA de CV",
    "subtotal": 13534.48,
    "iva": 2165.52,
    "total": 15700.00,
    "estadoFactura": "GENERADA",
    "fechaEmision": "2024-11-02T11:00:00",
    "fechaActualizacion": "2024-11-02T16:00:00"
  }
]
```

---

#### 7. VER FACTURAS ENVIADAS (ADMIN)
```
GET http://localhost:8080/api/facturas/admin/enviadas
```

Devuelve solo facturas con `estado_factura = 'ENVIADA'`

---

#### 8. MARCAR FACTURA COMO GENERADA (ADMIN)
```
PUT http://localhost:8080/api/facturas/admin/1/marcar-generada
```

**Sin body requerido**

**Respuesta Exitosa (200 OK):**
```json
{
  "idFactura": 1,
  "estadoFactura": "GENERADA",
  "fechaActualizacion": "2024-11-08T15:45:00"
}
```

---

#### 9. MARCAR FACTURA COMO ENVIADA (ADMIN)
```
PUT http://localhost:8080/api/facturas/admin/1/marcar-enviada
```

**Sin body requerido**

**Respuesta Exitosa (200 OK):**
```json
{
  "idFactura": 1,
  "estadoFactura": "ENVIADA",
  "fechaActualizacion": "2024-11-08T16:00:00"
}
```

---

#### 10. CAMBIAR ESTADO MANUALMENTE (ADMIN)
```
PUT http://localhost:8080/api/facturas/admin/1/cambiar-estado?estado=PENDIENTE
```

**Query Parameter:**
- `estado`: PENDIENTE | GENERADA | ENVIADA

**Respuesta Exitosa (200 OK):**
```json
{
  "idFactura": 1,
  "estadoFactura": "PENDIENTE",
  "fechaActualizacion": "2024-11-08T16:15:00"
}
```

---

## FLUJO DE PRUEBA COMPLETO

### ESCENARIO 1: Cliente Solicita Factura

1. **Verificar pedidos sin factura**
```
GET http://localhost:8080/api/facturas/pedido/3
# Debe devolver 404 (sin factura)
```

2. **Solicitar factura**
```
POST http://localhost:8080/api/facturas/solicitar
Body:
{
  "idPedido": 3,
  "rfc": "LOPR920315MDF",
  "razonSocial": "María López Rodríguez"
}
```

3. **Verificar que se creó**
```
GET http://localhost:8080/api/facturas/pedido/3
# Ahora debe devolver la factura
```

4. **Ver todas las facturas del usuario**
```
GET http://localhost:8080/api/facturas/usuario/2
# Debe mostrar 2 facturas
```

---

### ESCENARIO 2: Admin Procesa Facturas

1. **Ver facturas pendientes**
```
GET http://localhost:8080/api/facturas/admin/pendientes
```

2. **Marcar como generada**
```
PUT http://localhost:8080/api/facturas/admin/3/marcar-generada
```

3. **Ver facturas generadas**
```
GET http://localhost:8080/api/facturas/admin/generadas
```

4. **Marcar como enviada**
```
PUT http://localhost:8080/api/facturas/admin/3/marcar-enviada
```

5. **Ver facturas enviadas**
```
GET http://localhost:8080/api/facturas/admin/enviadas
```

---

## DATOS DE PRUEBA DISPONIBLES

### Usuarios
| ID | Nombre | Correo | Rol |
|----|--------|--------|-----|
| 1 | Juan Pérez | juan.perez@mail.com | ADMIN |
| 2 | María López | maria.lopez@mail.com | CLIENTE |
| 3 | Carlos Martínez | carlos.martinez@mail.com | CLIENTE |
| 5 | Pedro Ramírez | pedro.ramirez@mail.com | CLIENTE |

### Pedidos
| ID | Usuario | Total | Estado | ¿Tiene Factura? |
|----|---------|-------|--------|-----------------|
| 1 | María López | $11,499 | ENTREGADO | ✅ SÍ (ENVIADA) |
| 2 | Carlos Martínez | $15,700 | ENTREGADO | ✅ SÍ (GENERADA) |
| 3 | María López | $20,199 | ENVIADO | ❌ NO |
| 4 | Pedro Ramírez | $10,700 | PROCESANDO | ❌ NO |
| 5 | Carlos Martínez | $3,200 | ENTREGADO | ❌ NO |

### Facturas Existentes
| ID | Pedido | RFC | Total | Estado |
|----|--------|-----|-------|--------|
| 1 | 1 | XAXX010101000 | $11,499 | ENVIADA |
| 2 | 2 | MEGA880101A23 | $15,700 | GENERADA |

---

## CÁLCULO DEL IVA

El sistema calcula automáticamente:
```
Total del pedido = $20,199.00

subtotal = total / 1.16 = $17,412.07
iva = subtotal * 0.16 = $2,786.93
total = subtotal + iva = $20,199.00 ✓
```

---

## ERRORES COMUNES

### Error: "Connection refused"
- ✅ Verifica que la aplicación esté corriendo en http://localhost:8080
- ✅ Revisa los logs de Spring Boot

### Error: "Pedido no encontrado"
- ✅ Usa un ID de pedido que exista en la base de datos (1-5)
- ✅ Verifica que los datos de prueba se cargaron correctamente

### Error: "Ya existe una factura para este pedido"
- ✅ Ese pedido ya tiene factura asignada
- ✅ Usa otro pedido (3, 4 o 5)

### La base de datos está vacía
- ✅ Ejecuta el script `datos_prueba.sql` en MySQL
- ✅ Verifica la conexión: `mysql -u root -p` (password: root123)

---

## IMPORTAR COLECCIÓN A POSTMAN

Crea una nueva colección con estos requests ya configurados:

1. Abre Postman
2. Click en "Import"
3. Pega estos endpoints como cURL:

```bash
# Solicitar Factura
curl --location 'http://localhost:8080/api/facturas/solicitar' \
--header 'Content-Type: application/json' \
--data '{
  "idPedido": 3,
  "rfc": "LOPR920315MDF",
  "razonSocial": "María López Rodríguez"
}'

# Ver facturas de usuario
curl --location 'http://localhost:8080/api/facturas/usuario/2'

# Ver factura de pedido
curl --location 'http://localhost:8080/api/facturas/pedido/1'

# Admin: Ver todas
curl --location 'http://localhost:8080/api/facturas/admin/todas'

# Admin: Marcar como generada
curl --location --request PUT 'http://localhost:8080/api/facturas/admin/3/marcar-generada'

# Admin: Marcar como enviada
curl --location --request PUT 'http://localhost:8080/api/facturas/admin/3/marcar-enviada'
```

---

## VERIFICACIÓN EN LA BASE DE DATOS

Puedes verificar directamente en MySQL:

```sql
-- Ver todas las facturas
SELECT * FROM facturas;

-- Ver facturas con información del pedido
SELECT
    f.id_factura,
    f.rfc,
    f.razon_social,
    f.total,
    f.estado_factura,
    p.numero_guia,
    u.nombre,
    u.apellidos
FROM facturas f
JOIN pedidos p ON f.id_pedido = p.id_pedido
JOIN usuarios u ON p.id_usuario = u.id_usuario;

-- Ver pedidos sin factura
SELECT p.id_pedido, p.total, p.estado_pedido, u.nombre
FROM pedidos p
LEFT JOIN facturas f ON p.id_pedido = f.id_pedido
JOIN usuarios u ON p.id_usuario = u.id_usuario
WHERE f.id_factura IS NULL;
```

---

## CONTACTO Y SOPORTE

Si encuentras algún error o tienes dudas:
1. Revisa los logs de Spring Boot en la consola
2. Verifica que MySQL esté corriendo
3. Confirma que los datos de prueba se cargaron correctamente

**¡Listo para probar el sistema de facturación!** 🎉
