package com.kinal.app.biblioteca.service;

import com.kinal.app.biblioteca.entity.Prestamo;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IPrestamoService {
    List<Prestamo> listarTodos();
    List<Prestamo> listarPorSocioId(Long socioId);
    Optional<Prestamo> buscarPorId(Long id);
    Prestamo crearPrestamo(Long socioId, Long libroId, LocalDate fechaDevolucionEsperada);
    Prestamo devolverPrestamo(Long prestamoId);
    void eliminar(Long id);
}
