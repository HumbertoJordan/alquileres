package com.alquiler.alquileres.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alquiler.alquileres.dto.ReservaDTO;
import com.alquiler.alquileres.mapper.ReservaMapper;
import com.alquiler.alquileres.model.Reserva;
import com.alquiler.alquileres.repository.ReservaRepository;

@Service
public class ReservaService {
    
    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private ReservaMapper reservaMapper;

    // Buscar todo

    public List<ReservaDTO> findAll() {
        return reservaRepository.findAll().stream()
        .map(reservaMapper::toDto)
        .collect(Collectors.toList());
        
    }
    
    // Buscar x 1
    @SuppressWarnings("null")
    public Optional<ReservaDTO> findById(Long id) {
        return reservaRepository.findById(id)
        .map(reservaMapper::toDto);        
    }

    // Guardar Reserva
    @SuppressWarnings("null")
    public ReservaDTO save(ReservaDTO reservaDTO) {
        Reserva reserva = reservaMapper.toEntity(reservaDTO);
        Reserva savedReserva = reservaRepository.save(reserva);
        return reservaMapper.toDto(savedReserva);
        
    }

    // Actualizar Reserva
    @SuppressWarnings("null")
    public Optional<ReservaDTO> update(Long id, ReservaDTO reservaDTO) {
        if (reservaRepository.existsById(id)) {
            reservaDTO.setId(id);
            Reserva reserva = reservaMapper.toEntity(reservaDTO);
            Reserva updatedReserva = reservaRepository.save(reserva);
            return Optional.of(reservaMapper.toDto(updatedReserva));
        }
        return Optional.empty();
    }

    // Eliminar Reserva
    @SuppressWarnings("null")
    public boolean deleteById(Long id) {
        if (reservaRepository.existsById(id)) {
            reservaRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
}
