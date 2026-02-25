# Sistema de Alquiler de Casas con Pileta 🏊

Sistema web desarrollado en Spring Boot con Thymeleaf para la gestión de alquiler de casas con pileta.

## 🚀 Características

- ✅ Gestión de Propiedades (CRUD completo)
- ✅ Gestión de Clientes (CRUD completo)
- ✅ Gestión de Reservas (CRUD completo)
- ✅ API REST para todas las entidades
- ✅ Interfaz web con Thymeleaf
- ✅ Diseño responsive con Bootstrap 5
- ✅ Validaciones de formularios

## 📋 Requisitos Previos

- Java 17 o superior
- Maven 3.6+
- MySQL/PostgreSQL (o la base de datos que estés usando)
- Git

## 🛠️ Instalación

### 1. Clonar el repositorio

```bash
git clone https://github.com/tu-usuario/alquiler-casas.git
cd alquiler-casas
```

### 2. Configurar la base de datos

Edita el archivo `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/alquiler_db
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### 3. Copiar los archivos del frontend

Copia los archivos de templates y static a tu proyecto:

```bash
# Copiar templates
cp -r templates/* src/main/resources/templates/

# Copiar archivos estáticos
cp -r static/* src/main/resources/static/
```

### 4. Compilar y ejecutar

```bash
mvn clean install
mvn spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8080`

## 📁 Estructura del Proyecto

```
alquiler-casas/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/alquiler/alquileres/
│   │   │       ├── controller/
│   │   │       │   ├── ClienteController.java
│   │   │       │   ├── PropiedadController.java
│   │   │       │   ├── ReservaController.java
│   │   │       │   ├── WebClienteController.java
│   │   │       │   ├── WebPropiedadController.java
│   │   │       │   ├── WebReservaController.java
│   │   │       │   └── HomeController.java
│   │   │       ├── model/
│   │   │       ├── repository/
│   │   │       ├── service/
│   │   │       ├── dto/
│   │   │       └── mapper/
│   │   └── resources/
│   │       ├── templates/
│   │       │   ├── index.html
│   │       │   ├── layout/
│   │       │   │   └── main.html
│   │       │   ├── fragments/
│   │       │   │   ├── header.html
│   │       │   │   └── footer.html
│   │       │   ├── clientes/
│   │       │   │   ├── list.html
│   │       │   │   └── form.html
│   │       │   ├── propiedades/
│   │       │   │   ├── list.html
│   │       │   │   └── form.html
│   │       │   └── reservas/
│   │       │       ├── list.html
│   │       │       └── form.html
│   │       ├── static/
│   │       │   ├── css/
│   │       │   │   └── style.css
│   │       │   └── js/
│   │       │       └── main.js
│   │       └── application.properties
└── pom.xml
```

## 🌐 Endpoints

### Interfaz Web (Thymeleaf)
- `GET /` - Página principal
- `GET /propiedades` - Listado de propiedades
- `GET /clientes` - Listado de clientes
- `GET /reservas` - Listado de reservas

### API REST
- `GET /api/propiedades` - Listar todas las propiedades
- `POST /api/propiedades` - Crear nueva propiedad
- `PUT /api/propiedades/{id}` - Actualizar propiedad
- `DELETE /api/propiedades/{id}` - Eliminar propiedad

(Lo mismo para `/api/clientes` y `/api/reservas`)

## 🎨 Tecnologías Utilizadas

- **Backend:**
  - Spring Boot 3.x
  - Spring Data JPA
  - Spring Web
  - Thymeleaf
  - Bean Validation

- **Frontend:**
  - Thymeleaf
  - Bootstrap 5.3.2
  - Font Awesome 6.4.0
  - JavaScript ES6+

## 📝 Próximas Mejoras

- [ ] Sistema de autenticación y autorización
- [ ] Subida de imágenes para propiedades
- [ ] Calendario de disponibilidad
- [ ] Sistema de pagos
- [ ] Notificaciones por email
- [ ] Panel de estadísticas
- [ ] Búsqueda y filtros avanzados
- [ ] Valoraciones y comentarios

## 👤 Autor

Humberto - jordanhmza@gmail.com

## 📄 Licencia

Este proyecto está bajo la Licencia MIT.
