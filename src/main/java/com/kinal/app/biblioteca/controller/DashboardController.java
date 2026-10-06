package com.kinal.app.biblioteca.controller;

import com.kinal.app.biblioteca.service.IAutorService;
import com.kinal.app.biblioteca.service.ICategoriaService;
import com.kinal.app.biblioteca.service.ILibroService;
import com.kinal.app.biblioteca.service.IPrestamoService;
import com.kinal.app.biblioteca.service.ISocioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final ILibroService libroService;
    private final ISocioService socioService;
    private final IPrestamoService prestamoService;
    private final IAutorService autorService;
    private final ICategoriaService categoriaService;

    public DashboardController(ILibroService libroService,
                               ISocioService socioService,
                               IPrestamoService prestamoService,
                               IAutorService autorService,
                               ICategoriaService categoriaService) {
        this.libroService = libroService;
        this.socioService = socioService;
        this.prestamoService = prestamoService;
        this.autorService = autorService;
        this.categoriaService = categoriaService;
    }

    @GetMapping({"/", "/dashboard"})
    public String dashboard(Model model) {
        model.addAttribute("totalLibros", libroService.listarTodos().size());
        model.addAttribute("totalSocios", socioService.listarTodos().size());
        model.addAttribute("totalPrestamos", prestamoService.listarTodos().size());
        model.addAttribute("totalAutores", autorService.listarTodos().size());
        model.addAttribute("totalCategorias", categoriaService.listarTodos().size());
        return "dashboard";
    }
}
