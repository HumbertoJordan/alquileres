package com.alquiler.alquileres.config;

import com.alquiler.alquileres.model.Rol;
import com.alquiler.alquileres.model.Usuario;
import com.alquiler.alquileres.repository.RolRepository;
import com.alquiler.alquileres.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(UsuarioRepository usuarioRepository, 
                                   RolRepository rolRepository,
                                   PasswordEncoder passwordEncoder) {
        return args -> {
            // Crear roles si no existen
            Rol roleUser = rolRepository.findByNombre("ROLE_USER")
                .orElseGet(() -> {
                    Rol rol = new Rol("ROLE_USER");
                    rol.setDescripcion("Usuario normal del sistema");
                    return rolRepository.save(rol);
                });
            
            Rol roleAdmin = rolRepository.findByNombre("ROLE_ADMIN")
                .orElseGet(() -> {
                    Rol rol = new Rol("ROLE_ADMIN");
                    rol.setDescripcion("Administrador del sistema");
                    return rolRepository.save(rol);
                });
            
            System.out.println("✅ Roles inicializados correctamente");
            
            // Crear usuario "user" si no existe
            if (!usuarioRepository.existsByUsername("user")) {
                Usuario user = new Usuario();
                user.setUsername("user");
                user.setEmail("user@alquiler.com");
                user.setPassword(passwordEncoder.encode("1234"));
                user.setNombre("Usuario");
                user.setApellido("Prueba");
                user.setActivo(true);
                
                Set<Rol> roles = new HashSet<>();
                roles.add(roleUser);
                user.setRoles(roles);
                
                usuarioRepository.save(user);
                System.out.println("✅ Usuario USER creado");
                System.out.println("   Username: user");
                System.out.println("   Password: 1234");
            }
            
            // Crear usuario "admin" si no existe
            if (!usuarioRepository.existsByUsername("admin")) {
                Usuario admin = new Usuario();
                admin.setUsername("admin");
                admin.setEmail("admin@alquiler.com");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setNombre("Administrador");
                admin.setApellido("Sistema");
                admin.setActivo(true);
                
                Set<Rol> roles = new HashSet<>();
                roles.add(roleUser);
                roles.add(roleAdmin);
                admin.setRoles(roles);
                
                usuarioRepository.save(admin);
                System.out.println("✅ Usuario ADMIN creado");
                System.out.println("   Username: admin");
                System.out.println("   Password: admin123");
            }
            
            System.out.println("========================================");
            System.out.println("🎉 SISTEMA DE AUTENTICACIÓN INICIALIZADO");
            System.out.println("========================================");
        };
    }
}
