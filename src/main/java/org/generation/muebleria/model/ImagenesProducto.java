package org.generation.muebleria.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="imagenes_producto")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ImagenesProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_imagen")
    private Integer idImagen;

    @Column(name="url_imagen", nullable = false, length = 500)
    private String urlImagen;

    // --- Relación: Muchas imágenes pertenecen a un Producto ---
    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
    @JsonBackReference // Lado "trasero": evita bucles al serializar
    private Productos producto;

}