package org.generation.muebleria.service.interfaces;
import org.generation.muebleria.dto.request.UsuarioRequest;
import org.generation.muebleria.dto.response.UsuarioResponse;
import org.generation.muebleria.model.Usuarios;
//import org.generation.muebleria.model.Rol;

import java.util.List;
import java.util.Optional;

public interface IUsuariosService {

    List<UsuarioResponse> getAllUsers();
    UsuarioResponse getUserById(Long id);
    UsuarioResponse addUser(UsuarioRequest user);
    UsuarioResponse desactivarUserById(Long id);
    UsuarioResponse updateUserById(Long id, UsuarioRequest updatedUser);
    boolean validateUser(UsuarioRequest user);
    UsuarioResponse updateUserRole(Long userId, String newRoleName);
}
