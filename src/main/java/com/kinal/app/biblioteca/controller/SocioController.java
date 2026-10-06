package com.kinal.app.biblioteca.controller;

import com.kinal.app.biblioteca.entity.Socio;
import com.kinal.app.biblioteca.service.ISocioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/socios")
public class SocioController {

    private final ISocioService socioService;

    public SocioController(ISocioService socioService) {
        this.socioService = socioService;
    }

    @GetMapping
    public String listarSocios(Model model) {
        model.addAttribute("socios", socioService.listarTodos());
        return "listar-socios";
    }

    @GetMapping("/nuevo")
    public String nuevoSocio(Model model) {
        Socio socio = new Socio();
        socio.setFechaRegistro(LocalDate.now());
        model.addAttribute("socio", socio);
        return "formulario-socio";
    }

    @PostMapping("/guardar")
    public String guardarSocio(@ModelAttribute("socio") Socio socio, RedirectAttributes redirectAttributes) {
        if (socio.getFechaRegistro() == null) {
            socio.setFechaRegistro(LocalDate.now());
        }
        socioService.guardar(socio);
        redirectAttributes.addFlashAttribute("mensajeExito", "Socio guardado exitosamente.");
        return "redirect:/socios";
    }

    @GetMapping("/editar/{id}")
    public String editarSocio(@PathVariable("id") Long id, Model model) {
        Socio socio = socioService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Socio no encontrado"));
        model.addAttribute("socio", socio);
        return "formulario-socio";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarSocio(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            socioService.eliminar(id);
            redirectAttributes.addFlashAttribute("mensajeExito", "Socio eliminado correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", "No se puede eliminar el socio porque tiene préstamos registrados.");
        }
        return "redirect:/socios";
    }
}
