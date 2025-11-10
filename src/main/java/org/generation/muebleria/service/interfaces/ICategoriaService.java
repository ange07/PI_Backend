package org.generation.muebleria.service.interfaces;

import org.generation.muebleria.dto.request.CategoriaRequest;
import org.generation.muebleria.dto.response.CategoriaResponse;
import org.generation.muebleria.model.Categorias;
import java.util.List;
import java.util.Optional;

public interface ICategoriaService {
    CategoriaResponse mapToResponseDTO(Categorias categorias);
    // Obtener todas las categorías activas
    List<CategoriaResponse> getAllCategoriasActive();
    //traer todos los productos activos e inactivos
    List<CategoriaResponse> getAllCategorias();
    // Obtener una categoría por ID
    Optional<CategoriaResponse> getCategoriaById(Long id);
    // Crear una nueva categoría
    CategoriaResponse addCategoria(CategoriaRequest categoria);
    // Actualizar una categoría
    CategoriaResponse updateCategoriaById(Long id, CategoriaRequest categoriaActualizada);

    void desactivarCategoriaById(Long id);
    void activarCategoriaById(Long id);
}