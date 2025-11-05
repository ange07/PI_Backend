package org.generation.muebleria.service;

import lombok.AllArgsConstructor;
import org.generation.muebleria.model.Resenas;
import org.generation.muebleria.repository.ResenasRepository;
import org.generation.muebleria.service.interfaces.IResenasService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class ResenasService implements IResenasService {

    // Inyección de dependencia
    private ResenasRepository resenasRepository;

    // Implementación de los métodos de la interface (IResenasService)
    @Override
    public List<Resenas> getAllResenasActive() {
        return resenasRepository.findByActivoTrue();
    }

    @Override
    public Resenas getResenasById(Integer id) {
        return resenasRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reseña no encontrada con id: " + id));
    }

    @Override
    public Resenas createResenas(Resenas resenas) {
        resenas.setActivo(true);
        return resenasRepository.save(resenas);
    }

    @Override
    public Resenas updateResenasById(Integer id, Resenas updateResenas) {
        Resenas resenaExistente = getResenasById(id);

        if (updateResenas.getCalificacion() != null) {
            resenaExistente.setCalificacion(updateResenas.getCalificacion());
        }
        if (updateResenas.getComentario() != null) {
            resenaExistente.setComentario(updateResenas.getComentario());
        }
        if (updateResenas.getResenaVisible() != null) {
            resenaExistente.setResenaVisible(updateResenas.getResenaVisible());
        }

        return resenasRepository.save(resenaExistente);
    }

    @Override
    public Resenas deleteResenasById(Integer id) {
        Resenas resena = getResenasById(id);
        resena.setActivo(false);
        return resenasRepository.save(resena);
    }
}