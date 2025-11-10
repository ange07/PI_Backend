package org.generation.muebleria.service;

import lombok.AllArgsConstructor;
import org.generation.muebleria.dto.request.ResenaRequest;
import org.generation.muebleria.dto.response.ResenasResponse;
import org.generation.muebleria.dto.responseLite.PedidoResponseLite;
import org.generation.muebleria.dto.responseLite.ProductoResponseLite;
import org.generation.muebleria.dto.responseLite.UsuarioResponseLite;
import org.generation.muebleria.model.Pedidos;
import org.generation.muebleria.model.Productos;
import org.generation.muebleria.model.Resenas;
import org.generation.muebleria.model.Usuarios;
import org.generation.muebleria.repository.PedidosRepository;
import org.generation.muebleria.repository.ProductoRepository;
import org.generation.muebleria.repository.ResenasRepository;
import org.generation.muebleria.repository.UsuariosRepository;
import org.generation.muebleria.service.interfaces.IResenasService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ResenasService implements IResenasService {

    // Inyección de dependencia
    private ResenasRepository resenasRepository;
    private ProductoRepository productoRepository;
    private UsuariosRepository usuariosRepository;
    private PedidosRepository pedidosRepository;


    @Override
    public List<ResenasResponse> getAllResenasVisible() {
        List<Resenas> resenasActivas = resenasRepository.findByResenaVisibleTrue();
        return resenasActivas.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ResenasResponse> getAllResenas() {
        List<Resenas> resenas = resenasRepository.findAll();
        return resenas.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ResenasResponse> getResenaById(Long id) {
        Optional<Resenas> resenaById = Optional.ofNullable(resenasRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("La reseña con el id: " + id + " no existe")
        ));
        return resenaById.map(this::mapToResponseDTO);
    }

    @Override
    public List<ResenasResponse> getResenasByProducto(Long idProducto) {
        pedidosRepository.findById(idProducto).orElseThrow(
                () -> new IllegalArgumentException("El producto con el ID "+idProducto+" no existe")
        );

        List<Resenas> resenaByProducto = resenasRepository.findByProductoIdProducto(idProducto);

        return resenaByProducto.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ResenasResponse> getResenasByUsuario(Long idUsuario) {
        usuariosRepository.findById(idUsuario).orElseThrow(
                () -> new IllegalArgumentException("El usuario con el ID "+idUsuario+" no existe")
        );

        List<Resenas> resenaByUsuario = resenasRepository.findByUsuarioIdUsuario(idUsuario);

        return resenaByUsuario.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }


    @Override
    public ResenasResponse addResena(ResenaRequest resena) {
        Productos producto = productoRepository.findById(resena.getIdProducto()).orElseThrow(
                () -> new IllegalArgumentException("El producto con ID " + resena.getIdProducto() + " no existe.")
        );
        Usuarios usuario = usuariosRepository.findById(resena.getIdUsuario()).orElseThrow(
                () -> new IllegalArgumentException("El usuario con ID " + resena.getIdUsuario() + " no existe.")
        );
        Pedidos pedido = pedidosRepository.findById(resena.getIdPedido()).orElseThrow(
                () -> new IllegalArgumentException("El pedido con ID " + resena.getIdPedido() + " no existe.")
        );

        // Validar que el usuario compró el producto en ese pedido
        // Esto asume que el Pedido ya tiene una lista de DetallesPedido que vincula
        // el Producto y el Pedido.
        boolean haComprado = pedido.getDetallesPedidos().stream()
                .anyMatch(detalle -> detalle.getProducto().getIdProducto().equals(producto.getIdProducto()));

        if (!haComprado) {
            throw new IllegalArgumentException("El producto no forma parte del pedido " + resena.getIdPedido());
        }

        Resenas newResena = new Resenas();
        if(resena.getCalificacion() != null) newResena.setCalificacion(resena.getCalificacion());
        if(resena.getComentario() != null) newResena.setComentario(resena.getComentario());
        if(resena.getResenaVisible() != null) newResena.setResenaVisible(resena.getResenaVisible());

        newResena.setProducto(producto);
        newResena.setUsuario(usuario);
        newResena.setPedido(pedido);

        // Actualizar la calificación promedio del Producto
        //Añadir el campo calificacionPromedio a la entidad Productos.

        Resenas saveResena = resenasRepository.save(newResena);

        return mapToResponseDTO(saveResena);
    }

    @Override
    public ResenasResponse updateResenaById(Long id, ResenaRequest updateResena) {
        Optional<Resenas> optionalResena = resenasRepository.findById(id);
        if(optionalResena.isEmpty()) throw new IllegalArgumentException("La reseña no existe");

        // Obteniendo la reseña de la BD
        Resenas resenaDB = optionalResena.get();
        if(updateResena.getCalificacion() != null) resenaDB.setCalificacion(updateResena.getCalificacion());
        if(updateResena.getComentario() != null) resenaDB.setComentario(updateResena.getComentario());
        if(updateResena.getResenaVisible() != null) resenaDB.setResenaVisible(updateResena.getResenaVisible());

        if (updateResena.getIdProducto() != null) {
            Productos producto = productoRepository.findById(updateResena.getIdProducto())
                    .orElseThrow(() -> new IllegalArgumentException("El producto con ID " + updateResena.getIdProducto() + " no existe."));
            resenaDB.setProducto(producto);
        }

        if (updateResena.getIdUsuario() != null) {
            Usuarios usuario = usuariosRepository.findById(updateResena.getIdUsuario())
                    .orElseThrow(() -> new IllegalArgumentException("El usuario con ID " + updateResena.getIdUsuario() + " no existe."));
            resenaDB.setUsuario(usuario);
        }

        if (updateResena.getIdPedido() != null) {
            Pedidos pedido = pedidosRepository.findById(updateResena.getIdPedido())
                    .orElseThrow(() -> new IllegalArgumentException("El pedido con ID " + updateResena.getIdPedido() + " no existe."));
            resenaDB.setPedido(pedido);
        }

        Resenas saveResena = resenasRepository.save(resenaDB);

        return mapToResponseDTO(saveResena);
    }

    @Override
    public void ocultarResenaById(Long id) {
        Resenas resena = resenasRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("La reseña con el id: " + id + " no existe")
        );

        // Ocultar la reseña
        if(resena.getResenaVisible()){
            resena.setResenaVisible(false);
            resenasRepository.save(resena);

            //Llama a la funcion para recalcular el promedio [implementar si es necesario].

        } else {
            throw new IllegalArgumentException("La reseña con ID " + id + " ya está oculta.");
        }
    }

    @Override
    public void mostrarResenaById(Long id) {
        Resenas resena = resenasRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Reseña no encontrada con ID: " + id)
        );

        if (!resena.getResenaVisible()) {
            resena.setResenaVisible(true);
            resenasRepository.save(resena);

            //Llama a la funcion para recalcular el promedio [implementar si es necesario].

        } else {
            throw new IllegalArgumentException("La reseña con ID " + id + " ya está visible.");
        }
    }

    public ResenasResponse mapToResponseDTO(Resenas resena) {
        ResenasResponse dto = new ResenasResponse();

        dto.setIdResena(resena.getIdResena());
        dto.setCalificacion(resena.getCalificacion());
        dto.setComentario(resena.getComentario());
        dto.setFechaCreacion(resena.getFechaResena());

        if(resena.getProducto() != null){
            ProductoResponseLite proDto = new ProductoResponseLite();
            proDto.setIdProducto(resena.getProducto().getIdProducto());
            proDto.setProducto(resena.getProducto().getProducto());

            dto.setProducto(proDto);
        }

        if(resena.getUsuario() != null){
            UsuarioResponseLite usuDto = new UsuarioResponseLite();
            usuDto.setNombre(resena.getUsuario().getNombre());

            dto.setUsuario(usuDto);
        }

        if(resena.getPedido() != null){
            PedidoResponseLite peDto = new PedidoResponseLite();
            peDto.setIdPedido(resena.getPedido().getIdPedido());

            dto.setPedido(peDto);
        }

        return dto;
    }
}