package com.ConsultorioOdontologico.consultorioOdontologico.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // Manejo de errores de validación (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> 
            errors.add(error.getField() + ": " + error.getDefaultMessage())
        );

        ErrorResponse errorDetails = new ErrorResponse(
                LocalDateTime.now(),
                "Error de Validación",
                String.join(", ", errors),
                "VALIDATION_ERROR"
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    // Manejo de errores de lógica de negocio (Excepciones lanzadas manualmente)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        ErrorResponse errorDetails = new ErrorResponse(
                LocalDateTime.now(),
                "Error en la operación",
                ex.getMessage(),
                "BAD_REQUEST"
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    // Manejo global de excepciones inesperadas
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, WebRequest request) {
        log.error("Error no controlado capturado por el GlobalExceptionHandler: ", ex);
        ErrorResponse errorDetails = new ErrorResponse(
                LocalDateTime.now(),
                "Error interno del servidor",
                "Ocurrió un error inesperado en el servidor. Por favor, intente más tarde.",
                "INTERNAL_SERVER_ERROR"
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
