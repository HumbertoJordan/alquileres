package com.alquiler.alquileres.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Redirige /alquileres -> /reservas
 * Los "alquileres" del sistema son las reservas confirmadas/completadas.
 */
@Controller
@RequestMapping("/alquileres")
public class AlquileresRedirectController {

    @GetMapping
    public String redirectToReservas() {
        return "redirect:/reservas";
    }

    @GetMapping("/nuevo")
    public String redirectNuevo() {
        return "redirect:/reservas/nuevo";
    }
}
