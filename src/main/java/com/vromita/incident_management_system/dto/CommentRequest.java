package com.vromita.incident_management_system.dto;

import jakarta.validation.constraints.NotBlank;

public class CommentRequest {

    @NotBlank
    private String text;

    public CommentRequest() {}

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
}
