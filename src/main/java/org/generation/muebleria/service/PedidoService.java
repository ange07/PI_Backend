package org.generation.muebleria.service;
import jakarta.transaction.Transactional;
import org.generation.muebleria.dto.request.DetallePedidoRequest;
import org.generation.muebleria.dto.request.PedidoRequest;
import org.generation.muebleria.dto.response.DetallePedidoResponse;
import org.generation.muebleria.dto.response.DireccionResponse;
import org.generation.muebleria.dto.response.PedidoResponse;
import org.generation.muebleria.dto.response.ProductoResponse;
import org.generation.muebleria.dto.responseLite.PedidoResponseLite;
import org.generation.muebleria.dto.responseLite.ProductoResponseLite;
import org.generation.muebleria.dto.responseLite.UsuarioResponseLite;
import org.generation.muebleria.model.*;
import org.generation.muebleria.repository.DireccionRepository;
import org.generation.muebleria.repository.PedidosRepository;
import org.generation.muebleria.repository.ProductoRepository;
import org.generation.muebleria.repository.UsuariosRepository;
import org.generation.muebleria.service.interfaces.IPedidosService;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime; // Importante para las fechas
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor

public class PedidoService implements IPedidosService{
    private final PedidosRepository pedidoRepository;
    private final UsuariosRepository usuariosRepository;
    private final DireccionRepository direccionRepository;
    private final ProductoRepository productoRepository;

    @Override
    public Optional<PedidoResponse> getPedidosById(Long id) {
        return pedidoRepository.findById(id).map(this::mapToResponseDTO);
    }

    @Override
    @Transactional
    public PedidoResponse crearPedidos(PedidoRequest pedido) {
        Usuarios usuario = usuariosRepository.findById(pedido.getIdUsuario())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado."));

        Direccion direccion = direccionRepository.findById(pedido.getIdDireccion())
                .orElseThrow(() -> new IllegalArgumentException("Dirección no encontrada."));

        Pedidos newPedido = new Pedidos();
        newPedido.setUsuario(usuario);
        newPedido.setDireccion(direccion);
        newPedido.setEstadoPedido(EstadoPedido.PREPARANDO);

        BigDecimal totalPedido = BigDecimal.ZERO;
        List<DetallesPedido> detallesGuardados = new ArrayList<>();

        for (DetallePedidoRequest detalleDTO : pedido.getDetalles()) {
            Productos producto = productoRepository.findById(detalleDTO.getIdProducto())
                    .orElseThrow(() -> new IllegalArgumentException("Producto " + detalleDTO.getIdProducto() + " no encontrado."));

            // VALIDACIÓN DE STOCK
            if (producto.getStockDisponible() < detalleDTO.getCantidad()) {
                throw new IllegalArgumentException("Stock insuficiente para el producto: " + producto.getProducto());
            }

            // CALCULAR SUBTOAL Y PRECIO FIJO
            BigDecimal precioFijo = producto.getPrecioActual();
            BigDecimal subtotal = precioFijo.multiply(BigDecimal.valueOf(detalleDTO.getCantidad()));

            // CREAR ENTIDAD DETALLE
            DetallesPedido detalle = new DetallesPedido();
            detalle.setPedido(newPedido);
            detalle.setProducto(producto);
            detalle.setCantidad(detalleDTO.getCantidad());
            detalle.setPrecioUnitario(precioFijo);
            detalle.setSubtotal(subtotal);

            detallesGuardados.add(detalle);
            totalPedido = totalPedido.add(subtotal);

            // ACTUALIZAR STOCK DEL PRODUCTO
            producto.setStockDisponible(producto.getStockDisponible() - detalleDTO.getCantidad());
//            productoRepository.save(producto);
        }

        // 4. FINALIZAR Y GUARDAR PEDIDO
        newPedido.setDetallesPedidos(detallesGuardados);
        newPedido.setCostoEnvio(calcularCostoEnvio(direccion)); // Función auxiliar
        newPedido.setTotal(totalPedido.add(newPedido.getCostoEnvio()));
        newPedido.setNumeroGuia(generateTrackingNumber());

        Pedidos savedPedido = pedidoRepository.save(newPedido);

        return mapToResponseDTO(savedPedido);
    }

    // Función auxiliar para calcular el costo de envío
    private BigDecimal calcularCostoEnvio(Direccion direccion) {

        //Obteniendo costo de envio por estado
        String zona = direccion.getEstado(); // obtenemos el estado

        switch (zona.toUpperCase()) {
            case "CIUDAD":
                return new BigDecimal("5.00"); // Costo bajo para la misma ciudad
            case "ESTADO":
                return new BigDecimal("15.00"); // Costo medio para el estado de mexico
            default:
                // Tarifa por defecto o lanzar excepción
                return new BigDecimal("10.00");
        }
    }

    @Override
    public Optional<PedidoResponse> updateEstadoPedidos(Long id, EstadoPedido nuevoEstado) {
        Optional<Pedidos> pedidoOpt = pedidoRepository.findById(id);
        if (pedidoOpt.isEmpty()) {
            throw new IllegalArgumentException("El pedido con el ID "+id+" no existe");
        }

        Pedidos pedidoExistente = pedidoOpt.get();

        if (pedidoExistente.getEstadoPedido() == EstadoPedido.CANCELADO) {
            throw new IllegalStateException("No se puede modificar un pedido cancelado.");
        }

        //Devolución de Stock (Cuando se cancela)
        if (nuevoEstado == EstadoPedido.CANCELADO && pedidoExistente.getEstadoPedido() != EstadoPedido.CANCELADO) {
            // Llama a una función auxiliar para devolver el stock
            devolverStock(pedidoExistente);
        }

        // Actualizar estado y fecha (se actualizará automáticamente con hibernate)
        pedidoExistente.setEstadoPedido(nuevoEstado);

        // Guardar y mapear el DTO de Respuesta
        Pedidos updatedPedido = pedidoRepository.save(pedidoExistente);

        return Optional.of(mapToResponseDTO(updatedPedido));
    }

