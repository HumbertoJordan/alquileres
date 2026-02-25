package com.alquiler.alquileres.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alquiler.alquileres.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>     {
     
    
    List<Cliente> findByActivoTrue();
    Optional<Cliente> findByEmail(String email);
}
