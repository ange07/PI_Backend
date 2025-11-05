package org.generation.muebleria.repository;

import org.generation.muebleria.model.DetallesPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface DetallesPedidoRepository extends JpaRepository<DetallesPedido, Integer> {

    // Métodopara encontrar los detalles de pedido activos
    List<DetallesPedido> findByActivoTrue();

    // Buscar por cantidad
    List<DetallesPedido> findByCantidad(Integer cantidad);

    // Buscar por subtotal mayor a cierto valor
    List<DetallesPedido> findBySubtotalGreaterThan(BigDecimal subtotal);

    // Buscar activos ordenados por subtotal
    List<DetallesPedido> findByActivoTrueOrderBySubtotalDesc();
}