package org.generation.muebleria.repository;

import org.generation.muebleria.model.Proveedores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProveedoresRepository extends JpaRepository<Proveedores, Long> {

    // Buscar proveedores activos
    List<Proveedores> findByAcivoTrue();

}