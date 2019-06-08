package com.scientificcenter.service.delegate;

import com.scientificcenter.model.journal.ScientificJournal;
import com.scientificcenter.model.paper.ScientificPaper;
import com.scientificcenter.service.EmailService;
import com.scientificcenter.service.JournalService;
import com.scientificcenter.util.HandlerFunctions;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service("storePaperService")
public class StorePaperService implements JavaDelegate {

    private final JavaMailSender javaMailSender;

    private final HandlerFunctions handlerFunctions;

    private final JournalService journalService;

    private final EmailService emailService;

    public StorePaperService(JavaMailSender javaMailSender, HandlerFunctions handlerFunctions,
                             JournalService journalService, EmailService emailService) {
        this.javaMailSender = javaMailSender;
        this.handlerFunctions = handlerFunctions;
        this.journalService = journalService;
        this.emailService = emailService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("Here I am");
        ScientificPaper paper = (ScientificPaper) this.handlerFunctions.
                deserialize(delegateExecution.
                                getVariable("paper").toString(),
                        ScientificPaper.class);
        String journalId = (String) delegateExecution.getVariable("journal");
        ScientificJournal journal = this.journalService.findJournalById(Long.valueOf(journalId));
        delegateExecution.setVariable("loggedInEditor",
                journal.getBoard().getChiefEditor().getUser().getEmail().split("@")[0]);

        this.emailService.sendMail(Arrays.asList(journal.getBoard().getChiefEditor().getUser().getEmail(),
                paper.getMainAuthor().getUser().getEmail()), "Paper ".concat(paper.getTitle().concat(" are uploaded")),
                "Dear, \n Paper".concat(paper.getTitle())
                        .concat(" are uploaded, you can see him on page http://localhost:4200 ")
                        .concat("\n\nThanks in advice,\nScientific center"));
    }
}
