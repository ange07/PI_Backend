package org.generation.muebleria.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.generation.muebleria.dto.request.UsuarioRequest;
import org.generation.muebleria.dto.response.UsuarioResponse;
import org.generation.muebleria.dto.responseLite.UsuarioResponseLite;
import org.generation.muebleria.model.Roles;
import org.generation.muebleria.model.Usuarios;
import org.generation.muebleria.repository.RolRepository;
import org.generation.muebleria.repository.UsuariosRepository;
import org.generation.muebleria.service.interfaces.IUsuariosService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UsuarioService implements IUsuariosService, UserDetailsService {

    private final UsuariosRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final RolService roleService;
    //definicion de correo administrador
    private static final String CORREO_ADMIN = "admin@ecommerce.com";
    public PasswordEncoder passwordEncoder;



    @Override
    public List<UsuarioResponse> getAllUsers() {
        List<Usuarios> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioResponse getUserById(Long id) {
        Usuarios user = usuarioRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("No existe el usuario con el id" + id)
        );
        return mapToResponseDTO(user);
    }

    @Override
    public UsuarioResponse addUser(UsuarioRequest user) {
        if (usuarioRepository.findByCorreo(user.getCorreo()).isPresent()) {
            throw new IllegalArgumentException("El correo ya está registrado.");
        }

        String asignacionRol;
        if(CORREO_ADMIN.equalsIgnoreCase(user.getCorreo())){
            asignacionRol = "ADMINISTRADOR";
        }else{
            asignacionRol = "CLIENTE";
        }

        // Asignamos el rol
        Roles assignedRole = rolRepository.findByNombreRol(asignacionRol)
                .orElseThrow(() -> new RuntimeException("Rol "+asignacionRol+" no encontrado"));


        // Creamos la Entidad que vamos a guardar
        Usuarios newUser = new Usuarios();

        // Mapeo de campos
        newUser.setNombre(user.getNombre());
        newUser.setApellidos(user.getApellidos());
        newUser.setTelefono(user.getTelefono());
        newUser.setCorreo(user.getCorreo());

        // Asignación del rol
        newUser.setRol(assignedRole);
        //encriptar la contraseña que viene del usuario
        String encriptedPassword = passwordEncoder.encode(user.getPassword());
        //fijar la contraseña encriptada al objeto del usuario
        newUser.setPasswordHas(encriptedPassword);

        Usuarios saveUser = usuarioRepository.save(newUser);

        return mapToResponseDTO(saveUser);
    }

    @Override
    public UsuarioResponse desactivarUserById(Long id) {
        Usuarios user = usuarioRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("No existe el usuario con el id" + id)
        );

        if (user.getActivo() == null || !user.getActivo()) {
            throw new IllegalStateException("El usuario con ID " + id + " ya está inactivo.");
        }

        user.setActivo(false);

        Usuarios saveUser = usuarioRepository.save(user);
        return mapToResponseDTO(saveUser);
    }


    @Override
    public UsuarioResponse updateUserById(Long id, UsuarioRequest updatedUser) {
        Usuarios orignalInfo = usuarioRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("No existe el usuario con el id" + id)
        );

        // Si el correo cambia, debe ser único
        if (updatedUser.getCorreo() != null && !updatedUser.getCorreo().equals(orignalInfo.getCorreo())) {
            if (usuarioRepository.findByCorreo(updatedUser.getCorreo()).isPresent()) {
                throw new IllegalArgumentException("El nuevo correo ya está en uso.");
            }
            orignalInfo.setCorreo(updatedUser.getCorreo());
        }

        if(updatedUser.getNombre() != null) orignalInfo.setNombre(updatedUser.getNombre());
        if(updatedUser.getApellidos() != null) orignalInfo.setApellidos(updatedUser.getApellidos());
        if(updatedUser.getCorreo() != null) orignalInfo.setCorreo(updatedUser.getCorreo());
        if(updatedUser.getPassword() != null) orignalInfo.setPasswordHas(passwordEncoder.encode(updatedUser.getPassword()));
        if(updatedUser.getTelefono()!= null) orignalInfo.setTelefono(updatedUser.getTelefono());

        Usuarios saveOriginal = usuarioRepository.save(orignalInfo);

        return mapToResponseDTO(saveOriginal);
    }

    @Transactional
    public UsuarioResponse updateUserRole(Long userId, String newRoleName) {

        Usuarios userToUpdate = usuarioRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + userId));

        Roles newRole = rolRepository.findByNombreRol(newRoleName)
                .orElseThrow(() -> new IllegalArgumentException("Rol '" + newRoleName + "' no existe."));


        userToUpdate.setRol(newRole);

        Usuarios savedUser = usuarioRepository.save(userToUpdate);

        return mapToResponseDTO(savedUser);
    }

    @Override
    public boolean validateUser(UsuarioRequest user) {
        Optional<Usuarios> optionalUser = usuarioRepository.findByCorreo(user.getCorreo());
        if(optionalUser.isEmpty()) throw new IllegalArgumentException("El correo o la contraseña son incorrectos");
        //compramos las contraseñas, primero la contraseña que viene en la peticion, y luego la contraseña que tenemos almacenada
        //en la bd. Este metodo matches retorna true si coinciden o false si no coinciden
        return passwordEncoder.matches(user.getPassword(),optionalUser.get().getPasswordHas());
    }

    //Spring security lo va usar para hacer la carga de los usuarios
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return (UserDetails) usuarioRepository.findByCorreo(email).orElseThrow(
                () -> new UsernameNotFoundException("No se encontro el usuario con el emai" + email)
        );
    }

    public UsuarioResponse mapToResponseDTO(Usuarios user) {
        if (user == null) return null;
        UsuarioResponse dto = new UsuarioResponse();

        dto.setIdUsuario(user.getIdUsuario());
        dto.setNombre(user.getNombre());
        dto.setApellidos(user.getApellidos());
        dto.setCorreo(user.getCorreo());
        dto.setTelefono(user.getTelefono());
        dto.setActivo(user.getActivo());
        dto.setFechaRegistro(user.getFechaRegistro());
        dto.setFechaActualizacion(user.getFechaActualizacion());

        // Mapear la relación del Rol
        if (user.getRol() != null) {
            dto.setRol(roleService.mapToResponseDTO(user.getRol()));
        }

        return dto;
    }

    public UsuarioResponseLite mapToLiteDTO(Usuarios user) {
        if (user == null) return null;
        UsuarioResponseLite dto = new UsuarioResponseLite();

        dto.setIdUsuario(user.getIdUsuario());
        dto.setNombre(user.getNombre());
        dto.setApellidos(user.getApellidos());
        dto.setCorreo(user.getCorreo());

        return dto;
    }

}