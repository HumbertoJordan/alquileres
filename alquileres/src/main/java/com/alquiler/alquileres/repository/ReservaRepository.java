package com.alquiler.alquileres.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alquiler.alquileres.model.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
     
    List<Reserva> findByActivoTrue();
    List<Reserva> findByEstado(String estado);

}
