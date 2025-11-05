package org.generation.muebleria.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Tabla Facturas de la base de datos debe tener una relación OnetoOne con Pedidos.
 * Un pedido solo puede tener una factura.
 */
@Entity
@Table(name = "Facturas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Facturas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_factura")
    private Long idFactura; // HAY QUE CHECAR ESTO n la base de datos sale como Int pero es Long

    @Column(name = "rfc", nullable = false, length = 15)
    private String rfc;

    @Column(name = "razon_social", nullable = false, length = 150)
    private String razonSocial;

    @Column(name = "subtotal", nullable = false)
    private BigDecimal subtotal;

    @Column(name = "iva", nullable = false)
    private BigDecimal iva;

    @Column(name = "total", nullable = false)
    private BigDecimal total;

    // Mapea la columna fecha_emision que tiene un valor por defecto en la BD.
    // No la actualizamos ni la insertamos desde Java, dejamos que la BD maneje el default.
    @Column(name = "fecha_emision", insertable = false, updatable = false)
    private LocalDateTime fechaEmision;

    // AQUI va la relación con Pedidos
    /* @OneToOne
    @JoinColumn(name = "id_pedido")
    @JsonIgnore
    private Pedidos pedido;
    */
}