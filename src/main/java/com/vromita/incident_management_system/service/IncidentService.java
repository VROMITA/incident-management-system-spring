package com.vromita.incident_management_system.service;

import com.vromita.incident_management_system.dto.IncidentRequest;
import com.vromita.incident_management_system.exception.IncidentNotFoundException;
import com.vromita.incident_management_system.mapper.IncidentMapper;
import com.vromita.incident_management_system.model.Incident;
import com.vromita.incident_management_system.repository.IncidentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class IncidentService {

    private final IncidentRepository incidentRepository;

    // Constructor injection
    public IncidentService(IncidentRepository incidentRepository){
        this.incidentRepository=incidentRepository;
    }

    public Incident createIncident(IncidentRequest incidentRequest){

        Incident incident = IncidentMapper.toEntity(incidentRequest);

        incident.setSlaDeadline(LocalDateTime.now().plusHours(incident.getPriority().getSlaHours()));

        return incidentRepository.save(incident);

    }

    public List<Incident> getAllIncidents(){
        return incidentRepository.findAll();
    }

    public Incident getIncidentById(long id){
        return incidentRepository.findById(id) .orElseThrow(() ->
                new IncidentNotFoundException("Incident with id " + id + " not found"));

    }

    public Incident updateIncident(Long id, IncidentRequest request){
        // Find Incident by ID
        Incident incidentById = getIncidentById(id);

        IncidentMapper.toUpdate(incidentById, request);

        incidentById.setSlaDeadline(
                LocalDateTime.now().plusHours(request.getPriority().getSlaHours()));

        return incidentRepository.save(incidentById);
    }

    public void deleteIncidentById(long id){

        getIncidentById(id); // throw error if ID doesn't exist
        incidentRepository.deleteById(id);
    }
}
