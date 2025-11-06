package org.generation.muebleria.service;

import lombok.AllArgsConstructor;
import org.generation.muebleria.model.Categorias;
import org.generation.muebleria.repository.CategoriaRepository;
import org.generation.muebleria.service.interfaces.ICategoriaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor // Inyecta el repositorio automáticamente
public class CategoriaService implements ICategoriaService {

    // Inyección de dependencia (gracias a @AllArgsConstructor)
    public CategoriaRepository categoriaRepository;

    @Override
    public List<Categorias> getAllCategoriasActive() {
        return categoriaRepository.findByActivoTrue();
    }

    @Override
    public List<Categorias> getRootCategoriasActive() {
        return categoriaRepository.findByCategoriaPadreIsNullAndActivoTrue();
    }

    @Override
    public List<Categorias> getSubCategoriasActive(Integer idPadre) {
        return categoriaRepository.findByCategoriaPadreIdCategoriaAndActivoTrue(idPadre);
    }

    @Override
    public Categorias getCategoriaById(Integer id) {
        // .orElse(null) devuelve la categoría si existe, o null si no
        return categoriaRepository.findById(id).orElse(null);
    }

    @Override
    public Categorias createCategoria(Categorias categoria) {
        // Aseguramos que la nueva categoría esté activa por defecto
        categoria.setActivo(true);
        return categoriaRepository.save(categoria);
    }

    @Override
    public Categorias updateCategoriaById(Integer id, Categorias categoriaActualizada) {
        // 1. Buscamos la categoría existente por ID
        Optional<Categorias> categoriaExistente = categoriaRepository.findById(id);

        // 2. Si no existe, devolvemos null
        if (categoriaExistente.isEmpty()) {
            return null;
        }

        // 3. Si existe, actualizamos los campos
        Categorias categoriaDb = categoriaExistente.get();
        categoriaDb.setNombreCategoria(categoriaActualizada.getNombreCategoria());
        categoriaDb.setCategoriaPadre(categoriaActualizada.getCategoriaPadre());
        categoriaDb.setActivo(categoriaActualizada.getActivo());

        // 4. Guardamos y devolvemos la categoría actualizada
        return categoriaRepository.save(categoriaDb);
    }

    @Override
    public Categorias deleteCategoriaById(Integer id) {
        // Esta es la implementación de "Soft Delete" (borrado lógico)
        // que tu ejemplo de ProductoService parece implicar.

        Optional<Categorias> categoriaOptional = categoriaRepository.findById(id);

        if (categoriaOptional.isEmpty()) {
            return null;
        }

        Categorias categoria = categoriaOptional.get();
        categoria.setActivo(false); // 1. Marcamos como inactivo

        return categoriaRepository.save(categoria); // 2. Guardamos el cambio
    }
}