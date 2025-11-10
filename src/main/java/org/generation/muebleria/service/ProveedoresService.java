package org.generation.muebleria.service;

import lombok.AllArgsConstructor;
import org.generation.muebleria.dto.request.ProveedorRequest;
import org.generation.muebleria.dto.response.ProveedorResponse;
import org.generation.muebleria.model.Productos;
import org.generation.muebleria.model.Proveedores;
import org.generation.muebleria.repository.ProveedoresRepository;
import org.generation.muebleria.service.interfaces.IProveedoresService;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProveedoresService implements IProveedoresService {

    private final ProveedoresRepository proveedoresRepository;

    @Override
    public List<ProveedorResponse> getProveedoresActivos() {
        List<Proveedores> proveedoresActivos = proveedoresRepository.findByActivoTrue();

        return proveedoresActivos.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ProveedorResponse> getProveedorById(Long id) {
        Optional<Proveedores> proveedoresId = Optional.ofNullable(proveedoresRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("El proveedor con el id: " + id + " no existe")
        ));
        return proveedoresId.map(this::mapToResponseDTO);
    }

    @Override
    public List<ProveedorResponse> getAllProveedores() {
        List<Proveedores> proveedores = proveedoresRepository.findAll();
        return proveedores.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProveedorResponse addProveedor(ProveedorRequest proveedor) {
        if(proveedoresRepository.findByNombreEmpresa(proveedor.getNombreEmpresa()).isPresent()){
            throw new IllegalArgumentException("Ya existe un proveedor con esa empresa.");
        }

        Proveedores nuevoProveedor = new Proveedores();
        nuevoProveedor.setNombreEmpresa(proveedor.getNombreEmpresa());
        nuevoProveedor.setNombre(proveedor.getNombre());
        nuevoProveedor.setTelefono(proveedor.getTelefono());
        nuevoProveedor.setCorreo(proveedor.getCorreo());
        nuevoProveedor.setDireccion(proveedor.getDireccion());

        Proveedores saveProveedor = proveedoresRepository.save(nuevoProveedor);

        return mapToResponseDTO(saveProveedor);
    }

    @Override
    public ProveedorResponse updateProveedor(Long id, ProveedorRequest proveedor) {
        Proveedores proveedorDB = proveedoresRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Proveedor no encontrado."));

        if (proveedor.getNombreEmpresa() != null) proveedorDB.setNombreEmpresa(proveedor.getNombreEmpresa());
        if (proveedor.getNombre() != null) proveedorDB.setNombre(proveedor.getNombre());
        if (proveedor.getTelefono() != null) proveedorDB.setTelefono(proveedor.getTelefono());
        if (proveedor.getCorreo() != null) proveedorDB.setCorreo(proveedor.getCorreo());
        if (proveedor.getDireccion() != null) proveedorDB.setDireccion(proveedor.getDireccion());
        if (proveedor.getActivo() != null) proveedorDB.setActivo(proveedor.getActivo());

        Proveedores saveProveedor = proveedoresRepository.save(proveedorDB);

        return mapToResponseDTO(saveProveedor);
    }

    @Override
    public void desactivarProveedorById(Long id) {
        Proveedores proveedor = proveedoresRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Proveedor no encontrado con ID: " + id)
        );
        if (proveedor.getActivo()) {
            proveedor.setActivo(false);
            for(Productos producto: proveedor.getProductos()){
                if(producto.getActivo()){
                    producto.setActivo(false);
                    producto.setActivo_por_dependencia(true);
                }
            }
            proveedoresRepository.save(proveedor);
        }else{
            throw new IllegalArgumentException("El proveedor ya esta desactivado");
        }
    }

    @Override
    public void activarProveedorById(Long id) {
        Proveedores proveedor = proveedoresRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Proveedor no encontrado con ID: " + id));
        if (!proveedor.getActivo()) {
            proveedor.setActivo(true);
            for(Productos producto: proveedor.getProductos()){
                if(!producto.getActivo() && producto.getActivo_por_dependencia()){
                    boolean categoriaActivo = producto.getCategoria().getActivo();
                    if(categoriaActivo){
                        producto.setActivo(true);
                        producto.setActivo_por_dependencia(false);
                    }
                }
            }
            proveedoresRepository.save(proveedor);
        }else{
            throw new IllegalArgumentException("El proveedor ya esta activo");
        }
    }

    @Override
    public ProveedorResponse mapToResponseDTO(Proveedores proveedor) {
        ProveedorResponse dto = new ProveedorResponse();

        dto.setIdProveedor(proveedor.getIdProveedor());
        dto.setNombreEmpresa(proveedor.getNombreEmpresa());
        dto.setNombre(proveedor.getNombre());
        dto.setTelefono(proveedor.getTelefono());
        dto.setCorreo(proveedor.getCorreo());
        dto.setDireccion(proveedor.getDireccion());
        dto.setActivo(proveedor.getActivo());

        dto.setFechaRegistro(proveedor.getFechaRegistro());
        dto.setFechaActualizacion(proveedor.getFechaActualizacion());

        return dto;
    }
}