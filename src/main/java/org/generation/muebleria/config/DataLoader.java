package org.generation.muebleria.config;

import lombok.AllArgsConstructor;
import org.generation.muebleria.model.Roles;
import org.generation.muebleria.repository.RolRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final RolRepository rolRepository;

    @Override
    public void run(String... args) throws Exception {
        //Creando roles admin y cliente a nivel aplicacion
        if(rolRepository.findByNombreRol("ADMINISTRADOR").isEmpty()){
            Roles adminRole = new Roles(null,"ADMINISTRADOR",null);
            rolRepository.save(adminRole);
            System.out.println("Rol administrador crado");
        }

        if(rolRepository.findByNombreRol("CLIENTE").isEmpty()){
            Roles adminRole = new Roles(null,"CLIENTE",null);
            rolRepository.save(adminRole);
            System.out.println("Rol cliente creado");
        }
    }
}
