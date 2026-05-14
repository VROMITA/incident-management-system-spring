package com.vromita.incident_management_system.service;

import com.vromita.incident_management_system.dto.IncidentRequest;
import com.vromita.incident_management_system.model.Incident;
import com.vromita.incident_management_system.model.Priority;
import com.vromita.incident_management_system.model.Source;
import com.vromita.incident_management_system.repository.IncidentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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

      when(incidentRepository.save(any(Incident.class)))
              .thenReturn(savedIncident);

      // ACT
      Incident result = incidentService.createIncident(request);

      // ASSERT
      assertNotNull(result);
      assertNotNull(result.getSlaDeadline());
      verify(incidentRepository, times(1)).save(any(Incident.class));
   }

   }
