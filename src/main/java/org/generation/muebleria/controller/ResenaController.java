package org.generation.muebleria.controller;

import lombok.AllArgsConstructor;
import org.generation.muebleria.dto.request.ResenaRequest;
import org.generation.muebleria.dto.response.ResenasResponse;
import org.generation.muebleria.service.interfaces.IResenasService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="/api/resenas")
@AllArgsConstructor
public class ResenaController {
    private final IResenasService resenasService;

    //[GET: USUARIO] -> url -> /api/resenas
    @GetMapping
    public List<ResenasResponse> getAllResenasVisible(){
        return resenasService.getAllResenasVisible();
    }

    //[GET: ADMIN] -> url -> /api/resenas/admin/todas
    @GetMapping(path="/admin/todas")
    public List<ResenasResponse> getAllResenas(){
        return resenasService.getAllResenas();
    }

    //[GET: ADMIN] -> url -> /api/resenas/admin/{id}
    @GetMapping(path = "/admin/{resenaId}")
    public Optional<ResenasResponse> getResenaById(@PathVariable("resenaId") Long id){
        return resenasService.getResenaById(id);
    }

    //[GET: ADMIN] -> url -> /api/resenas/admin/producto/{productoId}
    @GetMapping(path = "/admin/producto/{productoId}")
    public List<ResenasResponse> getResenasByPedido(@PathVariable("productoId") Long idPedido){
        return resenasService.getResenasByProducto(idPedido);
    }

    //[GET: ADMIN] -> url -> /api/resenas/admin/usuario/{id}
    @GetMapping(path = "/admin/usuario/{usuarioId}")
    public List<ResenasResponse> getResenasByUsuario(@PathVariable("usuarioId") Long idUsuario){
        return resenasService.getResenasByUsuario(idUsuario);
    }

    //[POST: USUARIO] -> url -> /api/resenas/add
    @PostMapping(path = "/add")
    public ResenasResponse addResena(@RequestBody ResenaRequest resena){
        return  resenasService.addResena(resena);
    }

    //[PUT: USUARIO] -> url -> /api/resenas/update/{resenaId}
    @PutMapping(path ="/admin/update/{resenaId}")
    public ResenasResponse updateResenaById(@PathVariable("resenaId") Long id, @RequestBody ResenaRequest resena){
        return resenasService.updateResenaById(id, resena);
    }

    //[DELETE:ADMIN] -> url -> /api/resenas/admin/ocultar/{resenaId}
    @DeleteMapping(path="/admin/ocultar/{resenaId}")
    public void ocultarResenaById(@PathVariable("resenaId") Long id){
        resenasService.ocultarResenaById(id);
    }

    //[DELETE: ADMIN] -> url -> /api/resenas/admin/mostrar/{resenaId}
    @DeleteMapping(path="/admin/mostrar/{resenaId}")
    public void mostrarResenaById(@PathVariable("resenaId") Long id){
        resenasService.mostrarResenaById(id);
    }

}
