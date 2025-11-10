package org.generation.muebleria.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;

@Setter
@Getter
public class ResenaRequest {

    private Integer calificacion;
    private String comentario;
    private Boolean resenaVisible;


    private Long idProducto;
    private Long idUsuario;
    private Long idPedido;
}
