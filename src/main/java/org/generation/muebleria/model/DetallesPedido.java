package org.generation.muebleria.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "detalles_pedido")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetallesPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private Long idDetalle;
    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;
    @Column(name = "precio_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitario;
    @Column(name = "subtotal", nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;


    //Relacion: muchos -> uno (Productos)
    @ManyToOne
    @JoinColumn(name="id_producto", nullable = false)
    @JsonBackReference
    private Productos producto;

    //Relacion: muchos -> uno (Pedidos)
    @ManyToOne
    @JoinColumn(name="id_pedidos", nullable = false)
    @JsonBackReference
    private Pedidos pedido;

}