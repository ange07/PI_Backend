package org.generation.muebleria.repository;

import org.generation.muebleria.model.Facturas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad Facturas.
 */
@Repository
public interface FacturasRepository extends JpaRepository<Facturas, Long> {

    Optional<Facturas> findByPedidoIdPedido(Long idPedido);
}