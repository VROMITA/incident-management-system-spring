package com.vromita.incident_management_system.exception;

public class IncidentNotFoundException extends RuntimeException{

    public IncidentNotFoundException(String message) {
        super(message);
    }
}
