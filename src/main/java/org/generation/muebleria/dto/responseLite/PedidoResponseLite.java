package org.generation.muebleria.dto.responseLite;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
public class PedidoResponseLite {
    private Long idPedido;
    private BigDecimal total;
    private LocalDateTime fechaCreacion;
    private String estadoPedido;
}
