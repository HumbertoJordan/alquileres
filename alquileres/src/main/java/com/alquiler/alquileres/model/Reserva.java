package com.alquiler.alquileres.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity 
@Table(name = "reservas")
public class Reserva {
    
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "propiedad_id", nullable = false)
    private Propiedades propiedad;
    
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Integer precioTotal;
    private String estado;
    private boolean activo = true;

       
    public Reserva() {}


    public Long getId() { return id;}

    public void setId(Long id) {this.id = id;}

    public Cliente getCliente() {return cliente;}

    public void setCliente(Cliente cliente) {this.cliente = cliente; }

    public Propiedades getPropiedad() {return propiedad;}

    public void setPropiedad(Propiedades propiedad) { this.propiedad = propiedad;  }

    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }
    public Integer getPrecioTotal() { return precioTotal; }
    public void setPrecioTotal(Integer precioTotal) { this.precioTotal = precioTotal; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
    


}
