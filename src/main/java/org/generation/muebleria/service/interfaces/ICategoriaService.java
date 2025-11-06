package org.generation.muebleria.service.interfaces;

import org.generation.muebleria.model.Categorias;
import java.util.List;

public interface ICategoriaService {

    // Obtener todas las categorías activas
    List<Categorias> getAllCategoriasActive();

    // Obtener las categorías principales (sin padre)
    List<Categorias> getRootCategoriasActive();

    // Obtener las subcategorías (hijas) de un padre
    List<Categorias> getSubCategoriasActive(Integer idPadre);

    // Obtener una categoría por ID
    Categorias getCategoriaById(Integer id);

    // Crear una nueva categoría
    Categorias createCategoria(Categorias categoria);

    // Actualizar una categoría
    Categorias updateCategoriaById(Integer id, Categorias categoriaActualizada);

    // Borrar una categoría (lógica de "soft delete")
    Categorias deleteCategoriaById(Integer id);

}