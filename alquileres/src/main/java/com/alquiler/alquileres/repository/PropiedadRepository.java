package com.alquiler.alquileres.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alquiler.alquileres.model.Propiedades;

public interface PropiedadRepository extends JpaRepository<Propiedades, Long> {
     
    /* List<Propiedades> findByDisponibleTrue();
    List<Propiedades> findByActivoTrue(); */
}
