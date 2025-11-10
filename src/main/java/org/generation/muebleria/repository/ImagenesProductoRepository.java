package org.generation.muebleria.repository;

import org.generation.muebleria.model.ImagenesProducto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImagenesProductoRepository extends JpaRepository<ImagenesProducto, Long> {

    List<ImagenesProducto> findByProductoIdProducto(Long idProducto);
}