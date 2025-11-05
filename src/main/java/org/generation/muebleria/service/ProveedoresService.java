package org.generation.muebleria.service;

import lombok.AllArgsConstructor;
import org.generation.muebleria.model.Proveedores;
import org.generation.muebleria.repository.ProveedoresRepository;
import org.generation.muebleria.service.interfaces.IProveedoresService;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ProveedoresService implements IProveedoresService {

    private final ProveedoresRepository proveedoresRepository;

    @Override
    public Proveedores crearProveedor(Proveedores proveedor) {
        proveedor.setFechaRegistro(LocalDateTime.now());
        proveedor.setFechaActualizacion(LocalDateTime.now());

        // Si no se especifica, activar por defecto
        if (proveedor.getAcivo() == null) {
            proveedor.setAcivo(true);
        }

        return proveedoresRepository.save(proveedor);
    }

    @Override
    public List<Proveedores> obtenerProveedoresActivos() {
        return proveedoresRepository.findByAcivoTrue();
    }

    @Override
    public List<Proveedores> obtenerTodosProveedores() {
        return proveedoresRepository.findAll();
    }

    @Override
    public Proveedores obtenerProveedorPorId(Long id) {
        return proveedoresRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe el proveedor con id: " + id));
    }

    @Override
    public Proveedores actualizarProveedor(Long id, Proveedores proveedorActualizado) {
        Proveedores proveedorExistente = proveedoresRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe el proveedor con id: " + id));

        // Actualizar solo los campos que vienen en el request
        if (proveedorActualizado.getNombreEmpresa() != null) {
            proveedorExistente.setNombreEmpresa(proveedorActualizado.getNombreEmpresa());
        }
        if (proveedorActualizado.getNombre() != null) {
            proveedorExistente.setNombre(proveedorActualizado.getNombre());
        }
        if (proveedorActualizado.getTelefono() != null) {
            proveedorExistente.setTelefono(proveedorActualizado.getTelefono());
        }
        if (proveedorActualizado.getCorreo() != null) {
            proveedorExistente.setCorreo(proveedorActualizado.getCorreo());
        }
        if (proveedorActualizado.getDireccion() != null) {
            proveedorExistente.setDireccion(proveedorActualizado.getDireccion());
        }
        if (proveedorActualizado.getAcivo() != null) {
            proveedorExistente.setAcivo(proveedorActualizado.getAcivo());
        }

        // Actualizar fecha de modificación
        proveedorExistente.setFechaActualizacion(LocalDateTime.now());

        return proveedoresRepository.save(proveedorExistente);
    }

    @Override
    public Proveedores desactivarProveedor(Long id) {
        Proveedores proveedor = proveedoresRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe el proveedor con id: " + id));

        proveedor.setAcivo(false);
        proveedor.setFechaActualizacion(LocalDateTime.now());

        return proveedoresRepository.save(proveedor);
    }
}