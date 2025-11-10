package org.generation.muebleria.service;

import lombok.AllArgsConstructor;
import org.generation.muebleria.dto.request.ImagenProductoRequest;
import org.generation.muebleria.dto.response.ImagenProductoResponse;
import org.generation.muebleria.dto.responseLite.ProductoResponseLite;
import org.generation.muebleria.model.ImagenesProducto;
import org.generation.muebleria.model.Productos;
import org.generation.muebleria.repository.ImagenesProductoRepository;
import org.generation.muebleria.repository.ProductoRepository;
import org.generation.muebleria.service.interfaces.IImagenesProductoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor // Inyecta el repositorio automáticamente
public class ImagenesProductoService implements IImagenesProductoService {

    private final ImagenesProductoRepository imagenesProductoRepository;
    private final ProductoRepository productoRepository;

    @Override
    public List<ImagenProductoResponse> getAllImagenes() {
        return imagenesProductoRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ImagenProductoResponse> getImagenesByProductoId(Long idProducto) {
        productoRepository.findById(idProducto)
                .orElseThrow(() -> new IllegalArgumentException("El producto con ID " + idProducto + " no existe."));

        return imagenesProductoRepository.findByProductoIdProducto(idProducto).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ImagenProductoResponse> getImagenById(Long idImagen) {
        return imagenesProductoRepository.findById(idImagen)
                .map(this::mapToResponseDTO);
    }

    @Override
    public ImagenProductoResponse addImagen(ImagenProductoRequest imagenRequest) {
        Productos producto = productoRepository.findById(imagenRequest.getIdProducto())
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado."));

        ImagenesProducto nuevaImagen = new ImagenesProducto();
        nuevaImagen.setUrlImagen(imagenRequest.getUrlImagen());
        nuevaImagen.setProducto(producto);

        ImagenesProducto savedImagen = imagenesProductoRepository.save(nuevaImagen);
        return mapToResponseDTO(savedImagen);
    }

    @Override
    public ImagenProductoResponse updateImagen(Long idImagen, ImagenProductoRequest imagenRequest) {
        ImagenesProducto imagenExistente = imagenesProductoRepository.findById(idImagen)
                .orElseThrow(() -> new IllegalArgumentException("Imagen no encontrada con ID: " + idImagen));

        // Validación de que el producto no ha cambiado si se envía un idProducto diferente
        if (!imagenExistente.getProducto().getIdProducto().equals(imagenRequest.getIdProducto())) {
            throw new IllegalArgumentException("No se puede cambiar el producto asociado a una imagen existente.");
        }

        // Si solo se actualiza la URL
        if (imagenRequest.getUrlImagen() != null && !imagenRequest.getUrlImagen().isBlank()) {
            imagenExistente.setUrlImagen(imagenRequest.getUrlImagen());
        }

        ImagenesProducto updatedImagen = imagenesProductoRepository.save(imagenExistente);
        return mapToResponseDTO(updatedImagen);
    }

    @Override
    public void deleteImagen(Long idImagen) {
        if (!imagenesProductoRepository.existsById(idImagen)) {
            throw new IllegalArgumentException("La imagen con ID " + idImagen + " no existe.");
        }
        imagenesProductoRepository.deleteById(idImagen);
    }

    // --- Métodos de Mapeo ---
    private ProductoResponseLite mapToProductoLiteDTO(Productos producto) {
        if (producto == null) return null;
        ProductoResponseLite dto = new ProductoResponseLite();
        dto.setIdProducto(producto.getIdProducto());
        dto.setProducto(producto.getProducto());
        return dto;
    }

    private ImagenProductoResponse mapToResponseDTO(ImagenesProducto imagen) {
        if (imagen == null) return null;
        ImagenProductoResponse dto = new ImagenProductoResponse();
        dto.setIdImagen(imagen.getIdImagen());
        dto.setUrlImagen(imagen.getUrlImagen());

        if (imagen.getProducto() != null) {
            dto.setProducto(mapToProductoLiteDTO(imagen.getProducto()));
        }
        return dto;
    }
}