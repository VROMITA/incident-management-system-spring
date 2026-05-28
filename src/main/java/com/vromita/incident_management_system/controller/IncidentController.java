package com.vromita.incident_management_system.controller;

import com.vromita.incident_management_system.dto.IncidentRequest;
import com.vromita.incident_management_system.dto.IncidentResponse;
import com.vromita.incident_management_system.exception.IncidentNotFoundException;
import com.vromita.incident_management_system.model.Incident;
import com.vromita.incident_management_system.service.IncidentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.vromita.incident_management_system.mapper.IncidentMapper.toResponse;

/**
 * REST controller for managing incidents
 * Exposes endpoints for CRUD operations
 */
@RestController
@RequestMapping("/api/incidents")
public class IncidentController {

    private final IncidentService incidentService;


    public IncidentController(IncidentService incidentService) {
        this.incidentService = incidentService;
    }

    /**
     * Create a new incident
     * @param incidentRequest it receives the body to create the incident
     * @return the incidents created with 201 status code
     */
    @PostMapping("")
    public ResponseEntity<IncidentResponse> createIncident(@Valid @RequestBody IncidentRequest incidentRequest) {

        Incident incident = incidentService.createIncident(incidentRequest);

        IncidentResponse response = toResponse(incident);

        return ResponseEntity.status(201).body(response);
    }

    /**
     * List of the incidents
     *
     * @return all the incidents with 200 status code
     */
    @GetMapping("")
    public ResponseEntity<List<IncidentResponse>> getAllIncidents() {

        List<Incident> incidentList = incidentService.getAllIncidents();

        List<IncidentResponse> incidentResponseList = new ArrayList<>();

        for(Incident incident : incidentList) {

            IncidentResponse response = toResponse(incident);
            incidentResponseList.add(response);
        }

        return ResponseEntity.status(200).body(incidentResponseList);
    }

    /**
     * It returns the incident by the given ID
     * @param id the ID of the incident
     * @return  the incident with 200 status code or 404 not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<IncidentResponse> getIncidentById(@PathVariable long id) {

        Incident incident = incidentService.getIncidentById(id);

        IncidentResponse response = toResponse(incident);

        return ResponseEntity.status(200).body(response);
    }

    /**
     * It returns the incident with the updated body
     * @param id the id of the incident
     * @param request the body of the incident that needs to be updated
     * @throws IncidentNotFoundException if no incident is found with the given ID
     */
    @PutMapping("/{id}")
    public ResponseEntity<IncidentResponse> updateIncident(@PathVariable long id, @Valid @RequestBody IncidentRequest request) {

        Incident incident = incidentService.updateIncident(id, request);

        IncidentResponse response = toResponse(incident);

        return ResponseEntity.status(200).body(response);

    }


    /**
     * It deletes the incident by the given ID
     * @param id the id of the Incident
     * @return the method does not return anything but only the status code 204 if it is successful or 404 status code if the id has not been found
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('L3')")
    public ResponseEntity<Void> deleteIncident (@PathVariable long id) {

       incidentService.deleteIncidentById(id);

       return ResponseEntity.noContent().build();
    }


}
