package org.generation.muebleria.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class UsuarioResponse {
    private Long idUsuario;
    private String nombre;
    private String apellidos;
    private String correo;
    private String telefono;
    private Boolean activo;
    private LocalDateTime fechaRegistro;
    private LocalDateTime fechaActualizacion;
    private RolResponse rol;
}
