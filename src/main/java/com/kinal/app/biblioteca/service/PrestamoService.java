package com.kinal.app.biblioteca.service;

import com.kinal.app.biblioteca.entity.Libro;
import com.kinal.app.biblioteca.entity.Prestamo;
import com.kinal.app.biblioteca.entity.Socio;
import com.kinal.app.biblioteca.repository.LibroRepository;
import com.kinal.app.biblioteca.repository.PrestamoRepository;
import com.kinal.app.biblioteca.repository.SocioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PrestamoService implements IPrestamoService {

    private final PrestamoRepository prestamoRepository;
    private final LibroRepository libroRepository;
    private final SocioRepository socioRepository;

    public PrestamoService(PrestamoRepository prestamoRepository,
                           LibroRepository libroRepository,
                           SocioRepository socioRepository) {
        this.prestamoRepository = prestamoRepository;
        this.libroRepository = libroRepository;
        this.socioRepository = socioRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Prestamo> listarTodos() {
        return prestamoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Prestamo> listarPorSocioId(Long socioId) {
        return prestamoRepository.findBySocioId(socioId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Prestamo> buscarPorId(Long id) {
        return prestamoRepository.findById(id);
    }

    @Override
    public Prestamo crearPrestamo(Long socioId, Long libroId, LocalDate fechaDevolucionEsperada) {
        Socio socio = socioRepository.findById(socioId)
                .orElseThrow(() -> new RuntimeException("Socio no encontrado con ID: " + socioId));

        Libro libro = libroRepository.findById(libroId)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado con ID: " + libroId));

        // Validación de negocio: stock de copias
        if (libro.getCopiasDisponibles() <= 0) {
            throw new RuntimeException("El libro '" + libro.getTitulo() + "' no cuenta con copias disponibles para préstamo.");
        }

        // Descontar copia del libro
        libro.setCopiasDisponibles(libro.getCopiasDisponibles() - 1);
        libroRepository.save(libro);

        // Crear el préstamo
        Prestamo prestamo = new Prestamo();
        prestamo.setSocio(socio);
        prestamo.setLibro(libro);
        prestamo.setFechaPrestamo(LocalDate.now());
        prestamo.setFechaDevolucionEsperada(fechaDevolucionEsperada != null ? fechaDevolucionEsperada : LocalDate.now().plusDays(7));
        prestamo.setEstado("PRESTADO");

        return prestamoRepository.save(prestamo);
    }

    @Override
    public Prestamo devolverPrestamo(Long prestamoId) {
        Prestamo prestamo = prestamoRepository.findById(prestamoId)
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado con ID: " + prestamoId));

        if ("DEVUELTO".equalsIgnoreCase(prestamo.getEstado())) {
            throw new RuntimeException("Este préstamo ya fue devuelto anteriormente.");
        }

        // Marcar como devuelto y fecha de hoy
        prestamo.setEstado("DEVUELTO");
        prestamo.setFechaDevolucionReal(LocalDate.now());

        // Devolver la copia al inventario del libro
        Libro libro = prestamo.getLibro();
        if (libro != null) {
            libro.setCopiasDisponibles(libro.getCopiasDisponibles() + 1);
            libroRepository.save(libro);
        }

        return prestamoRepository.save(prestamo);
    }

    @Override
    public void eliminar(Long id) {
        prestamoRepository.deleteById(id);
    }
}
