package com.alquiler.alquileres.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alquiler.alquileres.dto.ReservaDTO;
import com.alquiler.alquileres.model.Cliente;
import com.alquiler.alquileres.dto.PropiedadDTO;
import com.alquiler.alquileres.service.ClienteService;
import com.alquiler.alquileres.service.PropiedadService;
import com.alquiler.alquileres.service.ReservaService;

@Controller
@RequestMapping("/reservas")
public class WebReservaController {

    private final ReservaService service;
    private final ClienteService clienteService;
    private final PropiedadService propiedadService;

    public WebReservaController(ReservaService service, ClienteService clienteService, PropiedadService propiedadService) {
        this.service = service;
        this.clienteService = clienteService;
        this.propiedadService = propiedadService;
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
        model.addAttribute("clientes", clienteService.findAll());
        model.addAttribute("propiedades", propiedadService.findAll());
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
        model.addAttribute("clientes", clienteService.findAll());
        model.addAttribute("propiedades", propiedadService.findAll());
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
