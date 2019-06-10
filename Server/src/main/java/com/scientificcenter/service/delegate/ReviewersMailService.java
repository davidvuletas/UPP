package com.scientificcenter.service.delegate;

import com.scientificcenter.model.dto.entity.ReviewerDto;
import com.scientificcenter.model.journal.ScientificAreaCodeBook;
import com.scientificcenter.model.paper.ScientificPaper;
import com.scientificcenter.model.users.Reviewer;
import com.scientificcenter.service.EmailService;
import com.scientificcenter.service.ProcessService;
import com.scientificcenter.service.ReviewerService;
import com.scientificcenter.util.HandlerFunctions;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.rest.dto.VariableValueDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service("reviewersMailService")
public class ReviewersMailService implements JavaDelegate {

    private final EmailService emailService;
    private final ReviewerService reviewerService;
    private final ProcessService processService;
    private final HandlerFunctions handlerFunctions;

    public ReviewersMailService(EmailService emailService, ReviewerService reviewerService,
                                ProcessService processService, HandlerFunctions handlerFunctions) {
        this.emailService = emailService;
        this.reviewerService = reviewerService;
        this.processService = processService;
        this.handlerFunctions = handlerFunctions;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        VariableValueDto reviewersVar = this.processService.getVariableForProcess("reviewers",
                delegateExecution.getProcessInstanceId());
        VariableValueDto paperDto = this.processService.getVariableForProcess("paper",
                delegateExecution.getProcessInstanceId());
        ScientificPaper paper = (ScientificPaper) this.handlerFunctions
                .deserialize(paperDto.getValue().toString(), ScientificPaper.class);
        List<ReviewerDto> reviewerDtos = (ArrayList<ReviewerDto>) this.handlerFunctions
                .deserialize(reviewersVar.getValue().toString(),
                        ReviewerDto.class);
        List<String> reviewerAssigne = new ArrayList<>();
        for (ReviewerDto dto : reviewerDtos) {
            Reviewer reviewer = this.reviewerService.findReviewerById(dto.getId());
            reviewerAssigne.add(reviewer.getUser().getEmail().split("@")[0]);
            StringBuilder message = new StringBuilder("Dear ");
            message.append(dto.getName());
            message.append("\n\n");
            message.append("You are chosen to do review for paper ");
            message.append(paper.getTitle());
            message.append(" You have 1 hour to do review!");
            message.append("\n\n");
            message.append("Sincerely,\nScientific center");
            emailService.sendMail(Collections.singletonList(reviewer.getUser().getEmail()),
                    "Review for paper", message.toString());
        }

        delegateExecution.setVariable("assigneeList", reviewerAssigne);
    }
}
