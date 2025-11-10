package org.generation.muebleria.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="categorias")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Categorias {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_categoria")
    private Long idCategoria;
    @Column(name="nombre_categoria", unique = true, nullable = false, length = 100)
    private String nombreCategoria;
    @Column(name="activo", columnDefinition = "TINYINT(1)")
    private Boolean activo = true;

    // Relacion:  uno -> muchos (Productos)
    //(Productos) -> se definio [categoria] para el mappedBy
    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Productos> productos;

    //relacion que se apunta a si misma (para categorias anidadas)
    @ManyToOne
    @JoinColumn(name = "id_categoria_padre", nullable = true)
    @JsonBackReference
    private Categorias categoriaPadre;
}