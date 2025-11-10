package org.generation.muebleria.controller;

import lombok.AllArgsConstructor;
import org.generation.muebleria.dto.request.UsuarioRequest;
import org.generation.muebleria.dto.response.UsuarioResponse;
import org.generation.muebleria.model.Usuarios;
import org.generation.muebleria.service.interfaces.IUsuariosService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api/users")
@AllArgsConstructor
public class UsuarioController {
    private final IUsuariosService usersService;

    @GetMapping
    public List<UsuarioResponse> getAllUsers(){
        return usersService.getAllUsers();
    }

    @GetMapping(path="/{userId}")
    public UsuarioResponse getUserById(@PathVariable("userId")Long id){
        return usersService.getUserById(id);
    }

    @PutMapping(path = "/{userId}")
    public UsuarioResponse updateProduct(@PathVariable("userId")Long id, @RequestBody UsuarioRequest user){
        return usersService.updateUserById(id,user);
    }

    @DeleteMapping(path="/{userId}")
    public UsuarioResponse deleteUserById(@PathVariable("userId")Long id){
        return usersService.desactivarUserById(id);
    }

    //security
    @PutMapping("/admin/{userId}/role")
    public UsuarioResponse changeUserRole(@PathVariable Long userId, @RequestParam String newRoleName) {
        return usersService.updateUserRole(userId, newRoleName);
    }
}
