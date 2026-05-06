package com.vromita.incident_management_system.model;

import jakarta.persistence.*;
import org.hibernate.cfg.Compatibility;

import java.time.LocalDateTime;

@Entity
@Table(name = "incidents")

public class Incident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Source source;

    @Enumerated(EnumType.STRING)
    @Column(name = "assigned_team")
    private Team assignedTeam;

    @Column(name = "created_at", nullable = false,  updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "last_updated_at")
    private LocalDateTime updatedAt;


    @Column(name = "assigned_to")
    private String assignedTo;

    @Column(name = "sla_deadline")
    private LocalDateTime slaDeadline;

    @PrePersist
    protected void onCreate(){
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if(status == null){
            status = Status.OPEN;
        }
    }

    @PreUpdate
    protected void onUpdate(){
        updatedAt = LocalDateTime.now();
    }

    public Incident(){

    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Priority getPriority() {
        return priority;
    }

    public String getDescription() {
        return description;
    }

    public Status getStatus() {
        return status;
    }

    public Source getSource() {
        return source;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public LocalDateTime getSlaDeadline() {
        return slaDeadline;
    }

    public Team getAssignedTeam() {
        return assignedTeam;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public void setAssignedTeam(Team assignedTeam) {
        this.assignedTeam = assignedTeam;
    }

    private void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public void setSlaDeadline(LocalDateTime slaDeadline) {
        this.slaDeadline = slaDeadline;
    }
}
