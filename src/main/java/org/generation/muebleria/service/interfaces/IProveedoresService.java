package org.generation.muebleria.service.interfaces;

import org.generation.muebleria.dto.request.ProveedorRequest;
import org.generation.muebleria.dto.response.ProveedorResponse;
import org.generation.muebleria.model.Proveedores;
import java.util.List;
import java.util.Optional;

public interface IProveedoresService {
    ProveedorResponse mapToResponseDTO(Proveedores proveedor);

    List<ProveedorResponse> getProveedoresActivos();
    Optional<ProveedorResponse> getProveedorById(Long id);
    List<ProveedorResponse> getAllProveedores();
    ProveedorResponse addProveedor(ProveedorRequest proveedor);
    ProveedorResponse updateProveedor(Long id, ProveedorRequest proveedor);

    void desactivarProveedorById(Long id);
    void activarProveedorById(Long id);
}