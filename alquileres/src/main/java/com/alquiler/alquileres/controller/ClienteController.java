package com.alquiler.alquileres.controller;

import java.util.List;
import java.util.stream.Collectors;

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
import com.alquiler.alquileres.dto.ClienteRequestDto;
import com.alquiler.alquileres.dto.ClienteResponseDto;
import com.alquiler.alquileres.mapper.ClienteMapper;
import com.alquiler.alquileres.model.Cliente;
import com.alquiler.alquileres.service.ClienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService service;
    private final ClienteMapper mapper;

    public ClienteController(ClienteService service, ClienteMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

   
    //  GET - Listar todos los clientes
    
    @GetMapping
    public ResponseEntity<ApiResponseSuccessDto<List<ClienteResponseDto>>> findAll() {
        List<Cliente> lista = service.findAll();
        List<ClienteResponseDto> data = lista.stream()
                .map(mapper::toResponseDto)
                .collect(Collectors.toList());
        ApiResponseSuccessDto<List<ClienteResponseDto>> resp =
                new ApiResponseSuccessDto<>(true,
                        data.isEmpty() ? "No hay clientes disponibles" : "Listado de clientes",
                        data);
        return ResponseEntity.ok(resp);
    }

    // ═══════════════════════════════════════════════════════════════
    // 2️⃣ GET /{id} - Buscar un cliente por ID
    // ═══════════════════════════════════════════════════════════════
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseSuccessDto<ClienteResponseDto>> findById(@PathVariable Long id) {
        Cliente cliente = service.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        ClienteResponseDto dto = mapper.toResponseDto(cliente);
        ApiResponseSuccessDto<ClienteResponseDto> resp =
                new ApiResponseSuccessDto<>(true, "Cliente encontrado", dto);
        return ResponseEntity.ok(resp);
    }

    // ═══════════════════════════════════════════════════════════════
    // 3️⃣ POST - Crear nuevo cliente
    // ═══════════════════════════════════════════════════════════════
    @PostMapping
    public ResponseEntity<ApiResponseSuccessDto<ClienteResponseDto>> create(
            @Valid @RequestBody ClienteRequestDto body) {
        Cliente cliente = mapper.toEntity(body);
        Cliente creada = service.save(cliente);
        ClienteResponseDto dto = mapper.toResponseDto(creada);
        ApiResponseSuccessDto<ClienteResponseDto> resp =
                new ApiResponseSuccessDto<>(true, "Cliente creado correctamente", dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    // ═══════════════════════════════════════════════════════════════
    // 4️⃣ PUT /{id} - Actualizar cliente existente
    // ═══════════════════════════════════════════════════════════════
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseSuccessDto<ClienteResponseDto>> update(
            @PathVariable Long id,
            @Valid @RequestBody ClienteRequestDto body) {
        service.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        Cliente cliente = mapper.toEntity(body);
        cliente.setId(id);
        Cliente actualizada = service.save(cliente);
        ClienteResponseDto dto = mapper.toResponseDto(actualizada);
        ApiResponseSuccessDto<ClienteResponseDto> resp =
                new ApiResponseSuccessDto<>(true, "Cliente actualizado correctamente", dto);
        return ResponseEntity.ok(resp);
    }

    // ═══════════════════════════════════════════════════════════════
    // 5️⃣ DELETE /{id} - Eliminar cliente
    // ═══════════════════════════════════════════════════════════════
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseSuccessDto<String>> delete(@PathVariable Long id) {
        service.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        service.deleteById(id);
        ApiResponseSuccessDto<String> resp =
                new ApiResponseSuccessDto<>(true, "Cliente eliminado correctamente", "Id: " + id);
        return ResponseEntity.ok(resp);
    }
}
