package com.vromita.incident_management_system.controller;

import com.vromita.incident_management_system.dto.IncidentRequest;
import com.vromita.incident_management_system.model.Incident;
import com.vromita.incident_management_system.service.IncidentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/incidents")
public class IncidentController {

    private final IncidentService incidentService;


    public IncidentController(IncidentService incidentService) {
        this.incidentService = incidentService;
    }

    @PostMapping("")
    public ResponseEntity<Incident> createIncident(@Valid @RequestBody IncidentRequest incidentRequest) {
        Incident incident = incidentService.createIncident(incidentRequest);
        return ResponseEntity.status(201).body(incident);
    }

    @GetMapping("")
    public ResponseEntity<List<Incident>> getAllIncidents() {

        List<Incident> incidentList = incidentService.getAllIncidents();

        return ResponseEntity.status(200).body(incidentList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Incident> getIncidentById(@PathVariable long id) {

        Incident incident = incidentService.getIncidentById(id);

        return ResponseEntity.status(200).body(incident);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Incident> updateIncident(@PathVariable long id, @Valid @RequestBody IncidentRequest request) {

        Incident incident = incidentService.updateIncident(id, request);

        return ResponseEntity.status(200).body(incident);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIncident (@PathVariable long id) {

       incidentService.deleteIncidentById(id);

       return ResponseEntity.noContent().build();
    }


}
