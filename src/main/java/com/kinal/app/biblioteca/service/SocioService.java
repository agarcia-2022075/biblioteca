package com.kinal.app.biblioteca.service;

import com.kinal.app.biblioteca.entity.Socio;
import com.kinal.app.biblioteca.repository.SocioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SocioService implements ISocioService {

    private final SocioRepository socioRepository;

    public SocioService(SocioRepository socioRepository) {
        this.socioRepository = socioRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Socio> listarTodos() {
        return socioRepository.findAll();
    }

    @Override
    public Socio guardar(Socio socio) {
        if (socio.getFechaRegistro() == null) {
            socio.setFechaRegistro(LocalDate.now());
        }
        return socioRepository.save(socio);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Socio> buscarPorId(Long id) {
        return socioRepository.findById(id);
    }

    @Override
    public void eliminar(Long id) {
        socioRepository.deleteById(id);
    }
}
