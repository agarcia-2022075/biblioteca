package com.kinal.app.biblioteca.config;

import com.kinal.app.biblioteca.entity.Usuario;
import com.kinal.app.biblioteca.repository.UsuarioRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

/**
 * Manejador de éxito al autenticar.
 * Guarda en la sesión HTTP los datos del usuario logueado para mostrarlos en las vistas Thymeleaf.
 */
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UsuarioRepository usuarioRepository;

    public CustomAuthenticationSuccessHandler(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        HttpSession session = request.getSession();
        User user = (User) authentication.getPrincipal();
        String username = user.getUsername();

        if ("admin".equals(username)) {
            session.setAttribute("usuarioLogueado", "Administrador Maestro");
            session.setAttribute("rolUsuario", "ADMINISTRADOR");
        } else if ("user".equals(username)) {
            session.setAttribute("usuarioLogueado", "Usuario Lector");
            session.setAttribute("rolUsuario", "USUARIO");
        } else {
            Optional<Usuario> dbUser = usuarioRepository.findByUsername(username);
            if (dbUser.isPresent()) {
                session.setAttribute("usuarioLogueado", dbUser.get().getUsername());
                session.setAttribute("rolUsuario", dbUser.get().getRol());
            } else {
                session.setAttribute("usuarioLogueado", username);
                session.setAttribute("rolUsuario", "USUARIO");
            }
        }

        // Redirige al panel de control después del login exitoso
        response.sendRedirect("/dashboard");
    }
}
