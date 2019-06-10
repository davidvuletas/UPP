package com.scientificcenter.service.delegate;

import com.scientificcenter.model.paper.ScientificPaper;
import com.scientificcenter.service.EmailService;
import com.scientificcenter.service.JournalService;
import com.scientificcenter.util.HandlerFunctions;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;

@Service("changeService")
public class ChangeService implements JavaDelegate {

    private final HandlerFunctions handlerFunctions;
    private final JournalService journalService;
    private final EmailService emailService;

    public ChangeService(HandlerFunctions handlerFunctions, JournalService journalService, EmailService emailService) {
        this.handlerFunctions = handlerFunctions;
        this.journalService = journalService;
        this.emailService = emailService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        ScientificPaper paper = (ScientificPaper) this.handlerFunctions.
                deserialize(delegateExecution.
                                getVariable("paper").toString(),
                        ScientificPaper.class);
        String mail = paper.getMainAuthor().getUser().getEmail();
        StringBuilder sb = new StringBuilder();
        sb.append("Dear ");
        sb.append(paper.getMainAuthor().getUser().getName());
        sb.append("\n\n");
        sb.append("You need to change paper ");
        sb.append(paper.getTitle());
        sb.append(", and after make that changes to publish paper again");
        sb.append("\n\n");
        sb.append("Sincerely,\nScientific center");
        this.emailService.sendMail(Collections.singletonList(mail), "Changes for paper ".concat(paper.getTitle()), sb.toString());
    }
}
