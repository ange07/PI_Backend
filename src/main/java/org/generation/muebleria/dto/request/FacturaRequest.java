package org.generation.muebleria.dto.request;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO (Formulario) para que el usuario envíe sus datos fiscales.
 * Esto se usaría al momento de "Generar Factura" para un pedido.
 */
@Getter
@Setter
public class FacturaRequest {
    private Long IdPedido;
    private String rfc;
    private String razonSocial;
}