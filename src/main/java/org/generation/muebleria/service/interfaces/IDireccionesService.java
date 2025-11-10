package org.generation.muebleria.service.interfaces;

import org.generation.muebleria.dto.request.DireccionesRequest;
import org.generation.muebleria.dto.response.DireccionesResponse;

import java.util.List;

public interface IDireccionesService {

    DireccionesResponse addDirectionUser(DireccionesRequest directionRequest);

    List<DireccionesResponse> getDirectionsByUserId(Long userId);

    DireccionesResponse updateDirection(Long userId, Long directionId, DireccionesRequest directionsRequest);

    DireccionesResponse getDirectionById(Long userId, Long directionId);

    void deleteDirectionById(Long userId, Long directionId);
}
