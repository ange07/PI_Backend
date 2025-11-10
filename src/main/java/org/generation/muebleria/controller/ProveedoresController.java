package org.generation.muebleria.controller;

import lombok.AllArgsConstructor;
import org.generation.muebleria.dto.request.ProveedorRequest;
import org.generation.muebleria.dto.response.ProveedorResponse;
import org.generation.muebleria.model.Proveedores;
import org.generation.muebleria.service.ProveedoresService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/proveedores")
@AllArgsConstructor
public class ProveedoresController {

    private final ProveedoresService proveedoresService;

    // [GET: USUARIO] -> url -> /api/proveedores
    @GetMapping
    public List<ProveedorResponse> getAllActiveProviders(){
        return proveedoresService.getProveedoresActivos();
    }

    // [GET: USUARIO] -> url -> /api/proveedores/{id}
    @GetMapping(path = "/{id}")
    public Optional<ProveedorResponse> getProviderById(@PathVariable("id") Long id){
        return proveedoresService.getProveedorById(id);
    }

    // [GET: ADMIN] -> url -> /api/proveedores/admin/todos
    @GetMapping(path = "/admin/todos")
    public List<ProveedorResponse> getAllProveedores(){
        return proveedoresService.getAllProveedores();
    }

    // [POST: ADMIN] -> url -> /api/proveedores/admin/add
    @PostMapping(path = "/admin/add")
    public ProveedorResponse addProvider(@RequestBody ProveedorRequest proveedor){
        return proveedoresService.addProveedor(proveedor);
    }

    // [PUT:ADMIN] -> url -> /api/proveedores/admin/update/{id}
    @PutMapping(path = "/admin/update/{id}")
    public ProveedorResponse updateProvider(@PathVariable("id") Long id, @RequestBody ProveedorRequest proveedor){
        return proveedoresService.updateProveedor(id, proveedor);
    }

    // DELETE /api/proveedores/admin/desactivar/{id} (Borrado suave)
    @DeleteMapping(path = "/admin/desactivar/{id}")
    public void desactivarProvider(@PathVariable("id") Long id){
        proveedoresService.desactivarProveedorById(id);
    }

    // PUT /api/proveedores/admin/activate/{id}
    @DeleteMapping(path = "/admin/activate/{id}")
    public void activateProvider(@PathVariable Long id){
        proveedoresService.activarProveedorById(id);
    }
}