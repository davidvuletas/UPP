package com.scientificcenter.service.delegate;

import com.scientificcenter.model.journal.ScientificJournal;
import com.scientificcenter.model.paper.ScientificPaper;
import com.scientificcenter.service.EmailService;
import com.scientificcenter.service.JournalService;
import com.scientificcenter.service.ProcessService;
import com.scientificcenter.service.ReviewerService;
import com.scientificcenter.util.HandlerFunctions;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.rest.dto.VariableValueDto;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service("reviewersDoneService")
public class ReviewersDoneService implements JavaDelegate {

    private final EmailService emailService;
    private final ProcessService processService;
    private final HandlerFunctions handlerFunctions;
    private final JournalService journalService;

    public ReviewersDoneService(EmailService emailService,
                                ProcessService processService, HandlerFunctions handlerFunctions, JournalService journalService) {
        this.emailService = emailService;
        this.journalService = journalService;
        this.processService = processService;
        this.handlerFunctions = handlerFunctions;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        VariableValueDto paperDto = this.processService.getVariableForProcess("paper",
                delegateExecution.getProcessInstanceId());
        ScientificPaper paper = (ScientificPaper) this.handlerFunctions
                .deserialize(paperDto.getValue().toString(), ScientificPaper.class);
        ScientificJournal journal = this.journalService.findJournalByPaper(paper.getId());

        StringBuilder message = new StringBuilder("Dear ");
        message.append(journal.getBoard().getChiefEditor().getUser().getName());
        message.append("\n\n");
        message.append("All reviews are submitted for paper ");
        message.append(paper.getTitle());
        message.append(", you need to analyze reviews and to do decision for that paper.");
        message.append("\n\n");
        message.append("Sincerely,\nScientific center");
        emailService.sendMail(Collections.singletonList(journal.getBoard().getChiefEditor().getUser().getEmail()),
                "All reviews are submitted", message.toString());

    }
}
