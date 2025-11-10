package org.generation.muebleria.service;

import lombok.AllArgsConstructor;
import org.generation.muebleria.dto.request.FacturaRequest;
import org.generation.muebleria.dto.response.FacturaResponse;
import org.generation.muebleria.model.Facturas;
import org.generation.muebleria.model.Pedidos;
import org.generation.muebleria.repository.FacturasRepository;
import org.generation.muebleria.repository.PedidosRepository; // Necesitamos el repo de Pedidos
import org.generation.muebleria.service.interfaces.IFacturaService;
import org.generation.muebleria.service.interfaces.IPedidosService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@AllArgsConstructor // Inyecta los repositorios por constructor
public class FacturaService implements IFacturaService {

    private final FacturasRepository facturasRepository;
    private final PedidosRepository pedidoRepository; //  trae la info de pedidos
    private final IPedidosService pedidosService;

    @Override
    public FacturaResponse generarFactura(FacturaRequest request) {
        Pedidos pedido = pedidoRepository.findById(request.getIdPedido())
                .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado."));

        //Un pedido solo puede tener una factura.
        if (pedido.getFactura() != null) {
            throw new IllegalStateException("El pedido con ID " + pedido.getIdPedido() + " ya ha sido facturado.");
        }

        //Usar el Total del Pedido
        BigDecimal total = pedido.getTotal();
        // Asumiendo un IVA del 16% (Factor: 1 + 0.16 = 1.16)
        BigDecimal factorIVA = new BigDecimal("1.16");

        // Subtotal = Total / 1.16
        BigDecimal subtotal = total.divide(factorIVA, 2, RoundingMode.HALF_UP);
        // IVA = Total - Subtotal
        BigDecimal iva = total.subtract(subtotal);

        //Crear Entidad
        Facturas newFactura = new Facturas();
        newFactura.setPedido(pedido);
        newFactura.setRfc(request.getRfc());
        newFactura.setRazonSocial(request.getRazonSocial());
        newFactura.setSubtotal(subtotal);
        newFactura.setIva(iva);
        newFactura.setTotal(total); // El total de la factura es el total del pedido

        //Guardar y actualizar relación
        Facturas savedFactura = facturasRepository.save(newFactura);

        //Actualizar la referencia bidireccional en el Pedido
        pedido.setFactura(savedFactura);
        pedidoRepository.save(pedido);

        return mapToResponseDTO(savedFactura);
    }

    @Override
    public FacturaResponse getFacturaById(Long idFactura) {
        return facturasRepository.findById(idFactura)
                .map(this::mapToResponseDTO)
                .orElseThrow(() -> new IllegalArgumentException("Factura no encontrada con ID: " + idFactura));

    }

    @Override
    public FacturaResponse mapToResponseDTO(Facturas factura) {
        if (factura == null) return null;
        FacturaResponse dto = new FacturaResponse();
        dto.setIdFactura(factura.getIdFactura());
        dto.setRfc(factura.getRfc());
        dto.setRazonSocial(factura.getRazonSocial());
        dto.setSubtotal(factura.getSubtotal());
        dto.setIva(factura.getIva());
        dto.setTotal(factura.getTotal());
        dto.setFechaEmision(factura.getFechaEmision());

        if (factura.getPedido() != null) {
            dto.setPedido(pedidosService.mapToLiteDTO(factura.getPedido()));
        }
        return dto;
    }
}