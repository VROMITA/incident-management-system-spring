package com.vromita.incident_management_system.controller;

import com.vromita.incident_management_system.dto.CommentRequest;
import com.vromita.incident_management_system.dto.CommentResponse;
import com.vromita.incident_management_system.model.Comment;
import com.vromita.incident_management_system.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api/incidents")
@RestController
public class CommentController {


    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentResponse> createComment(@PathVariable Long id,
                                                         @Valid @RequestBody CommentRequest request,
                                                                 Authentication authentication) {

        Comment comment = commentService.createComment(id, request, authentication.getName());

        CommentResponse response = toResponse(comment);


        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<List<CommentResponse>> getAllComments(@PathVariable Long id) {

       List<Comment> commentList = commentService.getCommentsByIncidentId(id);
       List<CommentResponse> responseList = new ArrayList<>();

       for(Comment comment : commentList){

           CommentResponse response = toResponse(comment);
           responseList.add(response);

       }
        return ResponseEntity.status(200).body(responseList);
    }


    private CommentResponse toResponse(Comment comment){

        CommentResponse response = new CommentResponse();
        response.setId(comment.getId());
        response.setText(comment.getText());
        response.setCreatedAt(comment.getCreatedAt());
        response.setAuthorUsername(comment.getAuthor().getUsername());

        return response;



    }
}
