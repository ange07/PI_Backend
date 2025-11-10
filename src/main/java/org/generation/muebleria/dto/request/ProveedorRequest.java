package org.generation.muebleria.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProveedorRequest {
    private String nombreEmpresa;
    private String nombre; // Nombre del contacto/persona
    private String telefono;
    private String correo;
    private String direccion;
    private Boolean activo;
}
