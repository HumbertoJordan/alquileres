package com.alquiler.alquileres.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// DTO para RECIBIR datos en POST/PUT (con validaciones)
public class ClienteRequestDto {
    
    // NOMBRE - Obligatorio
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;
    
    // APELLIDO - Obligatorio
    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 2, max = 100, message = "El apellido debe tener entre 2 y 100 caracteres")
    private String apellido;
    
    // EMAIL - Obligatorio y formato válido
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe ser válido")
    private String email;
    
    // TELÉFONO - Opcional
    @Size(max = 20, message = "El teléfono no puede exceder 20 caracteres")
    private String telefono;
    
    // DNI - Obligatorio
    @NotBlank(message = "El DNI es obligatorio")
    private String dni;

    // CONSTRUCTOR VACÍO
    public ClienteRequestDto() {
    }

    // CONSTRUCTOR CON PARÁMETROS
    public ClienteRequestDto(String nombre, String apellido, String email, String telefono, String dni) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
        this.dni = dni;
    }
    // GETTERS Y SETTERS 
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

   
}
