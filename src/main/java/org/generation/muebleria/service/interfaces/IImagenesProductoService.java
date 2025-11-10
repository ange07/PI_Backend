package org.generation.muebleria.service.interfaces;

import org.generation.muebleria.dto.request.ImagenProductoRequest;
import org.generation.muebleria.dto.response.ImagenProductoResponse;
import org.generation.muebleria.model.ImagenesProducto;
import java.util.List;
import java.util.Optional;

public interface IImagenesProductoService {

    // Obtener todas las imágenes (Admin)
    List<ImagenProductoResponse> getAllImagenes();

    // Obtener imágenes por producto (Público/Admin)
    List<ImagenProductoResponse> getImagenesByProductoId(Long idProducto);

    // Obtener una imagen por ID
    Optional<ImagenProductoResponse> getImagenById(Long idImagen);

    // Añadir una nueva imagen
    ImagenProductoResponse addImagen(ImagenProductoRequest imagenRequest);

    // Actualizar una imagen
    ImagenProductoResponse updateImagen(Long idImagen, ImagenProductoRequest imagenRequest);

    // Eliminar una imagen
    void deleteImagen(Long idImagen);

}