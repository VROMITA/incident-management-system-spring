package com.vromita.incident_management_system.dto;

import com.vromita.incident_management_system.model.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


// DTO for update data
public class IncidentRequest {

    @NotBlank(message="Title is required")
    private String title;
    private String description;
    @NotNull(message="Priority is required")
    private Priority priority;
    @NotNull(message="Source is required")
    private Source source;
    private Status status;
    private Team assignedTeam;
    private String assignedTo;


 public IncidentRequest(){}

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Priority getPriority() {
        return priority;
    }

    public Source getSource() {
        return source;
    }

    public Status getStatus() {
        return status;
    }

    public Team getAssignedTeam() {
        return assignedTeam;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setAssignedTeam(Team assignedTeam) {
        this.assignedTeam = assignedTeam;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }
}
