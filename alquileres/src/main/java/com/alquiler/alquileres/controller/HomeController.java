package com.alquiler.alquileres.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.alquiler.alquileres.service.PropiedadService;

@Controller
public class HomeController {

    private final PropiedadService propiedadService;

    public HomeController(PropiedadService propiedadService) {
        this.propiedadService = propiedadService;
    }

    @GetMapping("/")
    public String index() {
        // Puedes agregar propiedades destacadas si lo deseas
        // model.addAttribute("propiedades", propiedadService.findAll());
        return "index";
    }
}
