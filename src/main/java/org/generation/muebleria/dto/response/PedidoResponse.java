package org.generation.muebleria.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.generation.muebleria.dto.responseLite.UsuarioResponseLite;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class PedidoResponse {
    private Long idPedido;
    private String estadoPedido; // El enum se convierte a String
    private BigDecimal total;
    private BigDecimal costoEnvio;
    private String numeroGuia;
    private LocalDateTime fechaPedido;
    private LocalDateTime fechaActualizacion;

    // Relaciones (usando DTOs Lite o Response):
    private UsuarioResponseLite usuario;
    private DireccionResponse direccion; // Usamos un DTO completo para la dirección

    // La lista de artículos comprados:
    private List<DetallePedidoResponse> detallesPedidos;

    // La factura y las reseñas pueden ser opcionales y solo mostrarse si existen
    // private FacturaResponseLiteDTO factura;
    // private List<ResenaResponseDTO> resenas;
}
