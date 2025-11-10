package org.generation.muebleria.controller;

import lombok.AllArgsConstructor;
import org.generation.muebleria.dto.request.ProductoRequest;
import org.generation.muebleria.dto.response.ProductoResponse;
import org.generation.muebleria.model.Productos;
import org.generation.muebleria.service.interfaces.IProductoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="/api/productos")
@AllArgsConstructor
public class ProductoController {
    private final IProductoService productoService;

    //[GET: USUARIO] -> url -> /api/productos
    @GetMapping
    public List<ProductoResponse> getAllProductsActive(){
        return productoService.getAllProductsActive();
    }

    //[GET: USUARIO] -> url -> /api/productos/categoria/{categoriaId}
    @GetMapping(path="/categoria/{categoriaId}")
    public List<ProductoResponse> getProductosActiveByCategoria(@PathVariable("categoriaId")Long id){
        return productoService.getActiveProductosByCategoriaId(id);
    }

    //[GET: USUARIO] -> url -> /api/productos/proveedor/{proveedorId}
    @GetMapping(path = "/proveedor/{proveedorId}")
    public List<ProductoResponse> getProductosActiveByProveedor(@PathVariable("proveedorId")Long id){
        return productoService.getActiveProductosByProveedorId(id);
    }

    //[GET: USUARIO] -> url -> /api/productos/{productId}
    @GetMapping(path = "/{productId}")
    public Optional<ProductoResponse> getProductsById(@PathVariable("productId")Long id){
        return productoService.getProductsById(id);
    }

    //[GET: ADMIN] -> url -> /api/productos/admin/todos
    @GetMapping(path="/admin/todos")
    public List<ProductoResponse> getAllProducts(){
        return productoService.getAllProducts();
    }

    //[GET:ADMIN] -> url -> /api/productos/admin/filter?catId=C&provId=P
    @GetMapping(path = "/admin/filter")
    public List<ProductoResponse> getProductosByCategoriaAndProveedor(@RequestParam(required = false)Long catId, @RequestParam(required = false)Long provId){
        return productoService.getProductosByCategoriaAndProveedor(catId,provId);
    }

    //[POST: ADMIN] -> url -> /api/productos/admin/add
    @PostMapping(path="/admin/add")
    public ProductoResponse addPorduct(@RequestBody ProductoRequest product){
        return productoService.addProduct(product);
    }

    //[PUT: ADMIN] -> url -> /api/productos/admin/update/{productId}
    @PutMapping(path ="/admin/update/{productId}")
    public ProductoResponse updateProductsById(@PathVariable("productId")Long id, @RequestBody ProductoRequest product){
        return productoService.updateProductsById(id,product);
    }

    //[DELTE: ADMIN] -> url -> /api/productos/admin/desactivar/{productoId}
    @DeleteMapping(path="/admin/desactivar/{productId}")
    public void desactivarProductsById(@PathVariable("productId")Long id){
        productoService.desactivarProductsById(id);
    }

    //[DELTE: ADMIN] -> url -> /api/productos/admin/activar/{productoId}
    @DeleteMapping(path="/admin/activar/{productId}")
    public void activarProductsById(@PathVariable("productId")Long id){
        productoService.activarProductsById(id);
    }
}
