package org.generation.muebleria.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

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
    private Integer idCategoria;

    @Column(name="nombre_categoria", unique = true, length = 100)
    private String nombreCategoria;

    @Column(name="activo")
    private Boolean activo = true;

    // --- Relación: Muchas categorías (hijas) pueden tener una categoría (padre) ---
    @ManyToOne
    @JoinColumn(name = "id_categoria_padre")
    @JsonManagedReference // Lado "principal" de la relación
    private Categorias categoriaPadre;

    // --- Relación: Una categoría (padre) puede tener muchas categorías (hijas) ---
    @OneToMany(mappedBy = "categoriaPadre")
    @JsonBackReference // Lado "trasero" para evitar bucles
    private Set<Categorias> subCategorias;

}