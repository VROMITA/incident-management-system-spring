package com.vromita.incident_management_system.service;

import com.vromita.incident_management_system.dto.CommentRequest;
import com.vromita.incident_management_system.exception.IncidentNotFoundException;
import com.vromita.incident_management_system.model.Comment;
import com.vromita.incident_management_system.repository.AppUserRepository;
import com.vromita.incident_management_system.repository.CommentRepository;
import com.vromita.incident_management_system.repository.IncidentRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CommentService{

    private final CommentRepository commentRepository;
    private final IncidentRepository incidentRepository;
    private final AppUserRepository appUserRepository;


    public CommentService(CommentRepository commentRepository,  IncidentRepository incidentRepository, AppUserRepository appUserRepository) {
        this.commentRepository = commentRepository;
        this.incidentRepository = incidentRepository;
        this.appUserRepository = appUserRepository;
    }



    public Comment createComment(Long incidentId, CommentRequest commentRequest, String username ) {

        Comment comment = new Comment();

        comment.setText(commentRequest.getText());
        comment.setIncident(incidentRepository.findById(incidentId).orElseThrow(() ->
                new IncidentNotFoundException("Incident with id " + incidentId + " not found")));
        comment.setAuthor(appUserRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("Username " + username + " not found")));

        return commentRepository.save(comment);

    }

    public List<Comment> getCommentsByIncidentId(Long incidentId) {

        incidentRepository.findById(incidentId).orElseThrow(() ->
                new IncidentNotFoundException("Incident with id " + incidentId + " not found"));

        return commentRepository.findCommentByIncidentId(incidentId);
    }


}
