package com.projects.bugtracker.repositories;

import com.projects.bugtracker.entities.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    Page<Project> findByOwner_Username(String username, Pageable pageable);

    Page<Project> findByCollaborators_Username(String username, Pageable pageable);
}
