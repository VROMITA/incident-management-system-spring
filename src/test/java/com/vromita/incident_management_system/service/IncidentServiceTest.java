package com.vromita.incident_management_system.service;

import com.vromita.incident_management_system.repository.IncidentRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class IncidentServiceTest {

   @Mock
   private IncidentRepository incidentRepository;

   @InjectMocks
   private IncidentService incidentService;
}
