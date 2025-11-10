package org.generation.muebleria.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoRequest {
    private String producto;
    private String descripcion;
    private BigDecimal precioActual;
    private Integer stockDisponible;
    private Boolean activo;

    private Long idCategoria;
    private Long idProveedor;

}
