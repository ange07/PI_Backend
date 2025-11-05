package org.generation.muebleria.service;

import lombok.AllArgsConstructor;
import org.generation.muebleria.model.DetallesPedido;
import org.generation.muebleria.repository.DetallesPedidoRepository;
import org.generation.muebleria.service.interfaces.IDetallesPedidoService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class DetallesPedidoService implements IDetallesPedidoService {

    // Inyección de dependencia
    private DetallesPedidoRepository detallesPedidoRepository;

    // Implementación de los métodos de la interface (IDetallesPedidoService)
    @Override
    public List<DetallesPedido> getAllDetallesPedidoActive() {
        return detallesPedidoRepository.findByActivoTrue();
    }

    @Override
    public DetallesPedido getDetallesPedidoById(Integer id) {
        return detallesPedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Detalle de pedido no encontrado con id: " + id));
    }

    @Override
    public DetallesPedido createDetallesPedido(DetallesPedido detallesPedido) {
        detallesPedido.setActivo(true);
        return detallesPedidoRepository.save(detallesPedido);
    }

    @Override
    public DetallesPedido updateDetallesPedidoById(Integer id, DetallesPedido updateDetallesPedido) {
        DetallesPedido detalleExistente = getDetallesPedidoById(id);

        if (updateDetallesPedido.getCantidad() != null) {
            detalleExistente.setCantidad(updateDetallesPedido.getCantidad());
        }
        if (updateDetallesPedido.getPrecioUnitario() != null) {
            detalleExistente.setPrecioUnitario(updateDetallesPedido.getPrecioUnitario());
        }
        if (updateDetallesPedido.getSubtotal() != null) {
            detalleExistente.setSubtotal(updateDetallesPedido.getSubtotal());
        }

        return detallesPedidoRepository.save(detalleExistente);
    }

    @Override
    public DetallesPedido deleteDetallesPedidoById(Integer id) {
        DetallesPedido detalle = getDetallesPedidoById(id);
        detalle.setActivo(false);
        return detallesPedidoRepository.save(detalle);
    }
}