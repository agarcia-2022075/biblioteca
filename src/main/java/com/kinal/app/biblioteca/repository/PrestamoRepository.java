package com.kinal.app.biblioteca.repository;

import com.kinal.app.biblioteca.entity.Autor;
import com.kinal.app.biblioteca.entity.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo,Long> {
    List<Prestamo> findBySocioId(Long socioId);
}
