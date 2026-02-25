package com.alquiler.alquileres.mapper;

import com.alquiler.alquileres.dto.ReservaDTO;
import com.alquiler.alquileres.model.Reserva;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReservaMapper {

   
    @Mapping(target = "clienteId", source = "cliente.id")
    @Mapping(target = "propiedadId", source = "propiedad.id")
    ReservaDTO toDto(Reserva reserva);

    
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "propiedad", ignore = true)
    Reserva toEntity(ReservaDTO reservaDTO);
}