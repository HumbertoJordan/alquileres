package com.alquiler.alquileres.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.alquiler.alquileres.dto.PropiedadDTO;
import com.alquiler.alquileres.mapper.PropiedadMapper;
import com.alquiler.alquileres.model.Propiedades;
import com.alquiler.alquileres.repository.PropiedadRepository;

@Service
public class PropiedadService {

    private final PropiedadRepository repository;
    private final PropiedadMapper mapper;

    public PropiedadService(PropiedadRepository repository, PropiedadMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<PropiedadDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<PropiedadDTO> findById(Long id) {
        return repository.findById(id).map(mapper::toDto);
    }

    public PropiedadDTO save(PropiedadDTO dto) {
        Propiedades entity = mapper.toEntity(dto);
        return mapper.toDto(repository.save(entity));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}