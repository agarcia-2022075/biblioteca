package com.kinal.app.biblioteca.config;

import com.kinal.app.biblioteca.repository.UsuarioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuración central de Seguridad (Spring Security).
 * Define las reglas de acceso por roles (ADMINISTRADOR vs USUARIO).
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UsuarioRepository usuarioRepository;
    private final CustomAuthenticationSuccessHandler successHandler;

    public SecurityConfig(UsuarioRepository usuarioRepository, CustomAuthenticationSuccessHandler successHandler) {
        this.usuarioRepository = usuarioRepository;
        this.successHandler = successHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // 1. Rutas públicas (No requieren inicio de sesión)
                .requestMatchers("/login", "/registro", "/css/**", "/js/**", "/images/**").permitAll()

                // 2. Rutas EXCLUSIVAS para ADMINISTRADOR (Control total)
                .requestMatchers("/autores/**", "/categorias/**", "/socios/**", "/usuarios/**").hasRole("ADMINISTRADOR")
                .requestMatchers("/libros/nuevo", "/libros/guardar", "/libros/editar/**", "/libros/eliminar/**").hasRole("ADMINISTRADOR")
                .requestMatchers("/prestamos/devolver/**", "/prestamos/eliminar/**").hasRole("ADMINISTRADOR")

                // 3. Rutas accesibles tanto por ADMINISTRADOR como por USUARIO (Lector)
                .requestMatchers("/dashboard", "/libros", "/prestamos", "/prestamos/nuevo", "/prestamos/guardar").hasAnyRole("ADMINISTRADOR", "USUARIO")

                // 4. Cualquier otra petición requiere autenticación
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .successHandler(successHandler)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            )
            .exceptionHandling(exception -> exception
                .accessDeniedPage("/403") // Página de error 403 personalizada
            )
            .csrf(csrf -> csrf.disable()); // Deshabilitado para pruebas y formularios simples de examen

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            // Usuario Maestro 1: Administrador (para calificar el examen de una vez)
            if ("admin".equals(username)) {
                return User.withUsername("admin")
                        .password("admin123")
                        .roles("ADMINISTRADOR")
                        .build();
            }

            // Usuario Maestro 2: Lector / Usuario normal (para probar la vista de usuario)
            if ("user".equals(username)) {
                return User.withUsername("user")
                        .password("user123")
                        .roles("USUARIO")
                        .build();
            }

            // Búsqueda de usuarios creados en la base de datos
            return usuarioRepository.findByUsername(username)
                    .filter(u -> u.getEstado() == 1)
                    .map(u -> User.withUsername(u.getUsername())
                            .password(u.getPassword())
                            .roles(u.getRol().replace("ROLE_", ""))
                            .build())
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
        };
    }

    @SuppressWarnings("deprecation")
    @Bean
    public PasswordEncoder passwordEncoder() {
        // En entornos escolares/exámenes se usa NoOp para no encriptar contraseñas manualmente
        return NoOpPasswordEncoder.getInstance();
    }
}
