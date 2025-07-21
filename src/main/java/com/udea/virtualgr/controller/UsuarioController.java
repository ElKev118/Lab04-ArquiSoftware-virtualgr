package com.udea.virtualgr.controller;

import com.udea.virtualgr.entity.Usuario;
import com.udea.virtualgr.service.UsuarioService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import java.util.List;

@Controller
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @QueryMapping
    public List<Usuario> allUsuarios() {
        return usuarioService.getAllUsuarios();
    }

    @QueryMapping
    public Usuario usuarioById(@Argument Long id) {
        return usuarioService.getUsuarioById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    @QueryMapping
    public Usuario usuarioByEmail(@Argument String email) {
        return usuarioService.getUsuarioByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    @MutationMapping
    public Usuario createUsuario(@Argument String nombre, @Argument String email,
                                 @Argument String password, @Argument String rol) {
        Usuario.RolGlobal rolGlobal = Usuario.RolGlobal.valueOf(rol);
        return usuarioService.createUsuario(nombre, email, password, rolGlobal);
    }

    @MutationMapping
    public Usuario updateUsuario(@Argument Long id, @Argument String nombre, @Argument String email) {
        return usuarioService.updateUsuario(id, nombre, email);
    }

    @MutationMapping
    public Boolean deleteUsuario(@Argument Long id) {
        return usuarioService.deleteUsuario(id);
    }
}