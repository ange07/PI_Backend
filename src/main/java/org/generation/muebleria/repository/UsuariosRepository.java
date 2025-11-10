package org.generation.muebleria.repository;

import org.generation.muebleria.model.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuariosRepository extends JpaRepository<Usuarios,Long> {
    List<Usuarios> findByActivoTrue();
    Optional<Usuarios> findByCorreo(String correo);
}
