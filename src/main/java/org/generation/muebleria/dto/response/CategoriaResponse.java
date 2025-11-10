package org.generation.muebleria.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.generation.muebleria.dto.responseLite.CategoriaResponseLite;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaResponse {
    private Long idCategoria;
    private String nombreCategoria;
    private Boolean activo;
    private Long idCategoriaPadre;

    //se puede implementar aqui una lista para mostrar las subcategorias
    public List<CategoriaResponseLite> subcategorias;
}
