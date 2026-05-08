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


    /**
     * Creates a new incident and calculates the SLA deadline based on priority.
     *
     * @param incidentRequest DTO containing the incident data provided by the client
     * @return the saved Incident with generated ID and calculated SLA deadline
     */
    public Incident createIncident(IncidentRequest incidentRequest){

        Incident incident = IncidentMapper.toEntity(incidentRequest);

        incident.setSlaDeadline(LocalDateTime.now().plusHours(incident.getPriority().getSlaHours()));

        return incidentRepository.save(incident);

    }

    /**
     * Retrieves all incidents from the database.
     *
     * @return list of all incidents, empty list if none exist
     */

    public List<Incident> getAllIncidents(){
        return incidentRepository.findAll();
    }

    /**
     * It returns the incidents by the given ID
     *
     * @param id the ID is provided to find the correspondent incident
     * @throws IncidentNotFoundException if no incident is found with the given ID
     * @return it returns the incident
     */

    public Incident getIncidentById(long id){
        return incidentRepository.findById(id) .orElseThrow(() ->
                new IncidentNotFoundException("Incident with id " + id + " not found"));

    }

    /**
     * Update the incident searched by ID and if the priority changed, the SLA is recalculated
     * @param id the ID of the incidents
     * @param request a DTO is given to update the incident
     * @return return the incident with the new values
     */
    public Incident updateIncident(Long id, IncidentRequest request){

        Incident incidentById = getIncidentById(id);

        IncidentMapper.toUpdate(incidentById, request);

        incidentById.setSlaDeadline(
                LocalDateTime.now().plusHours(request.getPriority().getSlaHours()));

        return incidentRepository.save(incidentById);
    }

    /**
     * It looks up for the incident with ID and delete
     * @param id the ID of the Incident
     * @throws IncidentNotFoundException if no incident is found with the given ID
     */

    public void deleteIncidentById(long id){

        getIncidentById(id); // throw error if ID doesn't exist
        incidentRepository.deleteById(id);
    }
}
