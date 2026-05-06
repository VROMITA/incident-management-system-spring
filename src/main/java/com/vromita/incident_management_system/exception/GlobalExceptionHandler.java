package com.vromita.incident_management_system.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(IncidentNotFoundException.class)
        public ResponseEntity<String> handleIncidentNotFound(IncidentNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<String> handleGenericException(Exception e) {
            return ResponseEntity.status(500).body("Internal server error: " + e.getMessage());
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
         public ResponseEntity<Map<String, String>> handleValidationErrors(
            MethodArgumentNotValidException e) {

        Map<String, String> errors = new HashMap<>();

        e.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity.status(400).body(errors);
    }
    }

