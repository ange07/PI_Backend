package org.generation.muebleria.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.generation.muebleria.dto.responseLite.PedidoResponseLite;
import org.generation.muebleria.dto.responseLite.ProductoResponseLite;
import org.generation.muebleria.dto.responseLite.UsuarioResponseLite;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResenasResponse {
    private Long idResena;
    private Integer calificacion;
    private String comentario;
    private LocalDateTime fechaCreacion;

    // Relación con el Producto (solo ID y nombre)
    private ProductoResponseLite producto;

    // Relación con el Usuario (solo nombre de quien lo escribio)
    private UsuarioResponseLite usuario;

    // Relación con el Pedido (solo ID)
    private PedidoResponseLite pedido;

}
