package org.generation.muebleria.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "reseñas")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resenas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_resena")
    private Long idResena;
    @Column(name = "calificacion", nullable = false)
    private Integer calificacion;
    @Column(name = "comentario", columnDefinition = "TEXT")
    private String comentario;
    @Column(name = "resena_visible", columnDefinition = "TINYINT(1)")
    private Boolean resenaVisible = true;
    @Column(name = "fecha_resena")
    @CreationTimestamp //hibernate crea la fecha una sola vez
    private LocalDateTime fechaResena;

    // Relacion:  muchos -> uno (Productos)
    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
    @JsonBackReference
    private Productos producto;

    //Relacion: muchos -> uno (Usuarios)
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    @JsonBackReference
    private Usuarios usuario;

    //Relacion: muchos -> uno (Pedidos)
    @ManyToOne
    @JoinColumn(name = "id_pedido", nullable = false)
    @JsonBackReference
    private Pedidos pedido;
}