    @Override
    public List<PedidoResponse> getPedidosByUsuario(Usuarios usuario) {
        List<Pedidos> pedidosByUsuario = pedidoRepository.findByUsuario(usuario);
        return pedidosByUsuario.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PedidoResponse> getPedidosByEstado(EstadoPedido estado) {
        List<Pedidos> pedidosByEstado = pedidoRepository.findByEstadoPedido(estado);
        return pedidosByEstado.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    // Función auxiliar para devolver el stock
    private void devolverStock(Pedidos pedido) {
        for (DetallesPedido detalle : pedido.getDetallesPedidos()) {
            Productos producto = detalle.getProducto();
            // Sumar la cantidad devuelta al stock
            producto.setStockDisponible(producto.getStockDisponible() + detalle.getCantidad());
            // Guardar el producto actualizado
            productoRepository.save(producto);
        }
    }


    @Override
    public void cancelado(Long id) {
        updateEstadoPedidos(id,EstadoPedido.CANCELADO);
    }

    public PedidoResponse mapToResponseDTO(Pedidos pedido){
        PedidoResponse dto = new PedidoResponse();

        //mapeo de los campos
        dto.setIdPedido(pedido.getIdPedido());
        dto.setEstadoPedido(pedido.getEstadoPedido().toString());
        dto.setTotal(pedido.getTotal());
        dto.setCostoEnvio(pedido.getCostoEnvio());
        dto.setNumeroGuia(pedido.getNumeroGuia());
        dto.setFechaPedido(pedido.getFechaPedido());
        dto.setFechaActualizacion(pedido.getFechaActualizacion());

        if(pedido.getUsuario() != null){
            UsuarioResponseLite usuDto = new UsuarioResponseLite();
            usuDto.setIdUsuario(pedido.getUsuario().getIdUsuario());
            usuDto.setNombre(pedido.getUsuario().getNombre());
            usuDto.setApellidos(pedido.getUsuario().getApellidos());
            usuDto.setCorreo(pedido.getUsuario().getCorreo());

            dto.setUsuario(usuDto);
        }

        if(pedido.getDireccion() != null){
            DireccionResponse diDto = new DireccionResponse();
            diDto.setIdDireccion(pedido.getDireccion().getIdDireccion());
            diDto.setAlias(pedido.getDireccion().getAlias());
            diDto.setDireccion(pedido.getDireccion().getDireccion());
            diDto.setCiudad(pedido.getDireccion().getCiudad());
            diDto.setEstado(pedido.getDireccion().getEstado());
            diDto.setMunicipio(pedido.getDireccion().getMunicipio());
            diDto.setCodigo_postal(pedido.getDireccion().getCodigoPostal());
            diDto.setEs_predeterminada(pedido.getDireccion().getEsPredeterminada());

            dto.setDireccion(diDto);
        }

        //para mapear una lista con los campos de DetallesPedido se requrio de un mapeo previo
        //de los campos DetallePedidoResponse
        if(pedido.getDetallesPedidos() != null){
            List<DetallePedidoResponse> detalleDTO = pedido.getDetallesPedidos()
                    .stream().map(this::mapDetalleToResponseDTO)
                    .toList();

            dto.setDetallesPedidos(detalleDTO);
        }

        return dto;
    }

    //Mapeo de DetallePedidoResponse
    private DetallePedidoResponse mapDetalleToResponseDTO(DetallesPedido detalle) {
        DetallePedidoResponse dto = new DetallePedidoResponse();

        dto.setIdDetalle(detalle.getIdDetalle());
        dto.setCantidad(detalle.getCantidad());
        dto.setPrecioUnitario(detalle.getPrecioUnitario());
        dto.setSubtotal(detalle.getSubtotal());

        // Mapear el Producto Lite
        if (detalle.getProducto() != null) {
            ProductoResponseLite proDto = new ProductoResponseLite();
            proDto.setIdProducto(detalle.getProducto().getIdProducto());
            proDto.setProducto(detalle.getProducto().getProducto());

            dto.setProducto(proDto);
        }
        return dto;
    }

    public PedidoResponseLite mapToLiteDTO(Pedidos pedido) {
        if (pedido == null) return null;

        PedidoResponseLite dto = new PedidoResponseLite();

        dto.setIdPedido(pedido.getIdPedido());
        dto.setTotal(pedido.getTotal());
        dto.setFechaCreacion(pedido.getFechaPedido());

        // Si tu Pedidos tiene un campo estadoPedido (asumo que sí, es un Enum o String)
        if (pedido.getEstadoPedido() != null) {
            dto.setEstadoPedido(pedido.getEstadoPedido().name());
        }

        return dto;
    }

    private String generateTrackingNumber() {
        // Usamos el formato UUID para asegurar unicidad
        // Quitamos los guiones para un formato más limpio, ej: "a1b2c3d4e5f6g7h8"
        return UUID.randomUUID().toString().replace("-", "").substring(0, 16).toUpperCase();
    }

}
