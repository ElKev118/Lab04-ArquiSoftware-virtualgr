package com.udea.virtualgr.service;

import com.udea.virtualgr.entity.Usuario;
import com.udea.virtualgr.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> getUsuarioById(Long id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> getUsuarioByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public Usuario createUsuario(String nombre, String email, String password, Usuario.RolGlobal rol) {
        if (usuarioRepository.existsByEmail(email)) {
            throw new RuntimeException("El email ya está registrado");
        }

        if (!email.endsWith("@udea.edu.co")) {
            throw new RuntimeException("El email debe terminar en @udea.edu.co");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setPassword(password);
        usuario.setRol(rol);

        return usuarioRepository.save(usuario);
    }

    public Usuario updateUsuario(Long id, String nombre, String email) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (nombre != null) usuario.setNombre(nombre);
        if (email != null && !email.equals(usuario.getEmail())) {
            if (usuarioRepository.existsByEmail(email)) {
                throw new RuntimeException("El email ya está registrado");
            }
            if (!email.endsWith("@udea.edu.co")) {
                throw new RuntimeException("El email debe terminar en @udea.edu.co");
            }
            usuario.setEmail(email);
        }

        return usuarioRepository.save(usuario);
    }

    public boolean deleteUsuario(Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }
}