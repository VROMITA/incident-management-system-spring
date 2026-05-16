package com.vromita.incident_management_system.service;

import com.vromita.incident_management_system.dto.IncidentRequest;
import com.vromita.incident_management_system.exception.IncidentNotFoundException;
import com.vromita.incident_management_system.model.Incident;
import com.vromita.incident_management_system.model.Priority;
import com.vromita.incident_management_system.model.Source;
import com.vromita.incident_management_system.repository.IncidentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class IncidentServiceTest {

   @Mock
   private IncidentRepository incidentRepository;

   @InjectMocks
   private IncidentService incidentService;


   @Test
   void createIncident_shouldCalculateSlaDeadline() {

      // ARRANGE
      IncidentRequest request = new IncidentRequest();
      request.setTitle("Server down");
      request.setPriority(Priority.CRITICAL);
      request.setSource(Source.USER_REPORT);


      Incident savedIncident = new Incident();
      savedIncident.setTitle("Server down");
      savedIncident.setPriority(Priority.CRITICAL);
      savedIncident.setSlaDeadline(LocalDateTime.now().plusHours(4));

      when(incidentRepository.save(any(Incident.class)))
              .thenReturn(savedIncident);

      // ACT
      Incident result = incidentService.createIncident(request);

      // ASSERT
      assertNotNull(result);
      assertNotNull(result.getSlaDeadline());
      verify(incidentRepository, times(1)).save(any(Incident.class));
   }

   @Test
   void getIncidentById_shouldReturnIncident() {

      // ARRANGE
      Incident incident = new Incident();
      incident.setId(1L);
      incident.setTitle("Server down");
      incident.setPriority(Priority.CRITICAL);

      when(incidentRepository.findById(1L)).
              thenReturn(Optional.of(incident));
      // ACT
      Incident result = incidentService.getIncidentById(1L);

      //ASSERT
      assertNotNull(result);
      assertEquals(1L, result.getId());
      assertEquals("Server down",  result.getTitle());
      verify(incidentRepository, times(1)).findById(1L);

   }

   @Test
   void getIncidentById_shouldThrowException_whenNotFound() {

      when(incidentRepository.findById(1L)).
              thenReturn(Optional.empty());

      assertThrows(IncidentNotFoundException.class,
              () -> incidentService.getIncidentById(1L));

   }

   @Test
    void deleteIncident_shouldDeleteIncident() {
      Incident incident = new Incident();
      incident.setId(1L);
      incident.setTitle("Server down");
      incident.setPriority(Priority.CRITICAL);

      when(incidentRepository.findById(1L)).thenReturn(Optional.of(incident));

      incidentService.deleteIncidentById(1L);

      verify(incidentRepository, times(1)).deleteById(1L);
    }

    @Test
   void deleteIncident_shouldThrowException_whenNotFound() {
      when(incidentRepository.findById(1L)).
              thenReturn(Optional.empty());

      assertThrows(IncidentNotFoundException.class, ()
              -> incidentService.deleteIncidentById(1L));

    }

    @Test
    void updateIncident_shouldUpdateIncident() {
      Incident incident = new Incident();
      incident.setId(1L);
      incident.setTitle("Server down");
      incident.setPriority(Priority.LOW);

      IncidentRequest request = new IncidentRequest();
      request.setTitle("Server down");
      request.setPriority(Priority.CRITICAL);

      when(incidentRepository.findById(1L)).thenReturn(Optional.of(incident));
      when(incidentRepository.save(any(Incident.class))).thenReturn(incident);

      Incident result = incidentService.updateIncident(1L, request);

       assertNotNull(result);
       assertNotNull(result.getSlaDeadline());
       verify(incidentRepository, times(1)).save(any(Incident.class));


    }

   }
