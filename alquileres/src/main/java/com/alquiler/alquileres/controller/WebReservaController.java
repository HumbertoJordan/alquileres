package com.alquiler.alquileres.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alquiler.alquileres.dto.ReservaDTO;
import com.alquiler.alquileres.service.ReservaService;

@Controller
@RequestMapping("/reservas")
public class WebReservaController {

    private final ReservaService service;

    public WebReservaController(ReservaService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(Model model) {
        List<ReservaDTO> reservas = service.findAll();
        model.addAttribute("reservas", reservas);
        return "reservas/list";
    }

    @GetMapping("/nuevo")
    public String formularioNuevo(Model model) {
        model.addAttribute("reserva", new ReservaDTO());
        return "reservas/form";
    }

    @PostMapping
    public String guardar(ReservaDTO reservaDto) {
        service.save(reservaDto);
        return "redirect:/reservas";
    }

    @GetMapping("/editar/{id}")
    public String formularioEditar(@PathVariable Long id, Model model) {
        ReservaDTO reserva = service.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        model.addAttribute("reserva", reserva);
        model.addAttribute("id", id);
        return "reservas/form";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizar(@PathVariable Long id, ReservaDTO reservaDto) {
        reservaDto.setId(id);
        service.save(reservaDto);
        return "redirect:/reservas";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        service.deleteById(id);
        return "redirect:/reservas";
    }
}
