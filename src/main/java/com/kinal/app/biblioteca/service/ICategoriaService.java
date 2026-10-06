package com.kinal.app.biblioteca.service;

import com.kinal.app.biblioteca.entity.Categoria;
import java.util.List;
import java.util.Optional;

public interface ICategoriaService {
    List<Categoria> listarTodos();
    Categoria guardar(Categoria categoria);
    Optional<Categoria> buscarPorId(Long id);
    void eliminar(Long id);
}
