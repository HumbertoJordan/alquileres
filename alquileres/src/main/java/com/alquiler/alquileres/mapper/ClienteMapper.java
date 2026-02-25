package com.alquiler.alquileres.mapper;

import com.alquiler.alquileres.dto.ClienteDTO;
import com.alquiler.alquileres.dto.ClienteRequestDto;
import com.alquiler.alquileres.dto.ClienteResponseDto;
import com.alquiler.alquileres.model.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClienteMapper {

    // Para compatibilidad con ClienteDTO anterior
    ClienteDTO toDto(Cliente cliente);

    // Para REQUEST (POST/PUT) → Entity
    // Notas: id, fechaRegistro, activo se generan en el servicio
    @SuppressWarnings("all")
    Cliente toEntity(ClienteRequestDto requestDto);

    // Para RESPONSE (GET) → DTO
    ClienteResponseDto toResponseDto(Cliente cliente);

    // Para REQUEST → ClienteDTO (si lo necesitas)
    @SuppressWarnings("all")
    ClienteDTO fromDto(ClienteRequestDto requestDto);
}
