package org.generation.muebleria.repository;


import org.generation.muebleria.model.Direccion;
import org.generation.muebleria.model.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DireccionRepository extends JpaRepository<Direccion,Long> {
    // Añade este método para la lógica de predeterminada
    List<Direccion> findByUsuarioAndEsPredeterminadaTrue(Usuarios usuario);

    // Opcional: Para buscar direcciones de un usuario más eficientemente
    List<Direccion> findByUsuario(Usuarios usuario);
}
