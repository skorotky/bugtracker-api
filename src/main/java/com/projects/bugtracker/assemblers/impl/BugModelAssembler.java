package com.projects.bugtracker.assemblers.impl;

import com.projects.bugtracker.assemblers.ModelAssembler;
import com.projects.bugtracker.controllers.BugController;
import com.projects.bugtracker.controllers.ProjectController;
import com.projects.bugtracker.controllers.UserController;
import com.projects.bugtracker.dto.BugDto;
import lombok.NonNull;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BugModelAssembler implements ModelAssembler<BugDto> {

    @Override
    public @NonNull EntityModel<BugDto> toModel(BugDto bugDto) {
        Long bugId = bugDto.id();
        String username = bugDto.author().username();
        Long projectId = bugDto.projectId();

        return EntityModel.of(bugDto,
                linkTo(methodOn(BugController.class).getBug(bugId)).withSelfRel(),
                linkTo(methodOn(UserController.class).getUser(username)).withRel("author"),
                linkTo(methodOn(ProjectController.class).getProject(projectId)).withRel("project"),
                linkTo(methodOn(BugController.class).getComments(bugId, Pageable.unpaged())).withRel("comments").expand());
    }
}