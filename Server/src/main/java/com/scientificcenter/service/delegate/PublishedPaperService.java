package com.scientificcenter.service.delegate;


import com.scientificcenter.model.enums.Status;
import com.scientificcenter.model.paper.ScientificPaper;
import com.scientificcenter.service.EmailService;
import com.scientificcenter.service.ProcessService;
import com.scientificcenter.service.ScientificPaperService;
import com.scientificcenter.util.HandlerFunctions;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;

@Service("publishedPaperService")
public class PublishedPaperService implements JavaDelegate {

    private final HandlerFunctions handlerFunctions;
    private final ProcessService processService;
    private final EmailService emailService;
    private final ScientificPaperService scientificPaperService;

    public PublishedPaperService(HandlerFunctions handlerFunctions, ProcessService processService,
                                 EmailService emailService, ScientificPaperService scientificPaperService) {
        this.handlerFunctions = handlerFunctions;
        this.processService = processService;
        this.emailService = emailService;
        this.scientificPaperService = scientificPaperService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        ScientificPaper paper = (ScientificPaper) this.handlerFunctions.
                deserialize(delegateExecution.
                                getVariable("paper").toString(),
                        ScientificPaper.class);
        paper.setStatus(Status.ACCEPTED);
        paper.setDoi(UUID.randomUUID().toString());
        this.scientificPaperService.savePaper(paper);
        StringBuilder message = new StringBuilder("Dear ");
        message.append(paper.getMainAuthor().getUser().getName());
        message.append("\n\n");
        message.append("Your paper ");
        message.append(paper.getTitle());
        message.append(" is uploaded finally, we have added DOI to paper");
        message.append("\n\n");
        message.append("Sincerely,\nScientific center");
        emailService.sendMail(Collections.singletonList(paper.getMainAuthor().getUser().getEmail()),
                "Paper ".concat(paper.getTitle()).concat(" accepted"), message.toString());
    }
}
