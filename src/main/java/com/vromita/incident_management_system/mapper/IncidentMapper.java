package com.vromita.incident_management_system.mapper;

import com.vromita.incident_management_system.dto.IncidentRequest;
import com.vromita.incident_management_system.model.Incident;

import java.time.LocalDateTime;

public class IncidentMapper {

    public static Incident toEntity(IncidentRequest incidentRequest){

        Incident incident = new Incident();

        incident.setTitle(incidentRequest.getTitle());
        incident.setDescription(incidentRequest.getDescription());
        incident.setPriority(incidentRequest.getPriority());
        incident.setAssignedTeam(incidentRequest.getAssignedTeam());
        incident.setAssignedTo(incidentRequest.getAssignedTo());
        incident.setSource(incidentRequest.getSource());

        return incident;
    }

    public static void toUpdate(Incident incident, IncidentRequest incidentRequest){
        incident.setTitle(incidentRequest.getTitle());
        incident.setDescription(incidentRequest.getDescription());
        incident.setPriority(incidentRequest.getPriority());
        incident.setSource(incidentRequest.getSource());
        incident.setStatus(incidentRequest.getStatus());
        incident.setAssignedTeam(incidentRequest.getAssignedTeam());
        incident.setAssignedTo(incidentRequest.getAssignedTo());

    }
}
