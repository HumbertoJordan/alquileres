// Script Node.js para renombrar imágenes desde Visual Studio Code
// Ejecutar: node renombrar.mjs

import { readdirSync, renameSync, statSync, existsSync } from 'fs';
import { join, extname, basename, resolve } from 'path';

// ===== CONFIGURACIÓN =====
// Cambiá esta ruta a donde están tus imágenes
const CARPETA_IMAGENES = './alquileres/src/main/resources/static/img';
// =========================

console.log('==========================================');
console.log('  RENOMBRADOR DE IMÁGENES - NODE.JS');
console.log('==========================================\n');

// Función para limpiar nombres
function limpiarNombre(nombre) {
    let limpio = nombre;
    
    // Normalizar y eliminar acentos
    limpio = limpio.normalize('NFD').replace(/[\u0300-\u036f]/g, '');
    
    // Convertir a minúsculas
    limpio = limpio.toLowerCase();
    
    // Reemplazar espacios y caracteres especiales por guiones bajos
    limpio = limpio.replace(/[^a-z0-9._-]/g, '_');
    
    // Eliminar guiones bajos múltiples
    limpio = limpio.replace(/_+/g, '_');
    
    // Eliminar guiones bajos al inicio y final
    limpio = limpio.replace(/^_+|_+$/g, '');
    
    return limpio;
}

// Función principal
function renombrarImagenes() {
    // Verificar que la carpeta existe
    if (!existsSync(CARPETA_IMAGENES)) {
        console.error(`❌ Error: La carpeta "${CARPETA_IMAGENES}" no existe`);
        console.log('\n💡 Consejo: Cambiá la ruta en CARPETA_IMAGENES dentro del script');
        console.log(`\n📍 Ruta actual del script: ${process.cwd()}`);
        return;
    }
    
    console.log(`📁 Buscando imágenes en: ${resolve(CARPETA_IMAGENES)}\n`);
    
    const extensionesValidas = ['.jpg', '.jpeg', '.png', '.gif', '.webp', '.bmp', '.svg'];
    let contador = 0;
    const cambios = [];
    
    // Leer archivos
    const archivos = readdirSync(CARPETA_IMAGENES);
    
    archivos.forEach(archivo => {
        const rutaCompleta = join(CARPETA_IMAGENES, archivo);
        const stats = statSync(rutaCompleta);
        
        // Solo procesar archivos (no carpetas)
        if (stats.isFile()) {
            const extension = extname(archivo).toLowerCase();
            
            // Verificar que sea una imagen
            if (extensionesValidas.includes(extension)) {
                const nombreSinExt = basename(archivo, extname(archivo));
                const nombreLimpio = limpiarNombre(nombreSinExt);
                const archivoNuevo = nombreLimpio + extension;
                
                // Si el nombre cambió
                if (archivo !== archivoNuevo) {
                    const rutaNueva = join(CARPETA_IMAGENES, archivoNuevo);
                    
                    // Verificar si ya existe
                    if (existsSync(rutaNueva)) {
                        console.log(`⚠️  SALTADO: ${archivo}`);
                        console.log(`   → El archivo ${archivoNuevo} ya existe\n`);
                    } else {
                        try {
                            renameSync(rutaCompleta, rutaNueva);
                            console.log(`✅ RENOMBRADO: ${archivo}`);
                            console.log(`   → ${archivoNuevo}\n`);
                            cambios.push({ original: archivo, nuevo: archivoNuevo });
                            contador++;
                        } catch (error) {
                            console.log(`❌ ERROR: ${archivo}`);
                            console.log(`   → ${error.message}\n`);
                        }
                    }
                }
            }
        }
    });
    
    console.log('==========================================');
    console.log(`  Proceso completado`);
    console.log(`  Archivos renombrados: ${contador}`);
    console.log('==========================================\n');
    
    // Mostrar resumen
    if (cambios.length > 0) {
        console.log('📋 RESUMEN DE CAMBIOS:');
        console.log('------------------------------------------');
        cambios.forEach(cambio => {
            console.log(`  ${cambio.original} → ${cambio.nuevo}`);
        });
        console.log('------------------------------------------\n');
    } else if (contador === 0) {
        console.log('ℹ️  No se encontraron archivos para renombrar.');
        console.log('   Todos los nombres ya están correctos.\n');
    }
}

// Ejecutar
try {
    renombrarImagenes();
} catch (error) {
    console.error('❌ Error inesperado:', error.message);
    process.exit(1);
}
