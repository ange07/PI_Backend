package org.generation.muebleria.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="productos")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class Productos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_producto")
    private Long idProducto;
    @Column(name="producto", nullable = false, length = 200)
    private String producto;
    @Column(name="descripcion", length = 500)
    private String descripcion;
    @Column(name="precio_actual", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioActual;
    @Column(name="stock_disponible", nullable = false)
    private Integer stockDisponible;
    @Column(name="activo", columnDefinition = "TINYINT(1)")
    private Boolean activo = true;
    @Column(name="activo_por_dependencia", columnDefinition = "TINYINT(1)")
    private Boolean activo_por_dependencia = false;
    @Column(name="fecha_pedido")
    @CreationTimestamp //hibernate crea la fecha una sola vez
    private LocalDateTime fechaPedido;
    @Column(name="fecha_actualizacion",nullable = false )
    @UpdateTimestamp //hibernate actualiza la fecha cada update/add
    private LocalDateTime fechaActualizacion;

    // Relacion:  muchos -> uno (Categorias)
    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    @JsonBackReference
    private Categorias categoria;

    //Relacion: muchos -> uno (Proveedores)
    @ManyToOne
    @JoinColumn(name = "id_proveedor", nullable = false)
    private Proveedores proveedor;

    //Relacion:  uno -> muchos (Resena)
    //(Resena) -> se definio [producto] para el mappedBy
    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Resenas> resenas;

    //Relación: uno -> muchos (DetallesPedido)
    //(DetallesPedido) -> se definio [producto] para el mappedBy
    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<DetallesPedido> detallesPedido;

    //Relación: uno -> muchos (ImagenesProducto)
    //(ImagenesProducto) -> se definio [producto] para el mappedBy
    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<ImagenesProducto> imagenesProducto;


}
