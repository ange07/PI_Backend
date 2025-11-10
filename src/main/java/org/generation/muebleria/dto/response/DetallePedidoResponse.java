package org.generation.muebleria.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.generation.muebleria.dto.responseLite.ProductoResponseLite;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DetallePedidoResponse {
    private Long idDetalle;
    private Integer cantidad;
    private BigDecimal precioUnitario; // Precio al momento de la compra
    private BigDecimal subtotal;

    // Relación con el Producto (lite)
    private ProductoResponseLite producto;

    //este DTO se anida dentro del PedidoResponse
}
