package com.vromita.incident_management_system.mapper;

import com.vromita.incident_management_system.dto.IncidentRequest;
import com.vromita.incident_management_system.dto.IncidentResponse;
import com.vromita.incident_management_system.model.Incident;

/**
 * Mapper class responsible for converting between IncidentRequest DTO and Incident Entity.
 */
public class IncidentMapper {

    /**
     * Converts an IncidentRequest DTO to a new Incident Entity.
     * Does not set slaDeadline (business logic handled by Service).
     *
     * @param incidentRequest the DTO containing incident data from the client
     * @return a new Incident Entity with fields mapped from the DTO
     */
    public static Incident toEntity(IncidentRequest incidentRequest){

        Incident incident = new Incident();

        incident.setTitle(incidentRequest.getTitle());
        incident.setDescription(incidentRequest.getDescription());
        incident.setPriority(incidentRequest.getPriority());
        incident.setAssignedTeam(incidentRequest.getAssignedTeam());
        incident.setSource(incidentRequest.getSource());

        return incident;
    }

    /**
     * Updates an existing Incident Entity with data from an IncidentRequest DTO.
     * Does not update slaDeadline (business logic handled by Service).
     *
     * @param incident the existing Incident Entity to update
     * @param incidentRequest the DTO containing the updated data
     */

    public static void toUpdate(Incident incident, IncidentRequest incidentRequest){
        incident.setTitle(incidentRequest.getTitle());
        incident.setDescription(incidentRequest.getDescription());
        incident.setPriority(incidentRequest.getPriority());
        incident.setSource(incidentRequest.getSource());
        incident.setStatus(incidentRequest.getStatus());
        incident.setAssignedTeam(incidentRequest.getAssignedTeam());

    }

    public static IncidentResponse toResponse(Incident incident){

        IncidentResponse incidentResponse = new IncidentResponse();

        incidentResponse.setId(incident.getId());
        incidentResponse.setTitle(incident.getTitle());
        incidentResponse.setDescription(incident.getDescription());
        incidentResponse.setPriority(incident.getPriority());
        incidentResponse.setStatus(incident.getStatus());
        incidentResponse.setSource(incident.getSource());
        incidentResponse.setAssignedTeam(incident.getAssignedTeam());
        incidentResponse.setAssignedUser(incident.getAssignedTo() != null ? incident.getAssignedTo().getUsername() : null);
        incidentResponse.setCreatedAt(incident.getCreatedAt());
        incidentResponse.setUpdatedAt(incident.getUpdatedAt());
        incidentResponse.setClosedAt(incident.getClosedAt());
        incidentResponse.setSlaDeadline(incident.getSlaDeadline());

        return incidentResponse;
    }
}
