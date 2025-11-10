package org.generation.muebleria.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DetallePedidoRequest {
    private Long idProducto;
    private Integer cantidad;
}
