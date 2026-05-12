package com.vromita.incident_management_system.controller;

import com.vromita.incident_management_system.dto.IncidentRequest;
import com.vromita.incident_management_system.exception.IncidentNotFoundException;
import com.vromita.incident_management_system.model.Incident;
import com.vromita.incident_management_system.service.IncidentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<Incident> createIncident(@Valid @RequestBody IncidentRequest incidentRequest) {
        Incident incident = incidentService.createIncident(incidentRequest);
        return ResponseEntity.status(201).body(incident);
    }

    /**
     * List of the incidents
     *
     * @return all the incidents with 200 status code
     */
    @GetMapping("")
    public ResponseEntity<List<Incident>> getAllIncidents() {

        List<Incident> incidentList = incidentService.getAllIncidents();

        return ResponseEntity.status(200).body(incidentList);
    }

    /**
     * It returns the incident by the given ID
     * @param id the ID of the incident
     * @return  the incident with 200 status code or 404 not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Incident> getIncidentById(@PathVariable long id) {

        Incident incident = incidentService.getIncidentById(id);

        return ResponseEntity.status(200).body(incident);
    }

    /**
     * It returns the incident with the updated body
     * @param id the id of the incident
     * @param request the body of the incident that needs to be updated
     * @throws IncidentNotFoundException if no incident is found with the given ID
     */
    @PutMapping("/{id}")
    public ResponseEntity<Incident> updateIncident(@PathVariable long id, @Valid @RequestBody IncidentRequest request) {

        Incident incident = incidentService.updateIncident(id, request);

        return ResponseEntity.status(200).body(incident);

    }


    /**
     * It deletes the incident by the given ID
     * @param id the id of the Incident
     * @return the method does not return anything but only the status code 204 if it is successful or 404 status code if the id has not been found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIncident (@PathVariable long id) {

       incidentService.deleteIncidentById(id);

       return ResponseEntity.noContent().build();
    }


}
