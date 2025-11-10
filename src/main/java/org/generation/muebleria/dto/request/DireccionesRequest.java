package org.generation.muebleria.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.generation.muebleria.model.Direccion;


@Setter
@Getter
public class DireccionesRequest {
    private Direccion.TipoDireccion tipoDireccion;
    private String alias;
    private String direccion;
    private String ciudad;
    private String estado;
    private String municipio;
    private String codigoPostal;
    private Boolean esPredeterminada = false;
    private Long idUsuario; // A quién pertenece
}
