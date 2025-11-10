package org.generation.muebleria.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UsuarioRequest {
    private String nombre;
    private String apellidos;
    private String correo;
    // La contraseña es clave para el registro/actualización
    private String password;
    private String telefono;
    // Opcional: Para admins que quieran cambiar el rol (si no, se asigna por defecto en el service)
    private Long idRol;
    private String nombreRol;
}
