package org.generation.muebleria.repository;

import org.generation.muebleria.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Roles, Integer> {

    // Método para encontrar un rol por su nombre (útil para validaciones)
    Optional<Roles> findByNombreRol(String nombreRol);

}