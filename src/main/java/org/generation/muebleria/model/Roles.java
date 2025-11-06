package org.generation.muebleria.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name="roles")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_rol")
    private Integer idRol;

    @Column(name="nombre_rol", unique = true, nullable = false, length = 50)
    private String nombreRol;

    // --- Relación: Un Rol puede tener muchos Usuarios ---
    // La gestionamos aquí para evitar bucles JSON
    @OneToMany(mappedBy = "rol")
    @JsonBackReference // Lado "trasero": no se serializa para evitar bucles
    private Set<Usuarios> usuarios;

}