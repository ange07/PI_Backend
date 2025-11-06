package org.generation.muebleria.repository;

import org.generation.muebleria.model.Categorias;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categorias, Integer> {

    // Método para listar solo categorías activas
    List<Categorias> findByActivoTrue();

    // Método útil para buscar subcategorías (hijas) de una categoría padre
    List<Categorias> findByCategoriaPadreIdCategoriaAndActivoTrue(Integer idCategoriaPadre);

    // Método útil para buscar categorías principales (las que no tienen padre)
    List<Categorias> findByCategoriaPadreIsNullAndActivoTrue();

}