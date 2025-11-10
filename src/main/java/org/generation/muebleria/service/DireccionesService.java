package org.generation.muebleria.service;

import lombok.AllArgsConstructor;
import org.generation.muebleria.dto.request.DireccionesRequest;
import org.generation.muebleria.dto.response.DireccionResponse;
import org.generation.muebleria.dto.response.DireccionesResponse;
import org.generation.muebleria.model.Direccion;
import org.generation.muebleria.model.Usuarios;
import org.generation.muebleria.repository.DireccionRepository;
import org.generation.muebleria.repository.UsuariosRepository;
import org.generation.muebleria.service.interfaces.IDireccionesService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DireccionesService implements IDireccionesService {

    private final DireccionRepository directionsRepository;
    private final UsuariosRepository usersRepository;
    private UsuarioService usuarioService;

    public DireccionesResponse addDirectionUser(DireccionesRequest directionsRequest){
        // Obtenemos el ID del DTO de petición y verificamos que el usuario exista
        Long userId = directionsRequest.getIdUsuario();
        Usuarios user = usersRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("No existe el usuario con el id "+userId)
        );

        //si existe, obtenemos las direccion del usuario
        Direccion direction = new Direccion();
        if(directionsRequest.getDireccion() != null) direction.setDireccion(directionsRequest.getDireccion());
        if(directionsRequest.getAlias() != null) direction.setAlias(directionsRequest.getAlias());
        if(directionsRequest.getCiudad() != null) direction.setCiudad(directionsRequest.getCiudad());
        if(directionsRequest.getEstado() != null) direction.setEstado(directionsRequest.getEstado());
        if(directionsRequest.getCodigoPostal()!= null) direction.setCodigoPostal(directionsRequest.getCodigoPostal());
        if(directionsRequest.getMunicipio() != null) direction.setMunicipio(directionsRequest.getMunicipio());
        if(directionsRequest.getTipoDireccion() != null) direction.setTipoDireccion(directionsRequest.getTipoDireccion());
        if(directionsRequest.getEsPredeterminada() != null) direction.setEsPredeterminada(directionsRequest.getEsPredeterminada() != null ? directionsRequest.getEsPredeterminada() : false);

        // Si la nueva direccion es predeterminada, desactivar las viejas
        if (direction.getEsPredeterminada()) {
            unsetPredeterminada(user);
        }
        //Asociando el usuario a la direccion
        direction.setUsuario(user);
        //guardando en la bd la direccion
        directionsRepository.save(direction);

        Direccion saveDireccion = directionsRepository.save(direction);

        //actualizando el usuario en la bd
        return  mapToResponseDTO(saveDireccion);
    }

    public List<DireccionesResponse> getDirectionsByUserId(Long userId){
        Usuarios user = usersRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("El usuario no existe"));

        return user.getDirecciones().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public DireccionesResponse updateDirection(Long userId,Long directionId, DireccionesRequest directionsRequest){
        if(!usersRepository.existsById(userId)) throw new IllegalArgumentException("No existe el usuario con el id " + userId);
        Direccion direction = directionsRepository.findById(directionId).orElseThrow(
                () ->  new IllegalArgumentException("No existe una direccion con el id " + directionId)
        );
        if(!direction.getUsuario().getIdUsuario().equals(userId))throw new IllegalArgumentException("El id del usuario no coincide con el de la direccion") ;
        // Direccion predeterminada en la actualización
        if (directionsRequest.getEsPredeterminada() != null && directionsRequest.getEsPredeterminada()) {
            unsetPredeterminada(direction.getUsuario());
            direction.setEsPredeterminada(true); // Se establece explícitamente a true
        } else if (directionsRequest.getEsPredeterminada() != null && !directionsRequest.getEsPredeterminada()) {
            direction.setEsPredeterminada(false); // Se establece explícitamente a false
        }
        if(directionsRequest.getDireccion() != null) direction.setDireccion(directionsRequest.getDireccion());
        if(directionsRequest.getAlias() != null) direction.setAlias(directionsRequest.getAlias());
        if(directionsRequest.getCiudad() != null) direction.setCiudad(directionsRequest.getCiudad());
        if(directionsRequest.getEstado() != null) direction.setEstado(directionsRequest.getEstado());
        if(directionsRequest.getCodigoPostal()!= null) direction.setCodigoPostal(directionsRequest.getCodigoPostal());
        if(directionsRequest.getMunicipio() != null) direction.setMunicipio(directionsRequest.getMunicipio());
        if(directionsRequest.getTipoDireccion() != null) direction.setTipoDireccion(directionsRequest.getTipoDireccion());

        Direccion updatedDirection = directionsRepository.save(direction);

        return mapToResponseDTO(updatedDirection);
    }

    public DireccionesResponse getDirectionById(Long userId, Long directionId){
        if(!usersRepository.existsById(userId)) throw new IllegalArgumentException("No existe el usuario con el id " + userId);
        Direccion direction = directionsRepository.findById(directionId).orElseThrow(
                () ->  new IllegalArgumentException("No existe una direccion con el id " + directionId)
        );
        if(!direction.getUsuario().getIdUsuario().equals(userId))throw new IllegalArgumentException("El id del usuario no coincide con el de la direccion") ;

        return mapToResponseDTO(direction);
    }

    public void deleteDirectionById(Long userId, Long directionId){
        if(!usersRepository.existsById(userId)) throw new IllegalArgumentException("No existe el usuario con el id " + userId);
        Direccion direction = directionsRepository.findById(directionId).orElseThrow(
                () ->  new IllegalArgumentException("No existe una direccion con el id " + directionId)
        );
        if(!direction.getUsuario().getIdUsuario().equals(userId))throw new IllegalArgumentException("El id del usuario no coincide con el de la direccion") ;

        directionsRepository.deleteById(directionId);
    }

    // ---auxiliar para manejar 'esPredeterminada' ---
    private void unsetPredeterminada(Usuarios user) {
        List<Direccion> predeterminadas = directionsRepository.findByUsuarioAndEsPredeterminadaTrue(user);
        for (Direccion d : predeterminadas) {
            d.setEsPredeterminada(false);
            directionsRepository.save(d);
        }
    }

    public DireccionesResponse mapToResponseDTO(Direccion direccion) {
        if (direccion == null) return null;
        DireccionesResponse dto = new DireccionesResponse();

        dto.setIdDireccion(direccion.getIdDireccion());
        dto.setTipoDireccion(direccion.getTipoDireccion());
        dto.setAlias(direccion.getAlias());
        dto.setDireccion(direccion.getDireccion());
        dto.setCiudad(direccion.getCiudad());
        dto.setEstado(direccion.getEstado());
        dto.setMunicipio(direccion.getMunicipio());
        dto.setCodigoPostal(direccion.getCodigoPostal());
        dto.setEsPredeterminada(direccion.getEsPredeterminada());

        // Mapear la relación del Usuario (usando el DTO Lite)
        if (direccion.getUsuario() != null) {
            // Asumiendo que esta es una llamada al método del UsuarioService
            dto.setUsuario(usuarioService.mapToLiteDTO(direccion.getUsuario()));
        }

        return dto;
    }
}
