package org.generation.muebleria.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PedidoRequest {
    private Long idUsuario;
    private Long idDireccion;

    //listamos los articulos y su cantidad [ID, canidad]
    private List<DetallePedidoRequest> detalles;
}
