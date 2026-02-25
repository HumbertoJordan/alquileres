package com.alquiler.alquileres.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String nombre; // ROLE_USER, ROLE_ADMIN

    @Column(length = 200)
    private String descripcion;

    public Rol(String nombre) {
        this.nombre = nombre;
    }
}
