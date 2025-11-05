package org.generation.muebleria.model;

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
    private Integer idResena;

    @Column(name = "calificacion", nullable = false)
    private Integer calificacion;

    @Column(name = "comentario", columnDefinition = "TEXT")
    private String comentario;

    @Column(name = "resena_visible", nullable = false)
    private Boolean resenaVisible = true;

    @CreationTimestamp
    @Column(name = "fecha_resena", updatable = false)
    private LocalDateTime fechaResena;

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;
}