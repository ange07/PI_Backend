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

    /**
     ** Se puede buscar una factura usando el ID del pedido asociado a la factura.
     * Los tipos de dato del ID son Long
     */
    Optional<Facturas> findByPedido_IdPedido(Long idPedido);

    /**
     * También podríamos querer buscar facturas por RFC.
     * @param rfc El RFC del cliente.
     * @return Una lista de facturas asociadas a ese RFC.
     */
    List<Facturas> findByRfc(String rfc);

}