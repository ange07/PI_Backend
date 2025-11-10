package org.generation.muebleria.service.interfaces;

import org.generation.muebleria.dto.request.ResenaRequest;
import org.generation.muebleria.dto.response.ResenasResponse;
import org.generation.muebleria.model.Resenas;
import java.util.List;
import java.util.Optional;

public interface IResenasService {

    // Trae las reseñas visibles
    List<ResenasResponse> getAllResenasVisible();

    // Trae todas las reseñas visibles y ocultas
    List<ResenasResponse> getAllResenas();

    // Trae reseña por Id
    Optional<ResenasResponse> getResenaById(Long id);

    // Trae todas las reseñas de un producto específico
    List<ResenasResponse> getResenasByProducto(Long idProducto);

    // Trae todas las reseñas de un usuario específico
    List<ResenasResponse> getResenasByUsuario(Long idUsuario);

    // Agregar reseña
    ResenasResponse addResena(ResenaRequest resenasRequest);

    // Actualizar reseña
    ResenasResponse updateResenaById(Long id, ResenaRequest updateResenasRequest);

    // Ocultar reseña
    void ocultarResenaById(Long id);

    // Mostrar reseña
    void mostrarResenaById(Long id);

}