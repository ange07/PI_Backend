package org.generation.muebleria.controller;

import lombok.AllArgsConstructor;
import org.generation.muebleria.dto.request.FacturaRequest;
import org.generation.muebleria.dto.response.FacturaResponse;
import org.generation.muebleria.service.interfaces.IFacturaService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/facturas")
@AllArgsConstructor
public class FacturaController {

    private final IFacturaService facturasService;

    // [POST] Crear Factura
    @PostMapping
    public FacturaResponse createFactura(@RequestBody FacturaRequest request) {
        return facturasService.generarFactura(request);
    }

    // [GET] Obtener por ID
    @GetMapping("/{id}")
    public FacturaResponse getFacturaById(@PathVariable Long id) {
        return facturasService.getFacturaById(id);
    }
}
