package com.alquiler.alquileres.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.alquiler.alquileres.dto.ReservaDTO;
import com.alquiler.alquileres.mapper.ReservaMapper;
import com.alquiler.alquileres.model.Reserva;
import com.alquiler.alquileres.repository.ReservaRepository;

@Service
public class ReservaService {
    
    private final ReservaRepository reservaRepository;
    private final ReservaMapper reservaMapper;

    public ReservaService(ReservaRepository reservaRepository, ReservaMapper reservaMapper) {
        this.reservaRepository = reservaRepository;
        this.reservaMapper = reservaMapper;
    }

    public List<ReservaDTO> findAll() {
        return reservaRepository.findAll().stream()
                .map(reservaMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<ReservaDTO> findById(Long id) {
        return reservaRepository.findById(id).map(reservaMapper::toDto);
    }

    public ReservaDTO save(ReservaDTO reservaDTO) {
        Reserva reserva = reservaMapper.toEntity(reservaDTO);
        Reserva saved = reservaRepository.save(reserva);
        return reservaMapper.toDto(saved);
    }

    public Optional<ReservaDTO> update(Long id, ReservaDTO reservaDTO) {
        if (reservaRepository.existsById(id)) {
            reservaDTO.setId(id);
            Reserva reserva = reservaMapper.toEntity(reservaDTO);
            Reserva updated = reservaRepository.save(reserva);
            return Optional.of(reservaMapper.toDto(updated));
        }
        return Optional.empty();
    }

    public void deleteById(Long id) {
        reservaRepository.deleteById(id);
    }
}
