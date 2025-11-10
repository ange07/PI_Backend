package org.generation.muebleria.service.interfaces;

import org.generation.muebleria.dto.request.PedidoRequest;
import org.generation.muebleria.dto.response.PedidoResponse;
import org.generation.muebleria.dto.responseLite.PedidoResponseLite;
import org.generation.muebleria.model.EstadoPedido;
import org.generation.muebleria.model.Pedidos;

import org.generation.muebleria.model.Usuarios;

import java.util.List;
import java.util.Optional;

public interface IPedidosService {

    Optional<PedidoResponse> getPedidosById(Long id);
    PedidoResponse crearPedidos(PedidoRequest pedido);
    Optional<PedidoResponse> updateEstadoPedidos(Long id, EstadoPedido nuevoEstado);
    List<PedidoResponse> getPedidosByUsuario(Usuarios usuario);
    List<PedidoResponse> getPedidosByEstado(EstadoPedido estado);
    void cancelado(Long id);

    PedidoResponseLite mapToLiteDTO(Pedidos pedido);
}
