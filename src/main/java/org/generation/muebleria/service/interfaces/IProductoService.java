package org.generation.muebleria.service.interfaces;

import org.generation.muebleria.dto.request.ProductoRequest;
import org.generation.muebleria.dto.response.ProductoResponse;
import org.generation.muebleria.model.Productos;

import java.util.List;
import java.util.Optional;

public interface IProductoService {
    //trae los productos activos
    ProductoResponse mapToResponseDTO(Productos producto);

    List<ProductoResponse> getAllProductsActive();
    List<ProductoResponse> getActiveProductosByCategoriaId(Long categoriaId);
    List<ProductoResponse> getActiveProductosByProveedorId(Long proveedorId);
    List<ProductoResponse> getProductosByCategoriaAndProveedor(Long categoriaId, Long proveedorId);
    //trae todos los productos activos e inactivos
    List<ProductoResponse> getAllProducts();
    //trae productos por Id
    Optional<ProductoResponse> getProductsById(Long id);
    //agregar producto
    ProductoResponse addProduct(ProductoRequest productoDto);
    //actualizar producto
    ProductoResponse updateProductsById(Long id, ProductoRequest updateProductDto);
    //borrar producto o en este caso desactivar
    void desactivarProductsById(Long id);
    void activarProductsById(Long id);
}
