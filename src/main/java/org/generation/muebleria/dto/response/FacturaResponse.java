package org.generation.muebleria.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.generation.muebleria.dto.responseLite.PedidoResponseLite;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class FacturaResponse {
    private Long idFactura;
    private String rfc;
    private String razonSocial;
    private BigDecimal subtotal;
    private BigDecimal iva;
    private BigDecimal total;
    private LocalDateTime fechaEmision;
    private PedidoResponseLite pedido;
}
