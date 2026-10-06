package com.kinal.app.biblioteca.service;

import com.kinal.app.biblioteca.entity.Autor;
import com.kinal.app.biblioteca.repository.AutorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AutorService implements IAutorService {

    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Autor> listarTodos() {
        return autorRepository.findAll();
    }

    @Override
    public Autor guardar(Autor autor) {
        return autorRepository.save(autor);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Autor> buscarPorId(Long id) {
        return autorRepository.findById(id);
    }

    @Override
    public void eliminar(Long id) {
        autorRepository.deleteById(id);
    }
}
