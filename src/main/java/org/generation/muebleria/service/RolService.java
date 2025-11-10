package org.generation.muebleria.service;

import lombok.AllArgsConstructor;
import org.generation.muebleria.dto.request.RolRequest;
import org.generation.muebleria.dto.response.RolResponse;
import org.generation.muebleria.model.Roles;
import org.generation.muebleria.repository.RolRepository;
import org.generation.muebleria.service.interfaces.IRolService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor // Inyecta el repositorio automáticamente
public class RolService implements IRolService {

    // Inyección de dependencia
    public final RolRepository rolesRepository;


    @Override
    public RolResponse addRol(RolRequest rolRequest) {
        if (rolesRepository.findByNombreRol(rolRequest.getNombreRol()).isPresent()) {
            throw new IllegalArgumentException("El rol '" + rolRequest.getNombreRol() + "' ya existe.");
        }
        Roles nuevoRol = new Roles();
        nuevoRol.setNombreRol(rolRequest.getNombreRol());

        Roles savedRol = rolesRepository.save(nuevoRol);
        return mapToResponseDTO(savedRol);
    }

    @Override
    public List<RolResponse> getAllRoles() {
        return rolesRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RolResponse getRolById(Long id) {
        return rolesRepository.findById(id)
                .map(this::mapToResponseDTO)
                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado con ID: " + id));
    }

    @Override
    public RolResponse updateRol(Long id, RolRequest rolRequest) {
        Roles rolExistente = rolesRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado con ID: " + id));

        // Validación de unicidad si el nombre cambia
        if (!rolExistente.getNombreRol().equals(rolRequest.getNombreRol())) {
            if (rolesRepository.findByNombreRol(rolRequest.getNombreRol()).isPresent()) {
                throw new IllegalArgumentException("El nuevo nombre de rol ya existe.");
            }
            rolExistente.setNombreRol(rolRequest.getNombreRol());
        }

        Roles updatedRol = rolesRepository.save(rolExistente);
        return mapToResponseDTO(updatedRol);
    }

    @Override
    public void deleteRol(Long id) {
        if (!rolesRepository.existsById(id)) {
            throw new IllegalArgumentException("Rol no encontrado con ID: " + id);
        }
        //La eliminación en cascada borrará los usuarios asociados si no se reasignan.
        rolesRepository.deleteById(id);
    }

    public RolResponse mapToResponseDTO(Roles rol) {
        if (rol == null) return null;

        RolResponse dto = new RolResponse();
        dto.setIdRol(rol.getIdRol());
        dto.setNombreRol(rol.getNombreRol());

        return dto;
    }
}