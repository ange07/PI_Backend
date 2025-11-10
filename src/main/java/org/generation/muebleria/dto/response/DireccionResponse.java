package org.generation.muebleria.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DireccionResponse {
    private Long idDireccion;
    private String alias;
    private String direccion;
    private String ciudad;
    private String estado;
    private String municipio;
    private String codigo_postal;
    private Boolean es_predeterminada;

}
