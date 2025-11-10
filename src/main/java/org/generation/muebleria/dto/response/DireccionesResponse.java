package org.generation.muebleria.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.generation.muebleria.dto.responseLite.UsuarioResponseLite;
import org.generation.muebleria.model.Direccion;


@Setter
@Getter
public class DireccionesResponse {
    private Long idDireccion;
    private Direccion.TipoDireccion tipoDireccion;
    private String alias;
    private String direccion;
    private String ciudad;
    private String estado;
    private String municipio;
    private String codigoPostal;
    private Boolean esPredeterminada;
    private UsuarioResponseLite usuario;
}
