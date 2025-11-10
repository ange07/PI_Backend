package org.generation.muebleria.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.generation.muebleria.dto.responseLite.CategoriaResponseLite;
import org.generation.muebleria.dto.responseLite.ProveedorResponseLite;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoResponse {
    private Long idProducto;
    private String producto;
    private String descripcion;
    private BigDecimal precioActual;
    private Integer stockDisponible;
    private Boolean activo;
    private Boolean activoPorDependencia;

    private LocalDateTime fechaPedido; // Fecha de creación
    private LocalDateTime fechaActualizacion;

    //Uso de los DTOs Lite para las relaciones
    private CategoriaResponseLite categoria;
    private ProveedorResponseLite proveedor;
}
