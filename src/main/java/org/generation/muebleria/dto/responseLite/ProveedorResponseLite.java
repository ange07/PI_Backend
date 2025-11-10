package org.generation.muebleria.dto.responseLite;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProveedorResponseLite {
    private Long idProveedor;
    private String nombreEmpresa;
    private String nombre;
}
