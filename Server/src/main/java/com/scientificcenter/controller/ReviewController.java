package com.scientificcenter.controller;

import com.scientificcenter.model.dto.entity.ChangesDto;
import com.scientificcenter.model.dto.entity.ReviewDto;
import com.scientificcenter.model.dto.entity.ReviewPaperNameDto;
import com.scientificcenter.model.dto.entity.ReviewerDto;
import com.scientificcenter.model.dto.process.FormSubmissionDto;
import com.scientificcenter.model.enums.Suggestion;
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
@RequestMapping(value = "/api/review")
@Api("Controller for working with journals.")
@Log4j2
public class ReviewController {

    private final ReviewerService reviewerService;
    private final ProcessService processService;
    private final HandlerFunctions handlerFunctions;
    private final UserService userService;
    private final ReviewService reviewService;
    private final ScientificPaperService scientificPaperService;

    public ReviewController(ReviewerService reviewerService, ProcessService processService, HandlerFunctions handlerFunctions, UserService userService,
                            ReviewService reviewService, ScientificPaperService scientificPaperService) {
        this.reviewerService = reviewerService;
        this.processService = processService;
        this.handlerFunctions = handlerFunctions;
        this.userService = userService;
        this.reviewService = reviewService;
        this.scientificPaperService = scientificPaperService;
    }

    @PostMapping(value = "/{processId}")
    public ResponseEntity addReview(@PathVariable String processId, @RequestBody ReviewDto reviewDto) {
        TaskDto taskDto = null;
        List<TaskDto> tasksDto = this.processService.getTasksByProcessId(processId);
        for(TaskDto t1: tasksDto) {
            if (t1.getAssignee().equals(reviewDto.getUsername())) {
                taskDto = t1;
            }
        }

        VariableValueDto paperVar = this.processService.getVariableForProcess("paper", processId);
        ScientificPaper paper = (ScientificPaper) this.handlerFunctions.deserialize(paperVar.getValue().toString(),
                ScientificPaper.class);
        paper = this.scientificPaperService.getPaperById(paper.getId());
        User user = this.userService.findUserByUsername(reviewDto.getUsername());
        Review review = Review.builder().commentForAuthor(reviewDto.getCommentForAuthor())
                .commentForEditor(reviewDto.getCommentForEditor())
                .suggestionForAccept(reviewDto.getSuggestionForAccepting())
                .reviewer(this.reviewerService.findReviewerByUserId(user.getId())).build();
        review = this.reviewService.saveReview(review);
        paper.getReviews().add(review);
        this.scientificPaperService.savePaper(paper);
        this.processService.submitForm(taskDto.getId(), new ArrayList<FormSubmissionDto>());
        return ResponseEntity.ok(taskDto.getProcessInstanceId());
    }

    @GetMapping(value = "/all/{processId}")
    public ResponseEntity getAllReviews(@PathVariable String processId) {
        List<ReviewDto> reviewDtos = new ArrayList<>();
        TaskDto taskDto = this.processService.getTaskByProcessId(processId);
        VariableValueDto paperVar = this.processService.getVariableForProcess("paper", processId);
        ScientificPaper paper = (ScientificPaper) this.handlerFunctions.deserialize(paperVar.getValue().toString(), ScientificPaper.class);
        List<Review> reviews = this.reviewService.getAllReviewsForPaper(paper);
        for (Review review : reviews) {
            reviewDtos.add(ReviewDto.builder().commentForAuthor(review.getCommentForAuthor())
                    .commentForEditor(review.getCommentForEditor()).suggestionForAccepting(review.getSuggestionForAccept())
                    .username(review.getReviewer().getUser().getEmail().split("@")[0]).build());
        }
        return ResponseEntity.ok(ReviewPaperNameDto.builder().paperName(paper.getTitle()).reviews(reviewDtos).build());
    }
    @PostMapping(value = "/decision/{processId}")
    public ResponseEntity makeDecisionForPublishByReviews(@PathVariable String processId,
                                                          @RequestBody ChangesDto changes) {
        TaskDto task = this.processService.getTaskByProcessId(processId);
        VariableValueDto paperVar = this.processService.getVariableForProcess("paper", processId);
        VariableValueDto variableValueDto = new VariableValueDto();
        variableValueDto.setValue(setChanges(changes));
        variableValueDto.setType("String");
        this.processService.putVariableForProcess("changes", processId, variableValueDto);
        this.processService.submitForm(task.getId(), new ArrayList<>());
        return ResponseEntity.ok(task.getProcessInstanceId());
    }

    private String setChanges(ChangesDto changes) {
        switch (changes.getChanges()) {
            case ACCEPT:
                return "accept";
            case REJECT:
                return "reject";
            case ACCEPT_WITH_BIG_CHANGES:
                return "big";
            case ACCEPT_WITH_MINOR_CHANGES:
                return "minor";
            default:
                return "";
        }
    }

}
