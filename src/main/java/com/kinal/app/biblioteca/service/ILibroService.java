package com.kinal.app.biblioteca.service;

import com.kinal.app.biblioteca.entity.Libro;
import java.util.List;
import java.util.Optional;

public interface ILibroService {
    List<Libro> listarTodos();
    Libro guardar(Libro libro);
    Optional<Libro> buscarPorId(Long id);
    void eliminar(Long id);
}
