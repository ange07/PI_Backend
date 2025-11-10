package org.generation.muebleria.controller;

import lombok.AllArgsConstructor;
import org.generation.muebleria.dto.request.UsuarioRequest;
import org.generation.muebleria.dto.response.UsuarioResponse;
import org.generation.muebleria.model.Usuarios;
import org.generation.muebleria.service.interfaces.IUsuariosService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/api/auth")
@AllArgsConstructor
public class AuthContoller {
    //inyectar el servicio
    private final IUsuariosService usersService;

    @PostMapping(path = "/register")
    public UsuarioResponse registerUser(@RequestBody UsuarioRequest user){
        return usersService.addUser(user);
    }

    @PostMapping(path = "/login")
    public boolean loginUser(@RequestBody UsuarioRequest user){
        return usersService.validateUser(user);
    }
}
