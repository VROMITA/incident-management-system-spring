package com.vromita.incident_management_system.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(IncidentNotFoundException.class)
        public ResponseEntity<ErrorResponse> handleIncidentNotFound(IncidentNotFoundException e) {

            ErrorResponse error = new ErrorResponse(
                    e.getMessage(),
                    404,
                    LocalDateTime.now()
            );
            return ResponseEntity.status(404).body(error);
        }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException e) {
        ErrorResponse error = new ErrorResponse(
                "Access Denied",
                403,
                LocalDateTime.now()
        );
        return ResponseEntity.status(403).body(error);
    }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorResponse> handleGenericException(Exception e) {
            ErrorResponse error = new ErrorResponse(
                    e.getMessage(),
                    500,
                    LocalDateTime.now()
            );
            return ResponseEntity.status(500).body(error);
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
         public ResponseEntity<ErrorResponse> handleValidationErrors(
            MethodArgumentNotValidException e) {

        Map<String, String> errors = new HashMap<>();

        e.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        ErrorResponse error = new ErrorResponse(
                "Validation Failed",
                400,
                LocalDateTime.now(),
                errors

        );

        return ResponseEntity.status(400).body(error);
     }

    }

