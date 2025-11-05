package org.generation.muebleria.service.interfaces;

import org.generation.muebleria.model.Resenas;
import java.util.List;

public interface IResenasService {

    List<Resenas> getAllResenasActive();

    Resenas getResenasById(Integer id);
    Resenas createResenas(Resenas resenas);
    Resenas updateResenasById(Integer id, Resenas updateResenas);
    Resenas deleteResenasById(Integer id);
}