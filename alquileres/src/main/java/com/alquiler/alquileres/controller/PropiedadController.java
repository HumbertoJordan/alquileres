package com.alquiler.alquileres.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.alquiler.alquileres.dto.PropiedadDTO;
import com.alquiler.alquileres.service.PropiedadService;

@RestController
@RequestMapping("/api/propiedades")
public class PropiedadController {

    private final PropiedadService service;
    private static final String UPLOAD_DIR = "src/main/resources/static/img/propiedades/";

    public PropiedadController(PropiedadService service) {
        this.service = service;
    }

    @GetMapping
    public List<PropiedadDTO> listar() {
        return service.findAll();
    }

    @PostMapping
    public ResponseEntity<PropiedadDTO> crear(@RequestBody PropiedadDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
    }

    @PutMapping("/{id}")
    public PropiedadDTO actualizar(@PathVariable Long id, @RequestBody PropiedadDTO dto) {
        dto.setId(id);
        return service.save(dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.deleteById(id);
    }

    @PostMapping("/{id}/imagen")
    public ResponseEntity<String> subirImagen(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {

        PropiedadDTO propiedad = service.findById(id)
                .orElseThrow(() -> new RuntimeException("Propiedad no encontrada"));

        try {
            String nombre = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path ruta = Paths.get(UPLOAD_DIR + nombre);
            Files.createDirectories(ruta.getParent());
            Files.write(ruta, file.getBytes());

            propiedad.setImagenUrl("/img/propiedades/" + nombre);
            service.save(propiedad);

            return ResponseEntity.ok("Imagen subida correctamente");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al subir imagen");
        }
    }
}