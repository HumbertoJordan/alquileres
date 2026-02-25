package com.alquiler.alquileres.dto;

import java.time.LocalDate;

public class ReservaDTO {

    private Long id;
    private Long clienteId;
    private Long propiedadId;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Integer precioTotal;
    private String estado;

    public ReservaDTO() {
    }

    public ReservaDTO(Long id, Long clienteId, Long propiedadId, LocalDate fechaInicio,
                     LocalDate fechaFin, Integer precioTotal, String estado) {
        this.id = id;
        this.clienteId = clienteId;
        this.propiedadId = propiedadId;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.precioTotal = precioTotal;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Long getPropiedadId() {
        return propiedadId;
    }

    public void setPropiedadId(Long propiedadId) {
        this.propiedadId = propiedadId;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Integer getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(Integer precioTotal) {
        this.precioTotal = precioTotal;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
