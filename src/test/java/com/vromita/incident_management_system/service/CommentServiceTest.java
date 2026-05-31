package com.vromita.incident_management_system.service;


import com.vromita.incident_management_system.dto.CommentRequest;
import com.vromita.incident_management_system.exception.IncidentNotFoundException;
import com.vromita.incident_management_system.model.*;
import com.vromita.incident_management_system.repository.AppUserRepository;
import com.vromita.incident_management_system.repository.CommentRepository;
import com.vromita.incident_management_system.repository.IncidentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @Mock
    private AppUserRepository appUserRepository;
    @Mock
    private IncidentRepository incidentRepository;
    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentService commentService;

    @Test
    void createComment() {

        //Arrange
        Comment comment = new Comment();
        CommentRequest commentRequest = new CommentRequest();
        commentRequest.setText("Test");

        Incident savedIncident = new Incident();
        savedIncident.setId(1L);
        savedIncident.setAssignedTeam(Team.L3);
        savedIncident.setPriority(Priority.CRITICAL);

        AppUser appUser = new AppUser();
        appUser.setId(1L);
        appUser.setRole(Role.L3);
        appUser.setUsername("testUser");

        when(incidentRepository.findById(savedIncident.getId())).thenReturn(Optional.of(savedIncident));
        comment.setIncident(savedIncident);
        when(appUserRepository.findByUsername(appUser.getUsername())).thenReturn(Optional.of(appUser));
        comment.setAuthor(appUser);

        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        //Act
        Comment result = commentService.createComment(savedIncident.getId(), commentRequest, appUser.getUsername());

        //Assert
        assertNotNull(result);
        verify(commentRepository, times(1)).save(any(Comment.class));

    }

    @Test
    void CreateComment_shouldResetSlaIncident(){

        Comment comment = new Comment();
        CommentRequest commentRequest = new CommentRequest();
        commentRequest.setText("Test");

        Incident savedIncident = new Incident();
        savedIncident.setId(1L);
        savedIncident.setAssignedTeam(Team.L3);
        savedIncident.setPriority(Priority.CRITICAL);

        AppUser appUser = new AppUser();
        appUser.setId(1L);
        appUser.setRole(Role.L3);
        appUser.setUsername("testUser");

        when(incidentRepository.findById(savedIncident.getId())).thenReturn(Optional.of(savedIncident));
        comment.setIncident(savedIncident);

        when(appUserRepository.findByUsername(appUser.getUsername())).thenReturn(Optional.of(appUser));
        comment.setAuthor(appUser);

        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        // ACT

        Comment result = commentService.createComment(savedIncident.getId(), commentRequest, appUser.getUsername());

        // ASSERT
        assertNotNull(result);
        verify(commentRepository, times(1)).save(any(Comment.class));
        verify(incidentRepository, times(1)).save(any(Incident.class));
    }


    @Test
    void getCommentsByIncidentId_shouldReturnComments(){

        Comment comment = new Comment();

        comment.setText("Test");

        Incident savedIncident = new Incident();
        savedIncident.setId(1L);
        savedIncident.setAssignedTeam(Team.L3);
        savedIncident.setPriority(Priority.CRITICAL);

        comment.setIncident(savedIncident);

        when(incidentRepository.findById(savedIncident.getId())).thenReturn(Optional.of(savedIncident));
        when(commentRepository.findCommentByIncidentId(savedIncident.getId())).thenReturn(List.of(comment));

        List<Comment> result= commentService.getCommentsByIncidentId(savedIncident.getId());

        assertNotNull(result);
        verify(commentRepository, times(1)).findCommentByIncidentId(savedIncident.getId());


    }

    @Test
    void getCommentsByIncidentId_shouldThrowException_whenIncidentNotFound(){

        when(incidentRepository.findById(1L)).
                thenReturn(Optional.empty());

        assertThrows(IncidentNotFoundException.class,
                () ->  commentService.getCommentsByIncidentId(1L));
    }


}
