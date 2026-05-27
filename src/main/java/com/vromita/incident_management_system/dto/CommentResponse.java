package com.vromita.incident_management_system.dto;

import java.time.LocalDateTime;

/**
 * DTO class for CommentController
 */
public class CommentResponse {

    private Long id;

    private String text;

    private String authorUsername;

    private LocalDateTime createdAt;


    public CommentResponse() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
