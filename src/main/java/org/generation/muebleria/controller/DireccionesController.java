package org.generation.muebleria.controller;

import lombok.AllArgsConstructor;
import org.generation.muebleria.dto.request.DireccionesRequest;
import org.generation.muebleria.dto.response.DireccionesResponse;
import org.generation.muebleria.model.Direccion;
import org.generation.muebleria.model.Usuarios;
import org.generation.muebleria.service.DireccionesService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api/directions")
@AllArgsConstructor
public class DireccionesController {
    private final DireccionesService directionsService;

    @PostMapping
    public DireccionesResponse addDirectionUser(@RequestBody DireccionesRequest direction){
        return directionsService.addDirectionUser(direction);
    }

    @GetMapping
    public List<DireccionesResponse> getDirectionsByUserId(@RequestParam(required = true) Long userId){
        return this.directionsService.getDirectionsByUserId(userId);
    }

    @GetMapping(path = "/{id}")
    public DireccionesResponse getDirectionById(@RequestParam(required = true) Long userId, @RequestParam(required = true) Long directionId){
        return this.directionsService.getDirectionById(userId, directionId);
    }

    @PutMapping
    public DireccionesResponse updateDirection(@RequestParam(required = true) Long userId, @RequestParam(required = true) Long directionId,@RequestBody DireccionesRequest direction){
        return  directionsService.updateDirection(userId,directionId, direction);
    }

    @DeleteMapping
    public void deleteDirectionById(@RequestParam(required = true) Long userId, @RequestParam(required = true) Long directionId){
        directionsService.deleteDirectionById(userId,directionId);
    }
}
