package org.generation.muebleria.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="pedidos")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Pedidos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_pedido")
    private Long idPedido;
    @Column(name = "estado_pedido", nullable = false)
    private EstadoPedido estadoPedido;
    @Column(name = "total", nullable = false, precision = 10, scale = 2)
    private BigDecimal total;
    @Column(name = "costo_envio", nullable = false, precision = 10, scale = 2)
    private BigDecimal costoEnvio;
    @Column(name = "numero_guia", nullable = false, length = 150)
    private String numeroGuia;
    @Column(name = "fecha_pedido", insertable = false, updatable = false)
    private LocalDateTime fechaPedido;
    @Column(name = "fecha_actualizacion", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime fechaActualizacion;

    //Relacion: uno -> uno (facturas)
    @OneToOne
    @JoinColumn(name = "id_factura")
    @JsonIgnore
    private Facturas factura;

    //Relacion uno -> muchos (Resenas)
    //(Resenas) -> se definio [pedido] para el mappedBy
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Resenas> resenas;

    //Relacion: uno -> muchos (DetallesPedidos)
    //(DetallesPedido) -> se definio [pedido] para el mappedBy
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<DetallesPedido> detallesPedidos;

    //Relacion: muchos -> uno (Usuarios)
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuarios usuario;

    //Relacion: muchos -> uno (Direccion)
    @ManyToOne
    @JoinColumn(name = "id_direccion", nullable = false)
    private Direccion direccion;
}


