package com.alquiler.alquileres.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alquiler.alquileres.dto.ApiResponseSuccessDto;
import com.alquiler.alquileres.dto.ReservaDTO;
import com.alquiler.alquileres.service.ReservaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    private final ReservaService service;

    public ReservaController(ReservaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<ApiResponseSuccessDto<List<ReservaDTO>>> findAll() {
        List<ReservaDTO> data = service.findAll();
        ApiResponseSuccessDto<List<ReservaDTO>> resp =
                new ApiResponseSuccessDto<>(true,
                        data.isEmpty() ? "No hay reservas disponibles" : "Listado de reservas",
                        data);
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseSuccessDto<ReservaDTO>> findById(@PathVariable Long id) {
        ReservaDTO dto = service.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        ApiResponseSuccessDto<ReservaDTO> resp =
                new ApiResponseSuccessDto<>(true, "Reserva encontrada", dto);
        return ResponseEntity.ok(resp);
    }

    @PostMapping
    public ResponseEntity<ApiResponseSuccessDto<ReservaDTO>> create(
            @Valid @RequestBody ReservaDTO body) {
        ReservaDTO creada = service.save(body);
        ApiResponseSuccessDto<ReservaDTO> resp =
                new ApiResponseSuccessDto<>(true, "Reserva creada correctamente", creada);
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseSuccessDto<ReservaDTO>> update(
            @PathVariable Long id,
            @Valid @RequestBody ReservaDTO body) {
        service.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        body.setId(id);
        ReservaDTO actualizada = service.save(body);
        ApiResponseSuccessDto<ReservaDTO> resp =
                new ApiResponseSuccessDto<>(true, "Reserva actualizada correctamente", actualizada);
        return ResponseEntity.ok(resp);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseSuccessDto<String>> delete(@PathVariable Long id) {
        service.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        service.deleteById(id);
        ApiResponseSuccessDto<String> resp =
                new ApiResponseSuccessDto<>(true, "Reserva eliminada correctamente", "");
        return ResponseEntity.ok(resp);
    }
}
