package org.generation.muebleria.repository;

import org.generation.muebleria.model.ImagenesProducto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImagenesProductoRepository extends JpaRepository<ImagenesProducto, Integer> {

    // Método para encontrar todas las imágenes por el ID del producto
    List<ImagenesProducto> findByProductoIdProducto(Integer idProducto);

}