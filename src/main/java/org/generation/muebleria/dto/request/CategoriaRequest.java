package org.generation.muebleria.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoriaRequest {
    private String nombreCategoria;
    private Long idCategoriaPadre;
    private Boolean activo;
}
