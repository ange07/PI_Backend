package org.generation.muebleria.service.interfaces;

import org.generation.muebleria.dto.FacturaRequest;
import org.generation.muebleria.model.Facturas;

import java.util.List;

/**
 * Interfaz para el Servicio de Facturas.
 * Define qué operaciones se pueden realizar.
 */
public interface IFacturaService {

    /**
     * Genera una nueva factura para un pedido específico.
     * @param idPedido El ID del pedido a facturar.
     * @param request El DTO con los datos fiscales (RFC, Razón Social).
     * @return La factura recién creada.
     */
    Facturas generarFactura(Long idPedido, FacturaRequest request);

    /**
     * Busca una factura por su ID.
     */
    Facturas getFacturaById(Long idFactura);

    /**
     * Busca una factura usando el ID del pedido.
     */
    Facturas getFacturaByPedidoId(Long idPedido);

    /**
     * Busca todas las facturas de un cliente por su RFC.
     */
    List<Facturas> getFacturasByRfc(String rfc);
}