package org.generation.muebleria.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.generation.muebleria.dto.responseLite.ProductoResponseLite;

@Setter
@Getter
public class ImagenProductoResponse {
    private Long IdImagen;
    private String urlImagen;

    //referencia al producto al que pertenece
    private ProductoResponseLite producto;
}
