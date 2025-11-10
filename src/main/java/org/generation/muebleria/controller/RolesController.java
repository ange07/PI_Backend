package org.generation.muebleria.controller;

import lombok.AllArgsConstructor;
import org.generation.muebleria.dto.request.RolRequest;
import org.generation.muebleria.dto.response.RolResponse;
import org.generation.muebleria.service.interfaces.IRolService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@AllArgsConstructor
public class RolesController {
    private final IRolService rolesService;

    // [POST] Crear
    @PostMapping
    public RolResponse addRol(@RequestBody RolRequest request) {
        return rolesService.addRol(request);
    }

    // [GET] Listar Todos
    @GetMapping
    public List<RolResponse> getAllRoles() {
        return rolesService.getAllRoles();
    }

    // [GET] Por ID
    @GetMapping("/{id}")
    public RolResponse getRolById(@PathVariable Long id) {
        return rolesService.getRolById(id);
    }

    // [PUT] Actualizar
    @PutMapping("/{id}")
    public RolResponse updateRol(@PathVariable Long id, @RequestBody RolRequest request) {
        return rolesService.updateRol(id, request);
    }

    // [DELETE] Eliminar
    @DeleteMapping("/{id}")
    public void deleteRol(@PathVariable Long id) {
        rolesService.deleteRol(id);
    }
}
