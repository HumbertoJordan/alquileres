package com.alquiler.alquileres.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class PropiedadDTO {
    
    private Long id;

    @NotBlank
    private String titulo;

    @NotBlank
    private String descripcion;

    @NotBlank
    private String direccion;

    @NotBlank
    private String ciudad;

    @NotBlank
    private String provincia;
    
    @NotNull
    private BigDecimal precioAlquiler;

    private String imagenUrl;

    private Boolean disponible = true;

    private Boolean activo = true;

    public PropiedadDTO() {}
    
    public Long getId() { return id;}
    public void setId(Long id) { this.id = id;}

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }

    public String getProvincia() {return provincia; }

    public void setProvincia(String provincia) { this.provincia = provincia;}

    public BigDecimal getPrecioAlquiler() { return precioAlquiler; }
    public void setPrecioAlquiler(BigDecimal precioAlquiler) { this.precioAlquiler = precioAlquiler; }

    public String getImagenUrl() { return imagenUrl; }
    public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }

    public Boolean getDisponible() { return disponible; }
    public void setDisponible(Boolean disponible) { this.disponible = disponible; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
}
