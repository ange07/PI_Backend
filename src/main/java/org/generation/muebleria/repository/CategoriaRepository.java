package org.generation.muebleria.repository;

import org.generation.muebleria.model.Categorias;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categorias, Long> {

    //Métodopara listar solo categorías activas
    List<Categorias> findByActivoTrue();
    Optional<Categorias> findByNombreCategoria(String nombreCategoria);
}