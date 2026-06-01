package com.alquiler.alquileres.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.alquiler.alquileres.dto.PropiedadDTO;
import com.alquiler.alquileres.service.PropiedadService;

@Controller
@RequestMapping("/propiedades")
public class WebPropiedadController {

    private static final String UPLOAD_DIR = "E:/FinalSistemaAlquiler/uploads/propiedades/";

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
    @PreAuthorize("hasRole('ADMIN')")
    public String formularioNuevo(Model model) {
        model.addAttribute("propiedad", new PropiedadDTO());
        return "propiedades/form";
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String guardar(PropiedadDTO propiedadDto,
                          @RequestParam(value = "imagenFile", required = false) MultipartFile imagenFile) {
        if (imagenFile != null && !imagenFile.isEmpty()) {
            try {
                String nombre = UUID.randomUUID() + "_" + imagenFile.getOriginalFilename();
                Path ruta = Paths.get(UPLOAD_DIR + nombre);
                Files.createDirectories(ruta.getParent());
                Files.write(ruta, imagenFile.getBytes());
                propiedadDto.setImagenUrl("/uploads/propiedades/" + nombre);
            } catch (Exception e) {
                throw new RuntimeException("No se pudo guardar la imagen", e);
            }
        }
        service.save(propiedadDto);
        return "redirect:/propiedades";
    }

    @GetMapping("/ver/{id}")
    public String verPropiedad(@PathVariable Long id, Model model) {
        PropiedadDTO propiedad = service.findById(id)
                .orElseThrow(() -> new RuntimeException("Propiedad no encontrada"));
        model.addAttribute("propiedad", propiedad);
        model.addAttribute("id", id);
        return "propiedades/ver";
    }

    @GetMapping("/editar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String formularioEditar(@PathVariable Long id, Model model) {
        PropiedadDTO propiedad = service.findById(id)
                .orElseThrow(() -> new RuntimeException("Propiedad no encontrada"));
        model.addAttribute("propiedad", propiedad);
        model.addAttribute("id", id);
        return "propiedades/form";
    }

    @PostMapping("/actualizar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    /* @PreAuthorize("hasRole('ADMIN')") */
    public String actualizar(@PathVariable Long id,
                             PropiedadDTO propiedadDto,
                             @RequestParam(value = "imagenFile", required = false) MultipartFile imagenFile) {
        PropiedadDTO propiedadExistente = service.findById(id)
                .orElseThrow(() -> new RuntimeException("Propiedad no encontrada"));

        if (imagenFile != null && !imagenFile.isEmpty()) {
            try {
                String nombre = UUID.randomUUID() + "_" + imagenFile.getOriginalFilename();
                Path ruta = Paths.get(UPLOAD_DIR + nombre);
                Files.createDirectories(ruta.getParent());
                Files.write(ruta, imagenFile.getBytes());
                propiedadDto.setImagenUrl("/uploads/propiedades/" + nombre);
            } catch (Exception e) {
                throw new RuntimeException("No se pudo guardar la imagen", e);
            }
        } else if (propiedadDto.getImagenUrl() == null || propiedadDto.getImagenUrl().isBlank()) {
            propiedadDto.setImagenUrl(propiedadExistente.getImagenUrl());
        }

        propiedadDto.setId(id);
        propiedadDto.setActivo(propiedadExistente.getActivo());
        service.save(propiedadDto);
        return "redirect:/propiedades";
    }

    @GetMapping("/eliminar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String eliminar(@PathVariable Long id) {
        service.deleteById(id);
        return "redirect:/propiedades";
    }
}
