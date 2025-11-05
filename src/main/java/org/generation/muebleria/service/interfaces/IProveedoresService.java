package org.generation.muebleria.service.interfaces;

import org.generation.muebleria.model.Proveedores;
import java.util.List;

public interface IProveedoresService {

    // CREATE
    Proveedores crearProveedor(Proveedores proveedor);

    // READ
    List<Proveedores> obtenerProveedoresActivos();
    List<Proveedores> obtenerTodosProveedores();
    Proveedores obtenerProveedorPorId(Long id);

    // UPDATE
    Proveedores actualizarProveedor(Long id, Proveedores proveedor);

    // DELETE (soft delete)
    Proveedores desactivarProveedor(Long id);
}