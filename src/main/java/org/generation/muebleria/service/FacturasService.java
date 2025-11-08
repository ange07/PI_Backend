package org.generation.muebleria.service;

import lombok.AllArgsConstructor;
import org.generation.muebleria.dto.FacturaRequest;
import org.generation.muebleria.model.EstadoFactura;
import org.generation.muebleria.model.Facturas;
import org.generation.muebleria.model.Pedidos;
import org.generation.muebleria.repository.FacturasRepository;
import org.generation.muebleria.repository.PedidosRepository;
import org.generation.muebleria.service.interfaces.IFacturasService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * Servicio para la gestión de facturas.
 * Implementa la lógica de negocio del módulo de facturación.
 */
@Service
@AllArgsConstructor
public class FacturasService implements IFacturasService {

    private final FacturasRepository facturasRepository;
    private final PedidosRepository pedidosRepository;

    // Constante para IVA de México (16%)
    private static final BigDecimal IVA_TASA = new BigDecimal("0.16");
    private static final BigDecimal FACTOR_TOTAL = new BigDecimal("1.16"); // Para calcular subtotal desde total

    @Override
    public Facturas crearFactura(FacturaRequest request) {
        // 1. Validar que el pedido exista
        Pedidos pedido = pedidosRepository.findById(request.getIdPedido())
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe un pedido con ID: " + request.getIdPedido()));

        // 2. Validar que el pedido no tenga ya una factura
        if (facturasRepository.existsByPedido_IdPedido(request.getIdPedido())) {
            throw new IllegalArgumentException(
                    "El pedido con ID " + request.getIdPedido() + " ya tiene una factura asociada");
        }

        // 3. Validar que RFC y Razón Social no estén vacíos
        if (request.getRfc() == null || request.getRfc().trim().isEmpty()) {
            throw new IllegalArgumentException("El RFC es obligatorio para generar una factura");
        }
        if (request.getRazonSocial() == null || request.getRazonSocial().trim().isEmpty()) {
            throw new IllegalArgumentException("La Razón Social es obligatoria para generar una factura");
        }

        // 4. Calcular montos
        // El total del pedido incluye IVA, entonces:
        // subtotal = total / 1.16
        // iva = subtotal * 0.16
        // total = subtotal + iva (debe coincidir con pedido.getTotal())

        BigDecimal totalPedido = pedido.getTotal();
        BigDecimal subtotal = totalPedido.divide(FACTOR_TOTAL, 2, RoundingMode.HALF_UP);
        BigDecimal iva = subtotal.multiply(IVA_TASA).setScale(2, RoundingMode.HALF_UP);
        BigDecimal total = subtotal.add(iva);

        // 5. Crear la factura
        Facturas factura = new Facturas();
        factura.setPedido(pedido);
        factura.setRfc(request.getRfc().trim().toUpperCase()); // RFC en mayúsculas por convención
        factura.setRazonSocial(request.getRazonSocial().trim());
        factura.setSubtotal(subtotal);
        factura.setIva(iva);
        factura.setTotal(total);
        factura.setEstadoFactura(EstadoFactura.PENDIENTE);

        // 6. Guardar y retornar
        return facturasRepository.save(factura);
    }

    @Override
    public List<Facturas> obtenerFacturasPorEstado(EstadoFactura estado) {
        return facturasRepository.findByEstadoFactura(estado);
    }

    @Override
    public Facturas cambiarEstado(Long idFactura, EstadoFactura nuevoEstado) {
        // 1. Validar que la factura exista
        Facturas factura = facturasRepository.findById(idFactura)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe una factura con ID: " + idFactura));

        // 2. Validar transiciones de estado lógicas (opcional, para MVP puede omitirse)
        // PENDIENTE -> GENERADA -> ENVIADA (flujo normal)
        // Pero permitimos cualquier cambio para flexibilidad del admin

        // 3. Actualizar estado
        factura.setEstadoFactura(nuevoEstado);

        // 4. Guardar y retornar
        return facturasRepository.save(factura);
    }

    @Override
    public List<Facturas> obtenerFacturasPorUsuario(Long idUsuario) {
        return facturasRepository.findByPedido_Usuario_IdUsuario(idUsuario);
    }

    @Override
    public Facturas obtenerFacturaPorPedido(Long idPedido) {
        return facturasRepository.findByPedido_IdPedido(idPedido)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe una factura para el pedido con ID: " + idPedido));
    }

    @Override
    public List<Facturas> obtenerTodasLasFacturas() {
        return facturasRepository.findAll();
    }
}
