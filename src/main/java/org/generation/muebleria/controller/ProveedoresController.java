package org.generation.muebleria.controller;

import lombok.AllArgsConstructor;
import org.generation.muebleria.model.Proveedores;
import org.generation.muebleria.service.ProveedoresService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
@AllArgsConstructor
public class ProveedoresController {

    private final ProveedoresService proveedoresService;

    // POST - Crear proveedor
    @PostMapping
    public Proveedores crearProveedor(@RequestBody Proveedores proveedor) {
        return proveedoresService.crearProveedor(proveedor);
    }

    // GET - Obtener todos los proveedores activos
    @GetMapping("/activos")
    public List<Proveedores> obtenerProveedoresActivos() {
        return proveedoresService.obtenerProveedoresActivos();
    }

    // GET - Obtener todos los proveedores
    @GetMapping
    public List<Proveedores> obtenerTodosProveedores() {
        return proveedoresService.obtenerTodosProveedores();
    }

    // GET - Obtener proveedor por ID
    @GetMapping("/{id}")
    public Proveedores obtenerProveedorPorId(@PathVariable Long id) {
        return proveedoresService.obtenerProveedorPorId(id);
    }

    // PUT - Actualizar proveedor
    @PutMapping("/{id}")
    public Proveedores actualizarProveedor(@PathVariable Long id, @RequestBody Proveedores proveedor) {
        return proveedoresService.actualizarProveedor(id, proveedor);
    }

    // PATCH - Desactivar proveedor
    @PatchMapping("/{id}/desactivar")
    public Proveedores desactivarProveedor(@PathVariable Long id) {
        return proveedoresService.desactivarProveedor(id);
    }
}