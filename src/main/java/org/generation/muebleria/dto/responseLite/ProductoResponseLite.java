package org.generation.muebleria.dto.responseLite;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoResponseLite {
    private Long idProducto;
    private String producto;
}
