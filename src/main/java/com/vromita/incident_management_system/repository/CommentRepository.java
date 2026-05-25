package com.vromita.incident_management_system.repository;

import com.vromita.incident_management_system.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findCommentByIncidentId(Long incidentId);
}
