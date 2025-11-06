package org.generation.muebleria.service.interfaces;

import org.generation.muebleria.model.Roles;
import java.util.List;

public interface IRolService {

    // Obtener todos los roles
    List<Roles> getAllRoles();

    // Obtener un rol por ID
    Roles getRolById(Integer id);

    // Obtener un rol por Nombre
    Roles getRolByNombre(String nombre);

    // Crear un nuevo rol
    Roles createRol(Roles rol);

    // Actualizar un rol
    Roles updateRolById(Integer id, Roles rolActualizado);

    // Borrar un rol (Hard Delete)
    void deleteRolById(Integer id);

}