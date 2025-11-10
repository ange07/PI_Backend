package org.generation.muebleria.service.interfaces;

import org.generation.muebleria.dto.request.RolRequest;
import org.generation.muebleria.dto.response.RolResponse;
import org.generation.muebleria.model.Roles;
import java.util.List;

public interface IRolService {

    RolResponse addRol(RolRequest rolRequest);
    List<RolResponse> getAllRoles();
    RolResponse getRolById(Long id);
    RolResponse updateRol(Long id, RolRequest rolRequest);
    void deleteRol(Long id);

    RolResponse mapToResponseDTO(Roles rol);
}