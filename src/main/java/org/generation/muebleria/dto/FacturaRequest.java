package org.generation.muebleria.dto;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO (Formulario) para que el usuario envíe sus datos fiscales.
 * Esto se usaría al momento de "Generar Factura" para un pedido.
 */
@Getter
@Setter
public class FacturaRequest {

    private String rfc;
    private String razonSocial;
    // El idPedido vendrá por la URL, no en el body.
}