package com.kinal.app.biblioteca.repository;

import com.kinal.app.biblioteca.entity.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
    public interface AutorRepository extends JpaRepository<Autor,Long>{
}
