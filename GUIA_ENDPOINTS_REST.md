# 📚 GUÍA COMPLETA: CÓMO ARMAR UN ENDPOINT REST EN SPRING BOOT

## Índice
1. [Anatomía de un Endpoint](#anatomía-de-un-endpoint)
2. [Anotaciones Spring Core](#anotaciones-spring-core)
3. [HTTP Methods y Mapeos](#http-methods-y-mapeos)
4. [Validación con JSR-380](#validación-con-jsr-380)
5. [Estructura Recomendada](#estructura-recomendada)
6. [CRUD Completo Paso a Paso](#crud-completo-paso-a-paso)
7. [Manejo de Errores](#manejo-de-errores)
8. [Buenas Prácticas](#buenas-prácticas)

---

## 1. ANATOMÍA DE UN ENDPOINT

### ¿Qué es un Endpoint?
Un endpoint es una URL + método HTTP que ejecuta una acción específica en tu servidor.

**Ejemplo:**
```
GET http://localhost:8080/api/clientes/5
```

### Estructura Básica en Spring:
```java
@RestController                    // 1. DECORADOR: Clase que maneja HTTP
@RequestMapping("/api/clientes")   // 2. RUTA BASE: Prefijo de todos los endpoints
public class ClienteController {

    private final ClienteService service;  // 3. DEPENDENCIA: Lógica de negocio
    
    public ClienteController(ClienteService service) {  // 4. INYECCIÓN: Via constructor
        this.service = service;
    }

    @GetMapping            // 5. MÉTODO HTTP: GET
    public ResponseEntity<ApiResponseSuccessDto<List<ClienteResponseDto>>> findAll() {
        // 6. LÓGICA: Obtener datos
        // 7. RESPUESTA: Devolver JSON
    }
}
```

---

## 2. ANOTACIONES SPRING CORE

### 2.1 Anotaciones de Clase

| Anotación | Uso | Retorna |
|-----------|-----|---------|
| `@RestController` | Controlador REST (JSON) | JSON + HTTP |
| `@Controller` | Controlador MVC (HTML) | HTML + vistas |
| `@Service` | Capa de lógica de negocio | - |
| `@Repository` | Acceso a BD (JPA) | - |
| `@Component` | Componente genérico | - |

```java
// ✅ CORRECTO - Para APIs REST
@RestController
@RequestMapping("/api/clientes")
public class ClienteController { }

// ✅ CORRECTO - Para vistas HTML
@Controller
@RequestMapping("/clientes")
public class ClienteController { }
```

### 2.2 Anotaciones de Ruta

| Anotación | HTTP Method | Uso |
|-----------|-------------|-----|
| `@GetMapping` | GET | Leer datos |
| `@PostMapping` | POST | Crear datos |
| `@PutMapping` | PUT | Actualizar completo |
| `@PatchMapping` | PATCH | Actualizar parcial |
| `@DeleteMapping` | DELETE | Eliminar |

```java
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    // GET /api/clientes
    @GetMapping
    public ResponseEntity<...> getAll() { }

    // GET /api/clientes/5
    @GetMapping("/{id}")
    public ResponseEntity<...> getById(@PathVariable Long id) { }

    // POST /api/clientes
    @PostMapping
    public ResponseEntity<...> create(@RequestBody ClienteRequestDto dto) { }

    // PUT /api/clientes/5
    @PutMapping("/{id}")
    public ResponseEntity<...> update(@PathVariable Long id, @RequestBody ClienteRequestDto dto) { }

    // DELETE /api/clientes/5
    @DeleteMapping("/{id}")
    public ResponseEntity<...> delete(@PathVariable Long id) { }
}
```

### 2.3 Anotaciones de Parámetros

| Anotación | Captura desde | Ejemplo |
|-----------|---------------|---------|
| `@PathVariable` | URL path | `/clientes/{id}` → `id` |
| `@RequestParam` | Query string | `/clientes?estado=activo` → `estado` |
| `@RequestBody` | JSON en body | POST con JSON |
| `@RequestHeader` | Headers HTTP | `Authorization: Bearer token` |
| `@Valid` | Validación | Validar DTOs |

```java
// 1️⃣ PathVariable - Parámetro en la ruta
@GetMapping("/{id}")
public ResponseEntity<...> getById(@PathVariable Long id) {
    // GET /api/clientes/5  →  id=5
}

// 2️⃣ RequestParam - Query string
@GetMapping("/buscar")
public ResponseEntity<...> search(@RequestParam String nombre, @RequestParam(required = false) String estado) {
    // GET /api/clientes/buscar?nombre=Juan&estado=activo
}

// 3️⃣ RequestBody - JSON en body
@PostMapping
public ResponseEntity<...> create(@RequestBody ClienteRequestDto dto) {
    // POST /api/clientes  +  JSON -> ClienteRequestDto
}

// 4️⃣ Valid - Validación de entrada
@PostMapping
public ResponseEntity<...> create(@Valid @RequestBody ClienteRequestDto dto) {
    // Si @Valid falla, devuelve 400 Bad Request
}

// 5️⃣ RequestHeader - Headers HTTP
@GetMapping
public ResponseEntity<...> getAll(@RequestHeader(value = "Authorization") String token) {
    // Leer header Authorization
}
```

---

## 3. HTTP METHODS Y MAPEOS

### Tabla de Métodos HTTP

| Método | CRUD | Seguro | Idempotente | Cacheable |
|--------|------|--------|-------------|-----------|
| GET | Read | ✅ | ✅ | ✅ |
| POST | Create | ❌ | ❌ | ❌ |
| PUT | Update (completo) | ❌ | ✅ | ❌ |
| PATCH | Update (parcial) | ❌ | ❌ | ❌ |
| DELETE | Delete | ❌ | ✅ | ❌ |

### Códigos HTTP Comunes

| Código | Significado | Uso |
|--------|------------|-----|
| 200 OK | Éxito | GET, PUT, DELETE exitosos |
| 201 Created | Recurso creado | POST exitoso |
| 204 No Content | Sin contenido | DELETE sin retorno |
| 400 Bad Request | Solicitud inválida | Parámetros incorrectos |
| 401 Unauthorized | No autorizado | Falta autenticación |
| 403 Forbidden | Prohibido | Sin permisos |
| 404 Not Found | No existe | Recurso no encontrado |
| 500 Server Error | Error servidor | Excepción no manejada |

### Ejemplos de Mapeos

```java
// ✅ Mapeo completo (PUT - Actualizar TODO)
@PutMapping("/{id}")
public ResponseEntity<...> update(@PathVariable Long id, @Valid @RequestBody ClienteRequestDto dto) {
    // Reemplaza TODOS los campos del cliente
}

// ✅ Mapeo parcial (PATCH - Actualizar PARCIAL)
@PatchMapping("/{id}")
public ResponseEntity<...> partialUpdate(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
    // Actualiza solo los campos enviados
}

// ⚠️ Alternativa: POST para acciones no-CRUD
@PostMapping("/{id}/activar")
public ResponseEntity<...> activate(@PathVariable Long id) {
    // Acción específica: no es un POST de creación puro
}
```

---

## 4. VALIDACIÓN CON JSR-380

### Anotaciones de Validación

| Anotación | Valida | Ejemplo |
|-----------|--------|---------|
| `@NotNull` | No nulo | `private Long id;` |
| `@NotBlank` | No vacío (strings) | `@NotBlank private String nombre;` |
| `@NotEmpty` | Colecciones no vacías | `@NotEmpty private List<String> items;` |
| `@Size` | Tamaño de string/colección | `@Size(min=2, max=100) private String nombre;` |
| `@Min / @Max` | Rango numérico | `@Min(0) private int edad;` |
| `@Email` | Formato email | `@Email private String email;` |
| `@Pattern` | Regex | `@Pattern(regexp="[0-9]{8}") private String dni;` |
| `@Positive` | Número positivo | `@Positive private BigDecimal precio;` |
| `@PastOrPresent` | Fecha pasada/presente | `@PastOrPresent private LocalDate fecha;` |

### Ejemplo Completo

```java
package com.alquiler.alquileres.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class ClienteRequestDto {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "Entre 2 y 100 caracteres")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;

    @NotBlank(message = "Email requerido")
    @Email(message = "Email debe ser válido")
    private String email;

    @Size(max = 20)
    private String telefono;

    @NotBlank(message = "DNI requerido")
    @Pattern(regexp = "[0-9]{8}", message = "DNI debe tener 8 dígitos")
    private String dni;

    @Min(value = 0, message = "No puede ser negativo")
    private BigDecimal saldo;

    @PastOrPresent(message = "Fecha no puede ser futura")
    private LocalDate fechaNacimiento;

    // CONSTRUCTORES, GETTERS, SETTERS...
}
```

### Usar @Valid en Controller

```java
@PostMapping
public ResponseEntity<ApiResponseSuccessDto<ClienteResponseDto>> create(
        @Valid @RequestBody ClienteRequestDto body) {  // ← @Valid activa validación
    // Si hay error, Spring devuelve 400 automáticamente
    // No necesitas revisar validaciones manualmente
}
```

---

## 5. ESTRUCTURA RECOMENDADA

### Capas de tu Aplicación

```
src/main/java/com/alquiler/alquileres/
│
├── controller/              ← APIs REST (@RestController)
│   ├── ClienteController.java
│   ├── PropiedadController.java
│   └── ReservaController.java
│
├── service/                 ← Lógica de negocio (@Service)
│   ├── ClienteService.java
│   ├── PropiedadService.java
│   └── ReservaService.java
│
├── repository/              ← Acceso a BD (@Repository, extends JpaRepository)
│   ├── ClienteRepository.java
│   ├── PropiedadRepository.java
│   └── ReservaRepository.java
│
├── model/                   ← Entidades JPA (@Entity)
│   ├── Cliente.java
│   ├── Propiedades.java
│   └── Reserva.java
│
├── dto/                     ← Objetos de transferencia de datos
│   ├── ClienteRequestDto.java       (para POST/PUT - con validaciones)
│   ├── ClienteResponseDto.java      (para GET - sin validaciones)
│   ├── PropiedadRequestDto.java
│   ├── PropiedadResponseDto.java
│   ├── ApiResponseSuccessDto.java   (envolvedor de respuestas)
│   └── ApiResponseErrorDto.java     (para errores)
│
├── mapper/                  ← Mapeo Entity ↔ DTO (MapStruct)
│   ├── ClienteMapper.java
│   ├── PropiedadMapper.java
│   └── ReservaMapper.java
│
├── exception/               ← Manejo de excepciones
│   ├── GlobalExceptionHandler.java
│   └── ResourceNotFoundException.java
│
└── config/                  ← Configuración
    └── SecurityConfig.java
```

### Flujo de una Solicitud

```
1. Cliente → POST /api/clientes (JSON)
   ↓
2. Spring → ClienteController.create()
   ↓
3. @Valid valida ClienteRequestDto
   ↓
4. Controller → ClienteService.save()
   ↓
5. Mapper (Request → Entity)
   ↓
6. Service → ClienteRepository.save()
   ↓
7. BD guarda entidad
   ↓
8. Mapper (Entity → Response)
   ↓
9. ApiResponseSuccessDto envuelve response
   ↓
10. ResponseEntity con código 201
   ↓
11. Spring → JSON response
   ↓
12. Cliente recibe respuesta
```

---

## 6. CRUD COMPLETO PASO A PASO

### PASO 1: Crear ModeloEntity)

```java
package com.alquiler.alquileres.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String apellido;

    @Column(unique = true, nullable = false)
    private String email;

    private String telefono;

    @Column(unique = true, nullable = false)
    private String dni;

    private LocalDate fechaRegistro = LocalDate.now();
    private boolean activo = true;

    // CONSTRUCTORES, GETTERS, SETTERS...
    
    public Cliente() {}
    
    public Cliente(String nombre, String apellido, String email, String telefono, String dni) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
        this.dni = dni;
    }
}
```

### PASO 2: Crear DTOs (Request y Response)

**ClienteRequestDto.java** (para POST/PUT - CON validaciones):
```java
package com.alquiler.alquileres.dto;

import jakarta.validation.constraints.*;

public class ClienteRequestDto {

    @NotBlank(message = "Nombre obligatorio")
    @Size(min = 2, max = 100)
    private String nombre;

    @NotBlank(message = "Apellido obligatorio")
    private String apellido;

    @Email
    private String email;

    @Size(max = 20)
    private String telefono;

    @Pattern(regexp = "[0-9]{8}")
    private String dni;

    // CONSTRUCTORES, GETTERS, SETTERS...
}
```

**ClienteResponseDto.java** (para GET - SIN validaciones):
```java
package com.alquiler.alquileres.dto;

import java.time.LocalDate;

public class ClienteResponseDto {
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String dni;
    private LocalDate fechaRegistro;
    private boolean activo;

    // CONSTRUCTORES, GETTERS, SETTERS...
}
```

### PASO 3: Crear Mapper (ENTIDAD ↔ DTO)

```java
package com.alquiler.alquileres.mapper;

import com.alquiler.alquileres.model.Cliente;
import com.alquiler.alquileres.dto.ClienteRequestDto;
import com.alquiler.alquileres.dto.ClienteResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    // RequestDto → Entity (para POST/PUT)
    @SuppressWarnings("null")
    Cliente toEntity(ClienteRequestDto requestDto);

    // Entity → ResponseDto (para GET)
    ClienteResponseDto toResponseDto(Cliente cliente);
}
```

### PASO 4: Crear Repository

```java
package com.alquiler.alquileres.repository;

import com.alquiler.alquileres.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    // JpaRepository proporciona: save, findById, findAll, delete, etc.
    // Puedes agregar métodos personalizados:
    Cliente findByEmail(String email);
    Cliente findByDni(String dni);
}
```

### PASO 5: Crear Service

```java
package com.alquiler.alquileres.service;

import com.alquiler.alquileres.model.Cliente;
import com.alquiler.alquileres.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    // CREAR
    public Cliente create(Cliente cliente) {
        return repository.save(cliente);
    }

    // LEER (TODO)
    public List<Cliente> findAll() {
        return repository.findAll();
    }

    // LEER (UNO)
    public Optional<Cliente> findById(Long id) {
        return repository.findById(id);
    }

    // ACTUALIZAR
    public Cliente update(Long id, Cliente cliente) {
        cliente.setId(id);
        return repository.save(cliente);
    }

    // ELIMINAR
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
```

### PASO 6: Crear Controller (REST)

```java
package com.alquiler.alquileres.controller;

import com.alquiler.alquileres.dto.ApiResponseSuccessDto;
import com.alquiler.alquileres.dto.ClienteRequestDto;
import com.alquiler.alquileres.dto.ClienteResponseDto;
import com.alquiler.alquileres.mapper.ClienteMapper;
import com.alquiler.alquileres.model.Cliente;
import com.alquiler.alquileres.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService service;
    private final ClienteMapper mapper;

    public ClienteController(ClienteService service, ClienteMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    // 1️⃣ GET /api/clientes - Obtener todos
    @GetMapping
    public ResponseEntity<ApiResponseSuccessDto<List<ClienteResponseDto>>> getAll() {
        List<Cliente> clientes = service.findAll();
        List<ClienteResponseDto> data = clientes.stream()
                .map(mapper::toResponseDto)
                .collect(Collectors.toList());
        ApiResponseSuccessDto<List<ClienteResponseDto>> resp =
                new ApiResponseSuccessDto<>(true, "Listado de clientes", data);
        return ResponseEntity.ok(resp);
    }

    // 2️⃣ GET /api/clientes/{id} - Obtener uno
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseSuccessDto<ClienteResponseDto>> getById(@PathVariable Long id) {
        Cliente cliente = service.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        ClienteResponseDto dto = mapper.toResponseDto(cliente);
        ApiResponseSuccessDto<ClienteResponseDto> resp =
                new ApiResponseSuccessDto<>(true, "Cliente encontrado", dto);
        return ResponseEntity.ok(resp);
    }

    // 3️⃣ POST /api/clientes - Crear
    @PostMapping
    public ResponseEntity<ApiResponseSuccessDto<ClienteResponseDto>> create(
            @Valid @RequestBody ClienteRequestDto body) {
        Cliente cliente = mapper.toEntity(body);
        Cliente creado = service.create(cliente);
        ClienteResponseDto dto = mapper.toResponseDto(creado);
        ApiResponseSuccessDto<ClienteResponseDto> resp =
                new ApiResponseSuccessDto<>(true, "Cliente creado", dto);
        return ResponseEntity.status(201).body(resp);  // 201 Created
    }

    // 4️⃣ PUT /api/clientes/{id} - Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseSuccessDto<ClienteResponseDto>> update(
            @PathVariable Long id,
            @Valid @RequestBody ClienteRequestDto body) {
        service.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        Cliente cliente = mapper.toEntity(body);
        Cliente actualizado = service.update(id, cliente);
        ClienteResponseDto dto = mapper.toResponseDto(actualizado);
        ApiResponseSuccessDto<ClienteResponseDto> resp =
                new ApiResponseSuccessDto<>(true, "Cliente actualizado", dto);
        return ResponseEntity.ok(resp);
    }

    // 5️⃣ DELETE /api/clientes/{id} - Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseSuccessDto<String>> delete(@PathVariable Long id) {
        service.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        service.delete(id);
        ApiResponseSuccessDto<String> resp =
                new ApiResponseSuccessDto<>(true, "Cliente eliminado", "Id: " + id);
        return ResponseEntity.ok(resp);
    }
}
```

---

## 7. MANEJO DE ERRORES

### GlobalExceptionHandler.java

```java
package com.alquiler.alquileres.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice  // Maneja excepciones de TODOS los controllers
public class GlobalExceptionHandler {

    // Validación fallida (errores de @Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseErrorDto> handleValidationException(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.joining(", "));
        
        ApiResponseErrorDto resp = new ApiResponseErrorDto(false, message, null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
    }

    // Recurso no encontrado
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponseErrorDto> handleNotFound(RuntimeException ex) {
        ApiResponseErrorDto resp = new ApiResponseErrorDto(false, ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp);
    }

    // Error genérico
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseErrorDto> handleGenericException(Exception ex) {
        ApiResponseErrorDto resp = new ApiResponseErrorDto(false, "Error en el servidor", null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp);
    }
}
```

### ApiResponseErrorDto.java

```java
package com.alquiler.alquileres.dto;

public class ApiResponseErrorDto {
    private boolean success;
    private String message;
    private String details;

    public ApiResponseErrorDto(boolean success, String message, String details) {
        this.success = success;
        this.message = message;
        this.details = details;
    }

    // GETTERS, SETTERS...
}
```

---

## 8. BUENAS PRÁCTICAS

### ✅ DO's

```java
// ✅ 1. Usar DTOs separados para request/response
@PostMapping
public ResponseEntity<ApiResponseSuccessDto<ClienteResponseDto>> create(
        @Valid @RequestBody ClienteRequestDto body) {  // ← DTO input
    // ...
    return ResponseEntity.status(201).body(resp);  // ← DTO output
}

// ✅ 2. Validar con @Valid
@PostMapping
public ResponseEntity<...> create(@Valid @RequestBody ClienteRequestDto body) {
    // @Valid automáticamente revisa todas las validaciones
}

// ✅ 3. Usar constructores para inyección
private final ClienteService service;
public ClienteController(ClienteService service) {
    this.service = service;
}

// ✅ 4. Usar Optional para buscar por ID
public Optional<Cliente> findById(Long id) {
    return repository.findById(id);
}

// ✅ 5. Asignar códigos HTTP correctos
return ResponseEntity.status(201).body(resp);      // POST creación
return ResponseEntity.ok(resp);                    // GET, PUT, DELETE
return ResponseEntity.noContent().build();         // DELETE sin retorno

// ✅ 6. Usar mapper para conversión
Cliente cliente = mapper.toEntity(body);
ClienteResponseDto dto = mapper.toResponseDto(saved);

// ✅ 7. Nombres de rutas claros y RESTful
GET    /api/clientes           # Listar todos
GET    /api/clientes/{id}      # Obtener uno
POST   /api/clientes           # Crear
PUT    /api/clientes/{id}      # Actualizar
DELETE /api/clientes/{id}      # Eliminar
```

### ❌ DON'Ts

```java
// ❌ 1.NO mezclar @Controller con ResponseEntity
@Controller  // ← Devuelve vistas HTML
@GetMapping
public ResponseEntity<List<Cliente>> getAll() {  // ← Los datos JSON
}

// ❌ 2. NO exponer entidades JPA directamente
@PostMapping
public ResponseEntity<Cliente> create(@RequestBody Cliente cliente) {  // ❌
    // Usa DTOs en su lugar
}

// ❌ 3. NO usar @Autowired
@Autowired  // ❌ Deprecated en versiones nuevas
private ClienteService service;

// ✅ Usa constructor injection en su lugar
private final ClienteService service;
public ClienteController(ClienteService service) {
    this.service = service;
}

// ❌ 4. NO validar manualmente
@PostMapping
public ResponseEntity<...> create(@RequestBody ClienteRequestDto body) {
    if (body.getNombre() == null) {  // ❌ Validación manual
        return ResponseEntity.badRequest().build();
    }
}

// ✅ Usa @Valid
@PostMapping
public ResponseEntity<...> create(@Valid @RequestBody ClienteRequestDto body) {
    // Spring valida automáticamente
}

// ❌ 5. NO mezclar POST con GET
@GetMapping("/crear")  // ❌ Esto debería ser POST
public ResponseEntity<...> crear() { }

// ✅ 
@PostMapping
public ResponseEntity<...> create() { }
```

---

## CHECKLIST: Verifica tu Endpoint ANTES de publicar

- [ ] ¿Tiene `@RestController` en la clase?
- [ ] ¿Tiene `@RequestMapping` con la ruta base?
- [ ] ¿El método tiene `@GetMapping`, `@PostMapping`, etc.?
- [ ] ¿Usa DTOs request/response (no entidades)?
- [ ] ¿Tiene `@Valid` en parámetros que reciben datos?
- [ ] ¿Las validaciones están en el DTO (RequestDto)?
- [ ] ¿Usa `ResponseEntity` con código HTTP correcto?
- [ ] ¿Envuelve respuesta en `ApiResponseSuccessDto`?
- [ ] ¿El mapper convierte Entity ↔ DTO?
- [ ] ¿Maneja excepciones con `@ExceptionHandler`?
- [ ] ¿Retorna datos mapeados (Entity → ResponseDto)?
- [ ] ¿Los nombres de rutas son claros y RESTful?
- [ ] ¿Probaste en Postman/Insomnia?

---

## REFERENCIA RÁPIDA: Plantilla de Controller

```java
package com.alquiler.alquileres.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/TU_RECURSO")
public class TuController {

    private final TuService service;
    private final TuMapper mapper;

    public TuController(TuService service, TuMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    // GET - Listar todos
    @GetMapping
    public ResponseEntity<ApiResponseSuccessDto<List<TuResponseDto>>> getAll() {
        List<TuEntity> lista = service.findAll();
        List<TuResponseDto> data = lista.stream()
                .map(mapper::toResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new ApiResponseSuccessDto<>(true, "Listado", data));
    }

    // GET - Obtener uno
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseSuccessDto<TuResponseDto>> getById(@PathVariable Long id) {
        TuEntity entity = service.findById(id)
                .orElseThrow(() -> new RuntimeException("No encontrado"));
        TuResponseDto dto = mapper.toResponseDto(entity);
        return ResponseEntity.ok(new ApiResponseSuccessDto<>(true, "Encontrado", dto));
    }

    // POST - Crear
    @PostMapping
    public ResponseEntity<ApiResponseSuccessDto<TuResponseDto>> create(
            @Valid @RequestBody TuRequestDto body) {
        TuEntity entity = mapper.toEntity(body);
        TuEntity creado = service.create(entity);
        TuResponseDto dto = mapper.toResponseDto(creado);
        return ResponseEntity.status(201).body(new ApiResponseSuccessDto<>(true, "Creado", dto));
    }

    // PUT - Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseSuccessDto<TuResponseDto>> update(
            @PathVariable Long id,
            @Valid @RequestBody TuRequestDto body) {
        service.findById(id).orElseThrow(() -> new RuntimeException("No encontrado"));
        TuEntity entity = mapper.toEntity(body);
        TuEntity actualizado = service.update(id, entity);
        TuResponseDto dto = mapper.toResponseDto(actualizado);
        return ResponseEntity.ok(new ApiResponseSuccessDto<>(true, "Actualizado", dto));
    }

    // DELETE - Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseSuccessDto<String>> delete(@PathVariable Long id) {
        service.findById(id).orElseThrow(() -> new RuntimeException("No encontrado"));
        service.delete(id);
        return ResponseEntity.ok(new ApiResponseSuccessDto<>(true, "Eliminado", "Id: " + id));
    }
}
```

---

## PASOS FINALES: Cómo Armar un Endpoint Desde Cero

### Ejemplo: Crear PropiedadController

**PASO 1:** Define el modelo (si no existe)
```java
@Entity
public class Propiedad {
    @Id @GeneratedValue
    private Long id;
    @NotBlank
    private String titulo;
    // ... más campos
}
```

**PASO 2:** Crea RequesDtos con @Valid
```java
public class PropiedadRequestDto {
    @NotBlank(message = "Título requerido")
    private String titulo;
    // ...
}
```

**PASO 3:** Crea responseDtos (sin validación)
```java
public class PropiedadResponseDto {
    private Long id;
    private String titulo;
    // ... mismos campos que Entity
}
```

**PASO 4:** Crea Mapper
```java
@Mapper(componentModel = "spring")
public interface PropiedadMapper {
    Propiedad toEntity(PropiedadRequestDto dto);
    PropiedadResponseDto toResponseDto(Propiedad entity);
}
```

**PASO 5:** Crea Repository
```java
@Repository
public interface PropiedadRepository extends JpaRepository<Propiedad, Long> {}
```

**PASO 6:** Crea Service
```java
@Service
public class PropiedadService {
    private final PropiedadRepository repo;
    
    public PropiedadService(PropiedadRepository repo) {
        this.repo = repo;
    }
    
    public List<Propiedad> findAll() { return repo.findAll(); }
    public Optional<Propiedad> findById(Long id) { return repo.findById(id); }
    public Propiedad create(Propiedad p) { return repo.save(p); }
    public void delete(Long id) { repo.deleteById(id); }
}
```

**PASO 7:** Crea Controller (COPIA LA PLANTILLA DE ARRIBA y personaliza)
```java
@RestController
@RequestMapping("/api/propiedades")
public class PropiedadController {
    // ... sigue el patrón ...
}
```

---

## ¡Listo! 🎉

Ahora tienes una guía completa para:
- ✅ Entender anatomía de endpoints
- ✅ Conocer TODAS las anotaciones Spring
- ✅ Validar datos correctamente
- ✅ Estructurar tu proyecto
- ✅ Crear CRUD completo
- ✅ Manejar errores
- ✅ Armar endpoints desde cero

**¿Dudas? Revisa esta guía y sigue los pasos. ¡Puedes hacerlo!**
