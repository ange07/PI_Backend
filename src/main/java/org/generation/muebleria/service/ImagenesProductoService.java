package org.generation.muebleria.service;

import lombok.AllArgsConstructor;
import org.generation.muebleria.model.ImagenesProducto;
import org.generation.muebleria.repository.ImagenesProductoRepository;
import org.generation.muebleria.service.interfaces.IImagenesProductoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor // Inyecta el repositorio automáticamente
public class ImagenesProductoService implements IImagenesProductoService {

    // Inyección de dependencia
    public ImagenesProductoRepository imagenesProductoRepository;

    @Override
    public List<ImagenesProducto> getImagesByProductId(Integer idProducto) {
        return imagenesProductoRepository.findByProductoIdProducto(idProducto);
    }

    @Override
    public ImagenesProducto getImageById(Integer id) {
        return imagenesProductoRepository.findById(id).orElse(null);
    }

    @Override
    public ImagenesProducto createImage(ImagenesProducto imagen) {
        // Aquí, el objeto 'imagen' que recibas ya debe tener
        // el objeto 'Producto' anidado para que se guarde la relación.
        return imagenesProductoRepository.save(imagen);
    }

    @Override
    public void deleteImageById(Integer id) {
        // Esta tabla no tiene borrado lógico (soft delete),
        // así que la eliminamos físicamente.
        imagenesProductoRepository.deleteById(id);
    }
}