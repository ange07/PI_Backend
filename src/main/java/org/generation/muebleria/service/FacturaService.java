package org.generation.muebleria.service;

import lombok.AllArgsConstructor;
import org.generation.muebleria.dto.FacturaRequest;
import org.generation.muebleria.model.Facturas;
import org.generation.muebleria.model.Pedidos; // Necesitamos el modelo de Pedidos
import org.generation.muebleria.repository.FacturasRepository;
import org.generation.muebleria.repository.PedidosRepository; // Necesitamos el repo de Pedidos
import org.generation.muebleria.service.interfaces.IFacturaService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor // Inyecta los repositorios por constructor
public class FacturaService implements IFacturaService {

    private final FacturasRepository facturasRepository;
    private final PedidosRepository pedidosRepository; //  trae la info de pedidos

    @Override
    public Facturas generarFactura(Long idPedido, FacturaRequest request) {
        // 1. Verificar que el pedido exista
        Pedidos pedido = pedidosRepository.findById(idPedido).orElseThrow(
                () -> new IllegalArgumentException("El pedido con ID " + idPedido + " no existe.")
        );

        // 2. Verificar que el pedido no tenga ya una factura
        if (facturasRepository.findByPedido_Id(idPedido).isPresent()) {
            throw new IllegalStateException("El pedido " + idPedido + " ya ha sido facturado.");
        }

        // 3. Crear la nueva factura
        Facturas nuevaFactura = new Facturas();

        // 4. Copiar datos del DTO (lo que dio el cliente)
        nuevaFactura.setRfc(request.getRfc());
        nuevaFactura.setRazonSocial(request.getRazonSocial());

        // 5. Copiar datos del Pedido (lo que sabemos del sistema)
        nuevaFactura.setPedido(pedido);
        nuevaFactura.setTotal(pedido.getTotal()); // Asumiendo que Pedido tiene getTotal()

        // 6. Calcular impuestos (Ejemplo simple de IVA al 16%)
        // (En un sistema real, el pedido ya traería esto desglosado)
        BigDecimal subtotal = pedido.getTotal().divide(new BigDecimal("1.16"), 2, BigDecimal.ROUND_HALF_UP);
        BigDecimal iva = pedido.getTotal().subtract(subtotal);

        nuevaFactura.setSubtotal(subtotal);
        nuevaFactura.setIva(iva);

        // 7. Guardar en la BD
        return facturasRepository.save(nuevaFactura);
    }

    @Override
    public Facturas getFacturaById(Long idFactura) {
        return facturasRepository.findById(idFactura).orElseThrow(
                () -> new IllegalArgumentException("Factura no encontrada")
        );
    }

    @Override
    public Facturas getFacturaByPedidoId(Long idPedido) {
        return facturasRepository.findByPedido_Id(idPedido).orElseThrow(
                () -> new IllegalArgumentException("No se encontró factura para el pedido " + idPedido)
        );
    }

    @Override
    public List<Facturas> getFacturasByRfc(String rfc) {
        return facturasRepository.findByRfc(rfc);
    }
}