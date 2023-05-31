package com.bugtracker.api.controllers;

import com.bugtracker.api.assemblers.ModelAssembler;
import com.bugtracker.api.dto.bugcomment.BugCommentDtoMapper;
import com.bugtracker.api.dto.bugcomment.BugCommentResponseDto;
import com.bugtracker.api.dto.bug.BugDtoMapper;
import com.bugtracker.api.dto.bug.BugRequestDto;
import com.bugtracker.api.dto.bug.BugResponseDto;
import com.bugtracker.api.entities.Bug;
import com.bugtracker.api.entities.Project;
import com.bugtracker.api.security.principal.CurrentUser;
import com.bugtracker.api.services.BugCommentService;
import com.bugtracker.api.services.BugService;
import com.bugtracker.api.security.principal.UserPrincipal;
import com.bugtracker.api.services.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("api/bugs")
@RequiredArgsConstructor
public class BugController {

    private final BugService bugService;
    private final BugCommentService bugCommentService;
    private final ProjectService projectService;
    private final BugDtoMapper bugDtoMapper;
    private final BugCommentDtoMapper bugCommentDtoMapper;
    private final ModelAssembler<BugResponseDto> bugDtoModelAssembler;
    private final ModelAssembler<BugCommentResponseDto> bugCommentDtoModelAssembler;
    private final PagedResourcesAssembler<BugResponseDto> bugDtoPagedResourcesAssembler;
    private final PagedResourcesAssembler<BugCommentResponseDto> bugCommentDtoPagedResourcesAssembler;

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<BugResponseDto>>> getBugsPage(@PageableDefault(page = 0, size = 15) Pageable pageable) {
        Page<BugResponseDto> bugDtosPage = bugService.findAllBugs(pageable).map(bugDtoMapper::toDto);
        PagedModel<EntityModel<BugResponseDto>> bugDtosPagedModel = bugDtoPagedResourcesAssembler.toModel(bugDtosPage, bugDtoModelAssembler);
        return ResponseEntity.ok(bugDtosPagedModel);
    }

    @GetMapping("{bugId}")
    public ResponseEntity<EntityModel<BugResponseDto>> getBug(@PathVariable Long bugId) {
        BugResponseDto bug = bugDtoMapper.toDto(bugService.findBugById(bugId));
        EntityModel<BugResponseDto> bugDtoModel = bugDtoModelAssembler.toModel(bug);
        return ResponseEntity.ok(bugDtoModel);
    }

    @PostMapping
    public ResponseEntity<Void> createBug(@Valid @RequestBody BugRequestDto bugRequestDto, @CurrentUser UserPrincipal currentUser) {
        Project project = projectService.findProjectById(bugRequestDto.projectId());
        Bug bug = bugService.createBug(project, bugRequestDto, currentUser.user());
        URI createdBugUri = linkTo(methodOn(BugController.class).getBug(bug.getId())).toUri();
        return ResponseEntity.created(createdBugUri).build();
    }

    @PatchMapping("{bugId}")
    public ResponseEntity<Void> updateBug(@PathVariable Long bugId, @Valid @RequestBody BugRequestDto bugRequestDto) {
        Bug bug = bugService.findBugById(bugId);
        bugService.updateBug(bug, bugRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("{bugId}")
    public ResponseEntity<Void> deleteBug(@PathVariable Long bugId) {
        Bug bug = bugService.findBugById(bugId);
        bugService.deleteBug(bug);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("{bugId}/comments")
    public ResponseEntity<PagedModel<EntityModel<BugCommentResponseDto>>> getComments(
            @PathVariable Long bugId,
            @PageableDefault(page = 0, size = 15) Pageable pageable) {
        Bug bug = bugService.findBugById(bugId);
        Page<BugCommentResponseDto> commentDtosPage = bugCommentService.findAllCommentsByBug(bug, pageable).map(bugCommentDtoMapper::toDto);
        PagedModel<EntityModel<BugCommentResponseDto>> commentDtosPagedModel = bugCommentDtoPagedResourcesAssembler.toModel(commentDtosPage, bugCommentDtoModelAssembler);
        return ResponseEntity.ok(commentDtosPagedModel);
    }
}
