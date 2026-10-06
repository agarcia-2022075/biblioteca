package com.kinal.app.biblioteca.config;

import com.kinal.app.biblioteca.entity.Autor;
import com.kinal.app.biblioteca.entity.Categoria;
import com.kinal.app.biblioteca.entity.Libro;
import com.kinal.app.biblioteca.entity.Socio;
import com.kinal.app.biblioteca.entity.Usuario;
import com.kinal.app.biblioteca.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

/**
 * Semilla de datos iniciales (Data Seeder).
 * Inserta automáticamente datos de prueba si la base de datos está vacía.
 * ¡Perfecto para presentar en el examen sin tener que llenar todo a mano!
 */
@Configuration
public class DataInitializer implements CommandLineRunner {

    private final AutorRepository autorRepository;
    private final CategoriaRepository categoriaRepository;
    private final LibroRepository libroRepository;
    private final SocioRepository socioRepository;
    private final UsuarioRepository usuarioRepository;

    public DataInitializer(AutorRepository autorRepository,
                           CategoriaRepository categoriaRepository,
                           LibroRepository libroRepository,
                           SocioRepository socioRepository,
                           UsuarioRepository usuarioRepository) {
        this.autorRepository = autorRepository;
        this.categoriaRepository = categoriaRepository;
        this.libroRepository = libroRepository;
        this.socioRepository = socioRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // 1. Inicializar Categorías si no hay ninguna
        if (categoriaRepository.count() == 0) {
            categoriaRepository.save(new Categoria("Novela"));
            categoriaRepository.save(new Categoria("Ciencia Ficción"));
            categoriaRepository.save(new Categoria("Historia"));
            categoriaRepository.save(new Categoria("Tecnología y Programación"));
        }

        // 2. Inicializar Autores si no hay ninguno
        if (autorRepository.count() == 0) {
            autorRepository.save(new Autor("Gabriel García Márquez", "Colombiana"));
            autorRepository.save(new Autor("Miguel Ángel Asturias", "Guatemalteca"));
            autorRepository.save(new Autor("George Orwell", "Británica"));
        }

        // 3. Inicializar Socios si no hay ninguno
        if (socioRepository.count() == 0) {
            socioRepository.save(new Socio("Paolo García", "agarcia-2022075@kinal.edu.gt", "5555-1234", LocalDate.now()));
            socioRepository.save(new Socio("Carlos Mendoza", "carlos.mendoza@gmail.com", "4444-5678", LocalDate.now().minusDays(30)));
        }

        // 4. Inicializar Libros si no hay ninguno
        if (libroRepository.count() == 0) {
            Autor gabriel = autorRepository.findAll().get(0);
            Autor asturias = autorRepository.findAll().get(1);
            Categoria novela = categoriaRepository.findAll().get(0);

            libroRepository.save(new Libro("Cien Años de Soledad", "978-0307474728", 1967, 5, gabriel, novela));
            libroRepository.save(new Libro("El Señor Presidente", "978-8420658421", 1946, 3, asturias, novela));
        }

        // 5. Inicializar Usuario de prueba en BD si no existe
        if (usuarioRepository.count() == 0) {
            usuarioRepository.save(new Usuario("admin_bd", "admin123", "admin@kinal.edu.gt", "ADMINISTRADOR", 1));
            usuarioRepository.save(new Usuario("lector1", "12345", "lector1@kinal.edu.gt", "USUARIO", 1));
        }
    }
}
