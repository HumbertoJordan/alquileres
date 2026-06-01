package com.alquiler.alquileres.model;

import java.math.BigDecimal;

import jakarta.persistence.*;

@Entity
@Table(name = "propiedades")
public class Propiedades {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String titulo;

    @Column(nullable = false, length = 200)
    private String descripcion;

    @Column(nullable = false, length = 200)
    private String direccion;

    @Column(nullable = false)
    private BigDecimal precioAlquiler;

    @Column(name = "imagen_url", length = 200)
    private String imagenUrl;
    
    @Column(nullable = false, length = 60)    
    private String ciudad;

    private boolean disponible;
    private boolean activo;

    public Propiedades() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public BigDecimal getPrecioAlquiler() { return precioAlquiler; }
    public void setPrecioAlquiler(BigDecimal precioAlquiler) { this.precioAlquiler = precioAlquiler; }

    public String getImagenUrl() { return imagenUrl; }
    public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }

    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }

    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}
