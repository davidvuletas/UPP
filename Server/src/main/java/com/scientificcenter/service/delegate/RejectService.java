package com.scientificcenter.service.delegate;

import com.scientificcenter.model.enums.Status;
import com.scientificcenter.model.paper.ScientificPaper;
import com.scientificcenter.service.EmailService;
import com.scientificcenter.service.ScientificPaperService;
import com.scientificcenter.util.HandlerFunctions;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service("rejectService")
public class RejectService implements JavaDelegate {

    private final HandlerFunctions handlerFunctions;
    private final EmailService emailService;
    private final ScientificPaperService paperService;
    public RejectService(HandlerFunctions handlerFunctions, EmailService emailService, ScientificPaperService paperService) {
        this.handlerFunctions = handlerFunctions;
        this.emailService = emailService;
        this.paperService = paperService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        ScientificPaper paper = (ScientificPaper) this.handlerFunctions.
                deserialize(delegateExecution.
                                getVariable("paper").toString(),
                        ScientificPaper.class);
        paper.setStatus(Status.REJECTED);
        this.paperService.savePaper(paper);

        StringBuilder message = new StringBuilder("Dear ");
        message.append(paper.getMainAuthor().getUser().getName());
        message.append("\n\n");
        message.append("We are rejection your paper ");
        message.append(paper.getTitle());
        message.append(" due to bad format for journal you have chosen!");
        message.append("\n\n");
        message.append("Sincerely,\nScientific center");

        this.emailService.sendMail(Collections.singletonList(paper.getMainAuthor().getUser().getEmail()),
                "Reject of paper ".concat(paper.getTitle()), message.toString());
    }
}
