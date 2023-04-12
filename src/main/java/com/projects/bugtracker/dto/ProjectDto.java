package com.projects.bugtracker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "projects", itemRelation = "project")
public record ProjectDto(

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        Long id,

        @NotBlank(message = "Title must not be null or blank.")
        String title,

        String description,

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        UserDto owner
) {
}