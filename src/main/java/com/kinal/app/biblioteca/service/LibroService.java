package com.kinal.app.biblioteca.service;

import com.kinal.app.biblioteca.entity.Libro;
import com.kinal.app.biblioteca.repository.LibroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LibroService implements ILibroService {

    private final LibroRepository libroRepository;

    public LibroService(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Libro> listarTodos() {
        return libroRepository.findAll();
    }

    @Override
    public Libro guardar(Libro libro) {
        return libroRepository.save(libro);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Libro> buscarPorId(Long id) {
        return libroRepository.findById(id);
    }

    @Override
    public void eliminar(Long id) {
        libroRepository.deleteById(id);
    }
}
