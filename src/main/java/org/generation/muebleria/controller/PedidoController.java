package org.generation.muebleria.controller;

import lombok.AllArgsConstructor;
import org.generation.muebleria.dto.request.PedidoRequest;
import org.generation.muebleria.dto.response.PedidoResponse;
import org.generation.muebleria.model.EstadoPedido;
import org.generation.muebleria.model.Usuarios;
import org.generation.muebleria.service.interfaces.IPedidosService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="/api/pedidos")
@AllArgsConstructor
public class PedidoController {
    private final IPedidosService pedidosService;

    //[GET:USUARIO] -> url -> /api/pedidos
    @GetMapping(path = "/{id}")
    public Optional<PedidoResponse> getPedido(@PathVariable("id")Long id){
        return pedidosService.getPedidosById(id);
    }

    //[GET: ADMIN] -> url -> /api/pedidos/admin/usuario/{userId}
    @GetMapping(path = "/admin/usuario/{userId}")
    public List<PedidoResponse> getPedidosByUsuario(@PathVariable("{userId}") Usuarios usuarioId){
        return pedidosService.getPedidosByUsuario(usuarioId);
    }

    //[GET: ADMIN] -> url -> /api/pedidos/admin/estado/{estadoId}
    @GetMapping(path = "/admin/estado/{estadoId}")
    public List<PedidoResponse> getPedidosByEstado(@PathVariable("{estadoId}") EstadoPedido estadoId){
        return pedidosService.getPedidosByEstado(estadoId);
    }

    //[POST: USUARIO] -> url -> /api/pedidos/add
    @PostMapping(path = "/add")
    public PedidoResponse addPedido(@RequestBody PedidoRequest pedido){
        return pedidosService.crearPedidos(pedido);
    }

    //[DELETE: USUARIO] -> url -> /api/pedidos/cancelar/{id}
    @DeleteMapping(path = "/cancelar/{id}")
    public void cancelarPedido(@PathVariable("id") Long id){
        pedidosService.cancelado(id);
    }

}
