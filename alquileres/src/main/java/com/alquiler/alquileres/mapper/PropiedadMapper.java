package com.alquiler.alquileres.mapper;

import org.mapstruct.Mapper;
import com.alquiler.alquileres.dto.PropiedadDTO;
import com.alquiler.alquileres.model.Propiedades;

@Mapper(componentModel = "spring")
public interface PropiedadMapper {

    PropiedadDTO toDto(Propiedades propiedad);

    Propiedades toEntity(PropiedadDTO propiedadDto);
}