package org.generation.muebleria.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProveedorResponse {
    private Long idProveedor;
    private String nombreEmpresa;
    private String nombre;
    private String telefono;
    private String correo;
    private String direccion;
    private Boolean activo;
    private LocalDateTime fechaRegistro;
    private LocalDateTime fechaActualizacion;
}
