package org.generation.muebleria.controller;

import lombok.AllArgsConstructor;
import org.generation.muebleria.dto.request.CategoriaRequest;
import org.generation.muebleria.dto.response.CategoriaResponse;
import org.generation.muebleria.service.interfaces.ICategoriaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="/api/categorias")
@AllArgsConstructor
public class CategoriaController {

    private final ICategoriaService categoriaService;

    // [GET: USUARIO] -> url -> /api/categorias
    @GetMapping
    public List<CategoriaResponse> getAllActiveCategorias(){
        return categoriaService.getAllCategoriasActive();
    }

    // [GET: USUARIO] -> url -> /api/categorias/{categoriaId}
    @GetMapping(path = "/{categoriaId}")
    public Optional<CategoriaResponse> getCategoryById(@PathVariable("categoriaId")Long id){
        return categoriaService.getCategoriaById(id);
    }

    // [GET: ADMIN] -> url -> /api/categorias/admin/todos
    @GetMapping(path = "/admin/todos")
    public List<CategoriaResponse> getAllCategorias(){
        return categoriaService.getAllCategorias();
    }

    // [POST: ADMIN] -> url -> /api/categorias/admin/add
    @PostMapping(path = "/admin/add")
    public CategoriaResponse addCategory(@RequestBody CategoriaRequest categoria){
        return categoriaService.addCategoria(categoria);
    }

    // [PUT: ADMIN] -> url -> /api/categorias/admin/update/{categoriaId}
    @PutMapping(path = "/admin/update/{categoriaId}")
    public CategoriaResponse updateCategory(@PathVariable("categoriaId")Long id, @RequestBody CategoriaRequest categoria){
        return categoriaService.updateCategoriaById(id, categoria);
    }

    // [DELTE: ADMIN] -> url -> /api/categorias/admin/desactivar/{categoriaId}
    @DeleteMapping(path = "/admin/desactivar/{categoriaId}")
    public void desactivarCategoria(@PathVariable("categoriaId")Long id){
        categoriaService.desactivarCategoriaById(id);
    }

    // [DELETE: ADMIN] -> url -> /api/categorias/admin/activar/{categoriaId}
    @DeleteMapping(path = "/admin/activar/{categoriaId}")
    public void activarCategoria(@PathVariable("categoriaId") Long id){
        categoriaService.activarCategoriaById(id);
    }

}
