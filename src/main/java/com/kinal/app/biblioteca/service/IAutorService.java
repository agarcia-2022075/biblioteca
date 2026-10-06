package com.kinal.app.biblioteca.service;

import com.kinal.app.biblioteca.entity.Autor;
import com.kinal.app.biblioteca.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface IAutorService {
    List<Autor> listarTodos();
    Autor guardar(Autor autor);
    Optional<Autor> buscarPorId(Long id);
    void eliminar(Long id);
}
