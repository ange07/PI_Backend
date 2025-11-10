package org.generation.muebleria.dto.responseLite;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponseLite {
    private Long idUsuario;
    private String nombre;
    private String apellidos;
    private String Correo;
}
