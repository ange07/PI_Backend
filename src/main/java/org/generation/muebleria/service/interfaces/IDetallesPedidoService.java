package org.generation.muebleria.service.interfaces;

import org.generation.muebleria.model.DetallesPedido;
import java.util.List;

public interface IDetallesPedidoService {

    List<DetallesPedido> getAllDetallesPedidoActive();

    DetallesPedido getDetallesPedidoById(Integer id);

    DetallesPedido createDetallesPedido(DetallesPedido detallesPedido);

    DetallesPedido updateDetallesPedidoById(Integer id, DetallesPedido updateDetallesPedido);

    DetallesPedido deleteDetallesPedidoById(Integer id);
}