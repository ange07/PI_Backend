package org.generation.muebleria.service;

import lombok.AllArgsConstructor;
import org.generation.muebleria.dto.request.ProductoRequest;
import org.generation.muebleria.dto.response.ProductoResponse;
import org.generation.muebleria.dto.responseLite.CategoriaResponseLite;
import org.generation.muebleria.dto.responseLite.ProveedorResponseLite;
import org.generation.muebleria.model.Categorias;
import org.generation.muebleria.model.Productos;
import org.generation.muebleria.model.Proveedores;
import org.generation.muebleria.repository.CategoriaRepository;
import org.generation.muebleria.repository.ProductoRepository;
import org.generation.muebleria.repository.ProveedoresRepository;
import org.generation.muebleria.service.interfaces.IProductoService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class ProductoService implements IProductoService {

    //inyeccion de dependencia
    public ProductoRepository productoRepository;
    public CategoriaRepository categoriaRepository;
    public ProveedoresRepository proveedoresRepository;


    @Override
    public List<ProductoResponse> getAllProductsActive() {
        List<Productos> productosActivos = productoRepository.findByActivoTrueWithCategoriasAndProveedores();

        return productosActivos.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductoResponse> getActiveProductosByCategoriaId(Long categoriaId){
        categoriaRepository.findById(categoriaId).orElseThrow(
                () -> new IllegalArgumentException("La categoria con el ID "+categoriaId+" no existe")
        );

        List<Productos> productoByCategoria = productoRepository.findActiveByCategoriaId(categoriaId);
        return productoByCategoria.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductoResponse> getActiveProductosByProveedorId(Long proveedorId){
        proveedoresRepository.findById(proveedorId).orElseThrow(
                () -> new IllegalArgumentException("El proveedor con el ID "+proveedorId+" no existe")
        );
        List<Productos> productoByProv = productoRepository.findActiveByProveedorId(proveedorId);

        return productoByProv.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductoResponse> getProductosByCategoriaAndProveedor(Long categoriaId, Long proveedorId){
        if(categoriaId == null && proveedorId == null){
            return getAllProductsActive();
        }

        List<Productos> filter = productoRepository.filterActiveByCategoriaAndProveedor(categoriaId, proveedorId);
        return filter.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductoResponse> getAllProducts() {
        List<Productos> productos = productoRepository.findAll();
        return productos.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ProductoResponse> getProductsById(Long id) {
        Optional<Productos> productosById = Optional.ofNullable(productoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("El producto con el id: " + id + " no existe")
        ));

        return productosById.map(this::mapToResponseDTO);
    }

    @Override
    public ProductoResponse addProduct(ProductoRequest producto) {
        Categorias categoria = categoriaRepository.findById(producto.getIdCategoria()).orElseThrow(
                () -> new IllegalArgumentException("La categoría con ID " + producto.getIdCategoria() + " no existe.")
        );
        Proveedores proveedor = proveedoresRepository.findById(producto.getIdProveedor()).orElseThrow(
                () -> new IllegalArgumentException("El proveedor con ID " + producto.getIdProveedor() + " no existe.")
        );

        Productos newProducto = new Productos();
        if(producto.getProducto()!= null) newProducto.setProducto(producto.getProducto());
        if(producto.getDescripcion() != null) newProducto.setDescripcion(producto.getDescripcion());
        if(producto.getPrecioActual() != null) newProducto.setPrecioActual(producto.getPrecioActual());
        if(producto.getStockDisponible() != null) newProducto.setStockDisponible(producto.getStockDisponible());

        newProducto.setCategoria(categoria);
        newProducto.setProveedor(proveedor);

        //guardamos en la tabla Productos
        Productos saveProducto = productoRepository.save(newProducto);

        //mapeamos la entidad guardada a DTO
        return mapToResponseDTO(saveProducto);
    }

    @Override
    public ProductoResponse updateProductsById(Long id, ProductoRequest updateProduct) {
        Optional<Productos> optionalProduct = productoRepository.findById(id);
        if(optionalProduct.isEmpty()) throw new IllegalArgumentException("El producto no existe");
        //obteniendo el producto de la bd
        Productos productDB = optionalProduct.get();
        if(updateProduct.getProducto() != null) productDB.setProducto(updateProduct.getProducto());
        if(updateProduct.getDescripcion() != null) productDB.setDescripcion(updateProduct.getDescripcion());
        if(updateProduct.getPrecioActual() != null) productDB.setPrecioActual(updateProduct.getPrecioActual());
        if(updateProduct.getStockDisponible() != null) productDB.setStockDisponible(updateProduct.getStockDisponible());

        if (updateProduct.getIdCategoria() != null) {
            Categorias categoria = categoriaRepository.findById(updateProduct.getIdCategoria())
                    .orElseThrow(() -> new IllegalArgumentException("La categoría con ID " + updateProduct.getIdCategoria() + " no existe."));
            productDB.setCategoria(categoria);
        }

        if (updateProduct.getIdProveedor() != null) {
            Proveedores proveedor = proveedoresRepository.findById(updateProduct.getIdProveedor())
                    .orElseThrow(() -> new IllegalArgumentException("El proveedor con ID " + updateProduct.getIdProveedor() + " no existe."));
            productDB.setProveedor(proveedor);
        }

        Productos saveProducto = productoRepository.save(productDB);

        return mapToResponseDTO(saveProducto);
    }

    @Override
    public void desactivarProductsById(Long id) {
        Productos producto = productoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("El producto con el id: " + id + " no existe")
        );

        //desactivar el producto con Activo
        if(producto.getActivo()){
            producto.setActivo(false);
            productoRepository.save(producto);
        }else {
            throw new IllegalArgumentException("El producto con ID " + id + " ya está inactivo.");
        }

    }

    @Override
    public void activarProductsById(Long id) {
        Productos producto = productoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("El producto con el id: " + id + " no existe")
        );

        //activar producto
        if(producto.getActivo() == null || !producto.getActivo()){
            producto.setActivo(true);
            productoRepository.save(producto);
        }else{
            throw new IllegalArgumentException("El producto con ID " + id + " ya está activo.");
        }
    }

    //mapeo del DTO como respuesta en Postman [Personalizado]
    @Override
    public ProductoResponse mapToResponseDTO(Productos producto){
        ProductoResponse dto = new ProductoResponse();

        //mapeo de los campos de los productos
        dto.setIdProducto(producto.getIdProducto());
        dto.setProducto(producto.getProducto());
        dto.setDescripcion(producto.getDescripcion());
        dto.setPrecioActual(producto.getPrecioActual());
        dto.setStockDisponible(producto.getStockDisponible());
        dto.setActivo(producto.getActivo());
        dto.setActivoPorDependencia(producto.getActivo_por_dependencia());
        dto.setFechaPedido(producto.getFechaPedido());
        dto.setFechaActualizacion(producto.getFechaActualizacion());

        //mapeo de Categoria usando el DTO lite
        if (producto.getCategoria() != null) {
            CategoriaResponseLite catDto = new CategoriaResponseLite();
            catDto.setIdCategoria(producto.getCategoria().getIdCategoria());
            catDto.setNombreCategoria(producto.getCategoria().getNombreCategoria());
            catDto.setActivo(producto.getCategoria().getActivo());

            // Manejar el ID del padre (autobucle)
            if (producto.getCategoria().getCategoriaPadre() != null) {
                catDto.setIdCategoriaPadre(producto.getCategoria().getCategoriaPadre().getIdCategoria());
            }
            dto.setCategoria(catDto);
        }

        // mapeo de Proveedor usando el DTO lite
        if (producto.getProveedor() != null) {
            ProveedorResponseLite provDto = new ProveedorResponseLite();
            provDto.setIdProveedor(producto.getProveedor().getIdProveedor());
            provDto.setNombreEmpresa(producto.getProveedor().getNombreEmpresa());
            provDto.setNombre(producto.getProveedor().getNombre());
            dto.setProveedor(provDto);
        }

        return dto;
    }
}
