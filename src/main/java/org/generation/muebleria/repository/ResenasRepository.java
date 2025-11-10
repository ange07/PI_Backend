package org.generation.muebleria.repository;

import org.generation.muebleria.model.Resenas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ResenasRepository extends JpaRepository<Resenas, Long> {

    // Buscar todas las reseñas de un producto específico
    List<Resenas> findByProductoIdProducto(Long idProducto);

    // Buscar todas las reseñas de un usuario específico
    List<Resenas> findByUsuarioIdUsuario(Long idUsuario);

    // Buscar todas las reseñas de un pedido específico
    List<Resenas> findByPedidoIdPedido(Long idPedido);

    // Buscar solo las reseñas visibles
    List<Resenas> findByResenaVisible(Boolean visible);
    List<Resenas> findByResenaVisibleTrue();
}