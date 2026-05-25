package com.vromita.incident_management_system.repository;

import com.vromita.incident_management_system.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findCommentByIncidentId(Long incidentId);
}
