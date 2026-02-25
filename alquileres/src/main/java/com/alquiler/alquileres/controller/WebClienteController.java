package com.alquiler.alquileres.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alquiler.alquileres.dto.ClienteRequestDto;
import com.alquiler.alquileres.mapper.ClienteMapper;
import com.alquiler.alquileres.model.Cliente;
import com.alquiler.alquileres.service.ClienteService;

@Controller
@RequestMapping("/clientes")
public class WebClienteController {

    private final ClienteService service;
    private final ClienteMapper mapper;

    public WebClienteController(ClienteService service, ClienteMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public String listar(Model model) {
        List<Cliente> clientes = service.findAll();
        model.addAttribute("clientes", clientes);
        return "clientes/list";
    }

    @GetMapping("/nuevo")
    public String formularioNuevo(Model model) {
        model.addAttribute("cliente", new ClienteRequestDto());
        return "clientes/form";
    }

    @PostMapping
    public String guardar(ClienteRequestDto clienteDto) {
        Cliente cliente = mapper.toEntity(clienteDto);
        service.save(cliente);
        return "redirect:/clientes";
    }

    @GetMapping("/editar/{id}")
    public String formularioEditar(@PathVariable Long id, Model model) {
        Cliente cliente = service.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        ClienteRequestDto dto = new ClienteRequestDto();
        dto.setNombre(cliente.getNombre());
        dto.setApellido(cliente.getApellido());
        dto.setEmail(cliente.getEmail());
        dto.setTelefono(cliente.getTelefono());
        dto.setDni(cliente.getDni());
        model.addAttribute("cliente", dto);
        model.addAttribute("id", id);
        return "clientes/form";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizar(@PathVariable Long id, ClienteRequestDto clienteDto) {
        Cliente cliente = mapper.toEntity(clienteDto);
        cliente.setId(id);
        service.save(cliente);
        return "redirect:/clientes";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        service.deleteById(id);
        return "redirect:/clientes";
    }
}
