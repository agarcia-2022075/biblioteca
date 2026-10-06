package com.kinal.app.biblioteca.controller;

import com.kinal.app.biblioteca.entity.Libro;
import com.kinal.app.biblioteca.service.IAutorService;
import com.kinal.app.biblioteca.service.ICategoriaService;
import com.kinal.app.biblioteca.service.ILibroService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/libros")
public class LibroController {

    private final ILibroService libroService;
    private final IAutorService autorService;
    private final ICategoriaService categoriaService;

    public LibroController(ILibroService libroService,
                           IAutorService autorService,
                           ICategoriaService categoriaService) {
        this.libroService = libroService;
        this.autorService = autorService;
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public String listarLibros(Model model) {
        model.addAttribute("libros", libroService.listarTodos());
        return "listar-libros";
    }

    @GetMapping("/nuevo")
    public String nuevoLibro(Model model) {
        model.addAttribute("libro", new Libro());
        model.addAttribute("autores", autorService.listarTodos());
        model.addAttribute("categorias", categoriaService.listarTodos());
        return "formulario-libro";
    }

    @PostMapping("/guardar")
    public String guardarLibro(@ModelAttribute("libro") Libro libro, RedirectAttributes redirectAttributes) {
        libroService.guardar(libro);
        redirectAttributes.addFlashAttribute("mensajeExito", "Libro guardado exitosamente.");
        return "redirect:/libros";
    }

    @GetMapping("/editar/{id}")
    public String editarLibro(@PathVariable("id") Long id, Model model) {
        Libro libro = libroService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado con ID: " + id));
        model.addAttribute("libro", libro);
        model.addAttribute("autores", autorService.listarTodos());
        model.addAttribute("categorias", categoriaService.listarTodos());
        return "formulario-libro";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarLibro(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            libroService.eliminar(id);
            redirectAttributes.addFlashAttribute("mensajeExito", "Libro eliminado correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", "No se puede eliminar el libro porque tiene préstamos asociados.");
        }
        return "redirect:/libros";
    }
}
