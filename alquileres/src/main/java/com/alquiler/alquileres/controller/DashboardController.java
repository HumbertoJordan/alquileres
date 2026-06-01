package com.alquiler.alquileres.controller;

import com.alquiler.alquileres.dto.PropiedadDTO;
import com.alquiler.alquileres.service.PropiedadService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class DashboardController {

    private final PropiedadService propiedadService;

    public DashboardController(PropiedadService propiedadService) {
        this.propiedadService = propiedadService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication, Model model) {
        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/admin/dashboard";
        }
        List<PropiedadDTO> propiedades = propiedadService.findAll();
        model.addAttribute("propiedades", propiedades);
        return "dashboard";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "dashboard-admin";
    }
}