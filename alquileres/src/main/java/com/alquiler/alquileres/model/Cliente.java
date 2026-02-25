package com.alquiler.alquileres.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;

    @Column(unique = true, nullable = false)
    @Email(message = "Email inválido")
    @NotBlank(message = "El email es obligatorio")
    private String email;

    @Column(length = 20)
    private String telefono;
    
    @Column(unique = true, nullable = false)
    @NotBlank(message = "El DNI no puede estar vacío")
    
    private String dni;

    private java.time.LocalDate fechaRegistro = java.time.LocalDate.now();

    private boolean activo = true;

    public Cliente() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public java.time.LocalDate getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(java.time.LocalDate fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return java.util.Objects.equals(id, cliente.id);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
