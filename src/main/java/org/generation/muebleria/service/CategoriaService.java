package org.generation.muebleria.service;

import lombok.AllArgsConstructor;
import org.generation.muebleria.dto.request.CategoriaRequest;
import org.generation.muebleria.dto.response.CategoriaResponse;
import org.generation.muebleria.model.Categorias;
import org.generation.muebleria.model.Productos;
import org.generation.muebleria.repository.CategoriaRepository;
import org.generation.muebleria.service.interfaces.ICategoriaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor // Inyecta el repositorio automáticamente
public class CategoriaService implements ICategoriaService {

    public CategoriaRepository categoriaRepository;

    @Override
    public List<CategoriaResponse> getAllCategoriasActive() {
        List<Categorias> categoriasActivas = categoriaRepository.findByActivoTrue();

        return categoriasActivas.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoriaResponse> getAllCategorias() {
        List<Categorias> categorias = categoriaRepository.findAll();

        return categorias.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CategoriaResponse> getCategoriaById(Long id) {
        Optional<Categorias> categoriasById = Optional.ofNullable(categoriaRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("La categoria con el id: " + id + " no existe")
        ));

        return categoriasById.map(this::mapToResponseDTO);
    }

    private Optional<Categorias> findPadre(Long idPadre){
        return (idPadre != null) ? categoriaRepository.findById(idPadre) : Optional.empty();
    }

    @Override
    public CategoriaResponse addCategoria(CategoriaRequest categoria) {
        if(categoriaRepository.findByNombreCategoria(categoria.getNombreCategoria()).isPresent()){
            throw new IllegalArgumentException("Ya existe una categoria con ese nombre");
        }
        Categorias nuevaCategoria = new Categorias();
        nuevaCategoria.setNombreCategoria(categoria.getNombreCategoria());

        findPadre(categoria.getIdCategoriaPadre()).ifPresent(nuevaCategoria::setCategoriaPadre);

        Categorias saveCategoria = categoriaRepository.save(nuevaCategoria);

        return mapToResponseDTO(saveCategoria);
    }

    @Override
    public CategoriaResponse updateCategoriaById(Long id, CategoriaRequest categoriaActualizada) {
        // 1. Buscamos la categoría existente por ID
        Categorias categoriaExistente = categoriaRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Categoria no encontrada")
        );

        if (categoriaActualizada.getNombreCategoria() != null) {
            // Re-validar unicidad, asegurando que no sea el mismo ID
            Optional<Categorias> byName = categoriaRepository.findByNombreCategoria(categoriaActualizada.getNombreCategoria());
            if (byName.isPresent() && !byName.get().getIdCategoria().equals(id)) {
                throw new IllegalArgumentException("Ya existe otra categoría con ese nombre.");
            }
            categoriaExistente.setNombreCategoria(categoriaActualizada.getNombreCategoria());
        }

        // Actualizar relación padre
        if (categoriaActualizada.getIdCategoriaPadre() != null) {
            Categorias padre = categoriaRepository.findById(categoriaActualizada.getIdCategoriaPadre())
                    .orElseThrow(() -> new IllegalArgumentException("Categoría padre no encontrada."));

            // Buena práctica: evitar que una categoría sea su propia padre o forme un ciclo
            if (padre.getIdCategoria().equals(id)) {
                throw new IllegalArgumentException("Una categoría no puede ser su propia padre.");
            }
            categoriaExistente.setCategoriaPadre(padre);
        }

        // Actualizar estado activo
        if (categoriaActualizada.getActivo() != null) {
            categoriaExistente.setActivo(categoriaActualizada.getActivo());
        }

        Categorias saveCategoria = categoriaRepository.save(categoriaExistente);

        return mapToResponseDTO(saveCategoria);
    }

    @Override
    public void desactivarCategoriaById(Long id) {
        Categorias categoria = categoriaRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Categoría no encontrada con ID: " + id)
        );
        if (categoria.getActivo()) {
            categoria.setActivo(false);
            for(Productos producto : categoria.getProductos()){
                if(producto.getActivo()){
                    // ([categoria = false] -> [productos en relacion a la categoria = false])
                    producto.setActivo(false);
                    //Esto indica si fue desactivado por dependencia [true]
                    //fue desactivado porque la [categoria] a la cual pertenece esta desactivada
                    producto.setActivo_por_dependencia(true);
                }
            }
            categoriaRepository.save(categoria);
        }else {
            throw new IllegalArgumentException("La categoria ya esta desactivada");
        }
    }

    @Override
    public void activarCategoriaById(Long id) {
        Categorias categoria = categoriaRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Categoría no encontrada con ID: " + id)
        );
        if (!categoria.getActivo()) {
            categoria.setActivo(true);
            for(Productos producto: categoria.getProductos()){
                //con esta condicion verificamos que el producto esta en [false] y la dependica en [true]
                if(!producto.getActivo() && producto.getActivo_por_dependencia()){
                    //aqui obtenemos el estatus del Proveedor
                    boolean proveedorActivo = producto.getProveedor().getActivo();
                    //aqui comprobamos que el la dependencia proveedor sea [true]
                    if(proveedorActivo){
                        //si proovedor es [true]
                        //producto se activa
                        producto.setActivo(true);
                        //la depedencia se desactiva
                        producto.setActivo_por_dependencia(false);
                    }
                }
            }
            categoriaRepository.save(categoria);
        }else{
            throw new IllegalArgumentException("La categoria ya esta activa");
        }
    }

    @Override
    public CategoriaResponse mapToResponseDTO(Categorias categoria){
        CategoriaResponse dto = new CategoriaResponse();

        dto.setIdCategoria(categoria.getIdCategoria());
        dto.setNombreCategoria(categoria.getNombreCategoria());
        dto.setActivo(categoria.getActivo());

        // Mapear el ID del Padre (si existe)
        if (categoria.getCategoriaPadre() != null) {
            // Obtenemos solo el ID del padre de la entidad relacionada
            dto.setIdCategoriaPadre(categoria.getCategoriaPadre().getIdCategoria());
        } else {
            dto.setIdCategoriaPadre(null);
        }

        return dto;
    }

}