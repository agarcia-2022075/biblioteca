package com.kinal.app.biblioteca.controller;

import com.kinal.app.biblioteca.entity.Autor;
import com.kinal.app.biblioteca.service.IAutorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/autores")
public class AutorController {

    private final IAutorService autorService;

    public AutorController(IAutorService autorService) {
        this.autorService = autorService;
    }

    @GetMapping
    public String listarAutores(Model model) {
        model.addAttribute("autores", autorService.listarTodos());
        return "listar-autores";
    }

    @GetMapping("/nuevo")
    public String nuevoAutor(Model model) {
        model.addAttribute("autor", new Autor());
        return "formulario-autor";
    }

    @PostMapping("/guardar")
    public String guardarAutor(@ModelAttribute("autor") Autor autor, RedirectAttributes redirectAttributes) {
        autorService.guardar(autor);
        redirectAttributes.addFlashAttribute("mensajeExito", "Autor guardado exitosamente.");
        return "redirect:/autores";
    }

    @GetMapping("/editar/{id}")
    public String editarAutor(@PathVariable("id") Long id, Model model) {
        Autor autor = autorService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Autor no encontrado"));
        model.addAttribute("autor", autor);
        return "formulario-autor";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarAutor(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            autorService.eliminar(id);
            redirectAttributes.addFlashAttribute("mensajeExito", "Autor eliminado correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", "No se puede eliminar el autor porque tiene libros asociados.");
        }
        return "redirect:/autores";
    }
}
