package com.kinal.app.biblioteca.service;

import com.kinal.app.biblioteca.entity.Socio;
import java.util.List;
import java.util.Optional;

public interface ISocioService {
    List<Socio> listarTodos();
    Socio guardar(Socio socio);
    Optional<Socio> buscarPorId(Long id);
    void eliminar(Long id);
}
