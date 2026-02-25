package com.alquiler.alquileres.dto;

// Clase genérica para envolver respuestas exitosas
public class ApiResponseSuccessDto<T> {
    
    // 1. CAMPO: Indicador de éxito
    private boolean success;
    
    // 2. CAMPO: Mensaje para el cliente
    private String message;
    
    // 3. CAMPO: Datos genéricos (puede ser un objeto, lista, string, etc)
    private T data;

    // CONSTRUCTOR VACÍO (para deserialization)
    public ApiResponseSuccessDto() {
    }

    // CONSTRUCTOR CON PARÁMETROS ( usaremos en los endpoints)
    public ApiResponseSuccessDto(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    // GETTERS
    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    // SETTERS
    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(T data) {
        this.data = data;
    }
}
