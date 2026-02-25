package com.alquiler.alquileres.dto;

import java.time.LocalDate;

// DTO para DEVOLVER datos en GET/POST/PUT (sin validaciones, solo lectura)
public class ClienteResponseDto {
    
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String dni;
    private LocalDate fechaRegistro;
    private boolean activo;

    // CONSTRUCTOR VACÍO
    public ClienteResponseDto() {
    }
    
    // CONSTRUCTOR CON TODOS LOS PARÁMETROS
    public ClienteResponseDto(Long id, String nombre, String apellido, String email, 
                              String telefono, String dni, LocalDate fechaRegistro, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
        this.dni = dni;
        this.fechaRegistro = fechaRegistro;
        this.activo = activo;
    }

    // GETTERS
    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getDni() {
        return dni;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public boolean isActivo() {
        return activo;
    }

    // SETTERS
    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
