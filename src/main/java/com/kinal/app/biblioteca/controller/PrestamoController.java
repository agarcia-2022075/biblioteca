package com.kinal.app.biblioteca.controller;

import com.kinal.app.biblioteca.entity.Libro;
import com.kinal.app.biblioteca.service.ILibroService;
import com.kinal.app.biblioteca.service.IPrestamoService;
import com.kinal.app.biblioteca.service.ISocioService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/prestamos")
public class PrestamoController {

    private final IPrestamoService prestamoService;
    private final ILibroService libroService;
    private final ISocioService socioService;

    public PrestamoController(IPrestamoService prestamoService,
                              ILibroService libroService,
                              ISocioService socioService) {
        this.prestamoService = prestamoService;
        this.libroService = libroService;
        this.socioService = socioService;
    }

    @GetMapping
    public String listarPrestamos(Model model) {
        model.addAttribute("prestamos", prestamoService.listarTodos());
        return "listar-prestamos";
    }

    @GetMapping("/nuevo")
    public String nuevoPrestamo(Model model) {
        // Solo mostramos libros con al menos 1 copia disponible para préstamo
        List<Libro> librosDisponibles = libroService.listarTodos().stream()
                .filter(l -> l.getCopiasDisponibles() > 0)
                .collect(Collectors.toList());

        model.addAttribute("socios", socioService.listarTodos());
        model.addAttribute("libros", librosDisponibles);
        model.addAttribute("fechaEsperadaDefecto", LocalDate.now().plusDays(7));
        return "formulario-prestamo";
    }

    @PostMapping("/guardar")
    public String realizarPrestamo(@RequestParam("socioId") Long socioId,
                                   @RequestParam("libroId") Long libroId,
                                   @RequestParam(value = "fechaDevolucionEsperada", required = false)
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDevolucionEsperada,
                                   RedirectAttributes redirectAttributes) {
        try {
            prestamoService.crearPrestamo(socioId, libroId, fechaDevolucionEsperada);
            redirectAttributes.addFlashAttribute("mensajeExito", "¡Préstamo registrado exitosamente! Stock del libro actualizado.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", e.getMessage());
            return "redirect:/prestamos/nuevo";
        }
        return "redirect:/prestamos";
    }

    @GetMapping("/devolver/{id}")
    public String devolverPrestamo(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            prestamoService.devolverPrestamo(id);
            redirectAttributes.addFlashAttribute("mensajeExito", "¡Libro devuelto con éxito! Copia repuesta al inventario.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", e.getMessage());
        }
        return "redirect:/prestamos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarPrestamo(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            prestamoService.eliminar(id);
            redirectAttributes.addFlashAttribute("mensajeExito", "Registro de préstamo eliminado.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", "Error al eliminar el préstamo: " + e.getMessage());
        }
        return "redirect:/prestamos";
    }
}
