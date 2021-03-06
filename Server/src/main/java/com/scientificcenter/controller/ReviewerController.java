package com.scientificcenter.controller;

import com.scientificcenter.model.dto.entity.ReviewDto;
import com.scientificcenter.model.dto.entity.ReviewerDto;
import com.scientificcenter.model.dto.process.FormSubmissionDto;
import com.scientificcenter.model.paper.Review;
import com.scientificcenter.model.paper.ScientificPaper;
import com.scientificcenter.model.users.Reviewer;
import com.scientificcenter.model.users.User;
import com.scientificcenter.service.*;
import com.scientificcenter.util.HandlerFunctions;
import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;
import org.camunda.bpm.engine.rest.dto.VariableValueDto;
import org.camunda.bpm.engine.rest.dto.task.TaskDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/reviewers")
@Api("Controller for working with journals.")
@Log4j2
public class ReviewerController {

    private final ReviewerService reviewerService;
    private final ProcessService processService;
    private final HandlerFunctions handlerFunctions;
    private final UserService userService;
    private final ReviewService reviewService;
    private final ScientificPaperService scientificPaperService;

    public ReviewerController(ReviewerService reviewerService, ProcessService processService, HandlerFunctions handlerFunctions, UserService userService,
                              ReviewService reviewService, ScientificPaperService scientificPaperService) {
        this.reviewerService = reviewerService;
        this.processService = processService;
        this.handlerFunctions = handlerFunctions;
        this.userService = userService;
        this.reviewService = reviewService;
        this.scientificPaperService = scientificPaperService;
    }

    @GetMapping("/{processId}")
    public List<ReviewerDto> getAllReviewersForScientificArea(@PathVariable String processId) {
        VariableValueDto var = this.processService.getVariableForProcess("paper", processId);
        ScientificPaper paper = (ScientificPaper) this.handlerFunctions
                .deserialize(var.getValue().toString(),
                        ScientificPaper.class);
        List<ReviewerDto> reviewerDtos = new ArrayList<>();
        List<Reviewer> reviewers = this.reviewerService.getAllReviewersForScientificAreaAndPaper(paper);
        for (Reviewer reviewer : reviewers) {
            reviewerDtos.add(ReviewerDto.builder().name(reviewer.getUser().getName())
                    .lastname(reviewer.getUser().getLastname()).id(reviewer.getId()).build());
        }
        return reviewerDtos;
    }

    @PostMapping("/{main}/{processId}")
    public ResponseEntity chooseReviewers(@PathVariable boolean main,
                                          @PathVariable String processId,
                                          @RequestBody List<ReviewerDto> reviewerDtos) {

        TaskDto taskDto = this.processService.getTaskByProcessId(processId);
        this.processService.serializeObjectAndSetAsVariable(reviewerDtos,
                User.class.getTypeName(),
                processId, "reviewers");
        this.processService.submitForm(taskDto.getId(), new ArrayList<FormSubmissionDto>());
        return ResponseEntity.ok(taskDto.getProcessInstanceId());
    }



}
