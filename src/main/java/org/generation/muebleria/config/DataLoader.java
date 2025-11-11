package org.generation.muebleria.config;

import lombok.AllArgsConstructor;
import org.generation.muebleria.model.Categorias;
import org.generation.muebleria.model.Proveedores;
import org.generation.muebleria.model.Roles;
import org.generation.muebleria.repository.CategoriaRepository;
import org.generation.muebleria.repository.ProveedoresRepository;
import org.generation.muebleria.repository.RolRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime; // Importar para fechas de proveedores

@Component
@AllArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final RolRepository rolRepository;
    private final CategoriaRepository categoriaRepository;     // <-- AÑADIDO
    private final ProveedoresRepository proveedoresRepository; // <-- AÑADIDO

    @Override
    public void run(String... args) throws Exception {
        
        // --- Creación de Roles (ya lo tenías) ---
        if(rolRepository.findByNombreRol("ADMINISTRADOR").isEmpty()){
            Roles adminRole = new Roles(null,"ADMINISTRADOR",null);
            rolRepository.save(adminRole);
            System.out.println("Rol administrador creado");
        }

        if(rolRepository.findByNombreRol("CLIENTE").isEmpty()){
            Roles adminRole = new Roles(null,"CLIENTE",null);
            rolRepository.save(adminRole);
            System.out.println("Rol cliente creado");
        }

        // ==========================================================
        // INICIO - FIX PARA DEMO
        // Cargador de datos para que coincida con admin.js
        // ==========================================================

        // --- Creación de Categorías ---
        // Verificamos si no hay categorías para no duplicar
        if (categoriaRepository.count() == 0) { 
            System.out.println("Cargando categorías de demo...");
            
            // 1. Guardar Categorías Padre
            // OJO: Guardamos el objeto que retorna .save()
            // porque ese objeto tiene el ID que generó la BDD (1, 2, 3...)
            Categorias salas = categoriaRepository.save(new Categorias(null, "Salas", true, null, null));
            Categorias comedores = categoriaRepository.save(new Categorias(null, "Comedores", true, null, null));
            Categorias recamaras = categoriaRepository.save(new Categorias(null, "Recámaras", true, null, null));
            
            // Esta la creamos inactiva, tal como en tu JS
            categoriaRepository.save(new Categorias(null, "Oficina", false, null, null));

            // 2. Guardar Categorías Hijas (usando los objetos padre que guardamos)
            categoriaRepository.save(new Categorias(null, "Sofás", true, null, salas));
            categoriaRepository.save(new Categorias(null, "Sillones", true, null, salas));
            categoriaRepository.save(new Categorias(null, "Mesas de Comedor", true, null, comedores));
            categoriaRepository.save(new Categorias(null, "Sillas de Comedor", true, null, comedores));
            categoriaRepository.save(new Categorias(null, "Camas", true, null, recamaras));
            categoriaRepository.save(new Categorias(null, "Burós", true, null, recamaras));
            
            System.out.println("Categorías de demo cargadas.");
        }

        // --- Creación de Proveedores ---
        // Verificamos si no hay proveedores
        if (proveedoresRepository.count() == 0) { 
            System.out.println("Cargando proveedores de demo...");

            // Usamos el constructor @AllArgsConstructor de Proveedores
            // (id, nombreEmpresa, nombre, telefono, correo, direccion, activo, fechaRegistro, fechaActualizacion, productos)
            
            Proveedores p1 = new Proveedores(null, "Muebles Modernos S.A.", "Juan Pérez", "5512345678", "juan@modernos.com", "Calle Falsa 123", true, null, null, null);
            proveedoresRepository.save(p1);

            Proveedores p2 = new Proveedores(null, "Decoraciones Rústicas", "Ana García", "5587654321", "ana@rustico.com", "Avenida Siempre Viva 742", true, null, null, null);
            proveedoresRepository.save(p2);

            // Este lo creamos inactivo, tal como en tu JS
            Proveedores p3 = new Proveedores(null, "Importadora del Norte", "Carlos Sánchez", "5555555555", "carlos@import.com", "Blvd. Industrial 404", false, null, null, null);
            proveedoresRepository.save(p3);
            
            System.out.println("Proveedores de demo cargados.");
        }

        // ==========================================================
        // FIN - FIX PARA DEMO
        // ==========================================================
    }
}
