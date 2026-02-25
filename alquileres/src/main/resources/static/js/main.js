// JavaScript personalizado para Alquiler de Casas

document.addEventListener('DOMContentLoaded', function() {
    
    // Validación de formularios
    const forms = document.querySelectorAll('form');
    forms.forEach(form => {
        form.addEventListener('submit', function(e) {
            if (!form.checkValidity()) {
                e.preventDefault();
                e.stopPropagation();
            }
            form.classList.add('was-validated');
        });
    });

    // Confirmación de eliminación
    const deleteButtons = document.querySelectorAll('[data-action="delete"]');
    deleteButtons.forEach(button => {
        button.addEventListener('click', function(e) {
            if (!confirm('¿Está seguro de que desea eliminar este elemento?')) {
                e.preventDefault();
            }
        });
    });

    // Tooltips de Bootstrap
    const tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl);
    });

    // Validación de fechas en reservas
    const fechaInicio = document.getElementById('fechaInicio');
    const fechaFin = document.getElementById('fechaFin');
    
    if (fechaInicio && fechaFin) {
        fechaInicio.addEventListener('change', function() {
            fechaFin.min = this.value;
        });
        
        fechaFin.addEventListener('change', function() {
            if (fechaInicio.value && this.value < fechaInicio.value) {
                alert('La fecha de salida debe ser posterior a la fecha de entrada');
                this.value = '';
            }
        });
    }

    // Animación smooth scroll
    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function (e) {
            e.preventDefault();
            const target = document.querySelector(this.getAttribute('href'));
            if (target) {
                target.scrollIntoView({
                    behavior: 'smooth',
                    block: 'start'
                });
            }
        });
    });

    // Auto-cierre de alertas
    const alerts = document.querySelectorAll('.alert:not(.alert-permanent)');
    alerts.forEach(alert => {
        setTimeout(() => {
            alert.classList.add('fade');
            setTimeout(() => alert.remove(), 150);
        }, 5000);
    });
});

// Función para formatear precios
function formatearPrecio(precio) {
    return new Intl.NumberFormat('es-AR', {
        style: 'currency',
        currency: 'ARS'
    }).format(precio);
}

// Función para calcular días entre fechas
function calcularDias(fechaInicio, fechaFin) {
    const inicio = new Date(fechaInicio);
    const fin = new Date(fechaFin);
    const diferencia = fin - inicio;
    return Math.ceil(diferencia / (1000 * 60 * 60 * 24));
}

// Función para calcular precio total de reserva
function calcularPrecioTotal(precioPorNoche, fechaInicio, fechaFin) {
    const dias = calcularDias(fechaInicio, fechaFin);
    return precioPorNoche * dias;
}
