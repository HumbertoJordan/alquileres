package com.alquiler.alquileres.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alquiler.alquileres.dto.PropiedadDTO;
import com.alquiler.alquileres.service.PropiedadService;

@Controller
@RequestMapping("/propiedades")
public class WebPropiedadController {

    private final PropiedadService service;

    public WebPropiedadController(PropiedadService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(Model model) {
        List<PropiedadDTO> propiedades = service.findAll();
        model.addAttribute("propiedades", propiedades);
        return "propiedades/list";
    }

    @GetMapping("/nuevo")
    public String formularioNuevo(Model model) {
        model.addAttribute("propiedad", new PropiedadDTO());
        return "propiedades/form";
    }

    @PostMapping
    public String guardar(PropiedadDTO propiedadDto) {
        service.save(propiedadDto);
        return "redirect:/propiedades";
    }

    @GetMapping("/editar/{id}")
    public String formularioEditar(@PathVariable Long id, Model model) {
        PropiedadDTO propiedad = service.findById(id)
                .orElseThrow(() -> new RuntimeException("Propiedad no encontrada"));
        model.addAttribute("propiedad", propiedad);
        model.addAttribute("id", id);
        return "propiedades/form";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizar(@PathVariable Long id, PropiedadDTO propiedadDto) {
        propiedadDto.setId(id);
        service.save(propiedadDto);
        return "redirect:/propiedades";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        service.deleteById(id);
        return "redirect:/propiedades";
    }
}
