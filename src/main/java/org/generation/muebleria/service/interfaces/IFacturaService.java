package org.generation.muebleria.service.interfaces;

import org.generation.muebleria.dto.request.FacturaRequest;
import org.generation.muebleria.dto.response.FacturaResponse;
import org.generation.muebleria.model.Facturas;

import java.util.List;

/**
 * Interfaz para el Servicio de Facturas.
 * Define qué operaciones se pueden realizar.
 */
public interface IFacturaService {

    FacturaResponse generarFactura(FacturaRequest request);
    FacturaResponse getFacturaById(Long idFactura);
    FacturaResponse mapToResponseDTO(Facturas factura);
}