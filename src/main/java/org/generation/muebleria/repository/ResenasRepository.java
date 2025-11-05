package org.generation.muebleria.repository;

import org.generation.muebleria.model.Resenas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ResenasRepository extends JpaRepository<Resenas, Integer> {

    // Métodopara encontrar todas las reseñas activas
    List<Resenas> findByActivoTrue();

    // Métodopara encontrar reseñas visibles
    List<Resenas> findByResenaVisibleTrueAndActivoTrue();

    // Métodopara encontrar reseñas por calificación
    List<Resenas> findByCalificacionAndActivoTrue(Integer calificacion);

    // Buscar reseñas ordenadas por fecha (más recientes primero)
    List<Resenas> findByActivoTrueOrderByFechaResenaDesc();

    // Buscar reseñas con calificación mayor o igual a X
    List<Resenas> findByCalificacionGreaterThanEqualAndActivoTrue(Integer calificacion);

    // Contar reseñas activas
    Long countByActivoTrue();
}