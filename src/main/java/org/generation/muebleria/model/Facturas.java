package org.generation.muebleria.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

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
    private Long idFactura;
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
    @Column(name = "fecha_emision")
    @CreationTimestamp //hibernate crea la fecha una sola vez
    private LocalDateTime fechaEmision;


    // Relacion: uno -> uno (Pedidos)
    @OneToOne
    @JoinColumn(name = "id_pedido")
    @JsonBackReference
    private Pedidos pedido;
}