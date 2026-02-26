package org.park.controllers;

import jakarta.validation.ConstraintViolationException;
import org.park.dtos.errors.ErrorResponse;
import org.park.exceptions.BaseBusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. Errores de Negocio (Recurso no encontrado)
    @ExceptionHandler(BaseBusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusiness(BaseBusinessException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getCodigo(), ex.getMessage()), ex.getStatus());
    }

    // 2. Errores de Validación (Campos de DTO con @Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> detalles = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(f ->
                detalles.put(f.getField(), f.getDefaultMessage())
        );

        ErrorResponse error = new ErrorResponse(
                "ERR_VALIDATION",
                "Datos de entrada inválidos",
                LocalDateTime.now(),
                detalles
        );

        return ResponseEntity.badRequest().body(error);
    }

    // 3. Errores de PathVariables o RequestParams (@Validated)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraint(ConstraintViolationException ex) {
        ErrorResponse error = new ErrorResponse("ERR_PARAMETER", ex.getMessage());
        return ResponseEntity.badRequest().body(error);
    }
}
