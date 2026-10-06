package com.kinal.app.biblioteca.controller;

import com.kinal.app.biblioteca.entity.Usuario;
import com.kinal.app.biblioteca.service.IUsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final IUsuarioService usuarioService;

    public AuthController(IUsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registro")
    public String registro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro";
    }

    @PostMapping("/registro")
    public String registrarUsuario(@ModelAttribute("usuario") Usuario usuario) {
        // Por defecto todo usuario registrado desde el formulario es rol USUARIO
        usuario.setRol("USUARIO");
        usuario.setEstado(1);
        usuarioService.guardar(usuario);
        return "redirect:/login?registroExitoso";
    }

    @GetMapping("/403")
    public String accesoDenegado() {
        return "403";
    }
}
