package com.alquiler.alquileres.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.alquiler.alquileres.model.Cliente;
import com.alquiler.alquileres.repository.ClienteRepository;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    // Buscar todos los clientes
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    // Buscar 1 cliente por ID
    @SuppressWarnings("null")
    public Optional<Cliente> findById(Long id) {
        return clienteRepository.findById(id);
    }

    // Guardar cliente
    @SuppressWarnings("null")
    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    // Eliminar cliente por ID
    @SuppressWarnings("null")
    public void deleteById(Long id) {
        clienteRepository.deleteById(id);
    }
}
