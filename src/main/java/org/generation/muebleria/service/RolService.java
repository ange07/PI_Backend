package org.generation.muebleria.service;

import lombok.AllArgsConstructor;
import org.generation.muebleria.model.Roles;
import org.generation.muebleria.repository.RolRepository;
import org.generation.muebleria.service.interfaces.IRolService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor // Inyecta el repositorio automáticamente
public class RolService implements IRolService {

    // Inyección de dependencia
    public RolRepository rolRepository;

    @Override
    public List<Roles> getAllRoles() {
        return rolRepository.findAll();
    }

    @Override
    public Roles getRolById(Integer id) {
        return rolRepository.findById(id).orElse(null);
    }

    @Override
    public Roles getRolByNombre(String nombre) {
        return rolRepository.findByNombreRol(nombre).orElse(null);
    }

    @Override
    public Roles createRol(Roles rol) {
        // Aquí podrías añadir validación para que no se repita el nombre
        return rolRepository.save(rol);
    }

    @Override
    public Roles updateRolById(Integer id, Roles rolActualizado) {
        // 1. Buscamos el rol
        Optional<Roles> rolExistente = rolRepository.findById(id);

        // 2. Si no existe, devolvemos null
        if (rolExistente.isEmpty()) {
            return null;
        }

        // 3. Actualizamos el nombre y guardamos
        Roles rolDb = rolExistente.get();
        rolDb.setNombreRol(rolActualizado.getNombreRol());

        return rolRepository.save(rolDb);
    }

    @Override
    public void deleteRolById(Integer id) {
        // A diferencia de Categorias, Roles no tiene 'activo',
        // así que aplicamos un borrado físico (Hard Delete).
        rolRepository.deleteById(id);
    }
}