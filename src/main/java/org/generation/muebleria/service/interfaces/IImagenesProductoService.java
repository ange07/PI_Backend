package org.generation.muebleria.service.interfaces;

import org.generation.muebleria.model.ImagenesProducto;
import java.util.List;

public interface IImagenesProductoService {

    // Obtener todas las imágenes de un producto
    List<ImagenesProducto> getImagesByProductId(Integer idProducto);

    // Obtener una imagen específica por su ID
    ImagenesProducto getImageById(Integer id);

    // Guardar una nueva imagen (asociada a un producto)
    ImagenesProducto createImage(ImagenesProducto imagen);

    // Borrar una imagen por su ID
    void deleteImageById(Integer id);

}