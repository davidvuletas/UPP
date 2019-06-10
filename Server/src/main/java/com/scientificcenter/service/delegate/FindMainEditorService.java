package com.scientificcenter.service.delegate;

import com.scientificcenter.model.journal.ScientificJournal;
import com.scientificcenter.model.paper.ScientificPaper;
import com.scientificcenter.model.users.User;
import com.scientificcenter.service.EmailService;
import com.scientificcenter.service.JournalService;
import com.scientificcenter.service.UserService;
import com.scientificcenter.util.HandlerFunctions;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service("findMainEditorService")
public class FindMainEditorService implements JavaDelegate {

    private final HandlerFunctions handlerFunctions;
    private final EmailService emailService;
    private final UserService userService;
    private final JournalService journalService;

    public FindMainEditorService(HandlerFunctions handlerFunctions, EmailService emailService, UserService userService, JournalService journalService) {
        this.handlerFunctions = handlerFunctions;
        this.emailService = emailService;
        this.userService = userService;
        this.journalService = journalService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        ScientificPaper paper = (ScientificPaper) this.handlerFunctions.
                deserialize(delegateExecution.
                                getVariable("paper").toString(),
                        ScientificPaper.class);
        ScientificJournal journal = this.journalService.findJournalByPaper(paper.getId());

        List<User> editors = this.userService.findUsersWhichAreCorrespondingForArea(paper.getScientificArea().getId());
        String name;
        String mail;
        if (editors.size() == 0) {
            name = journal.getBoard().getChiefEditor().getUser().getName();
            mail = journal.getBoard().getChiefEditor().getUser().getEmail();
        } else {
            name = editors.get(0).getName();
            mail = editors.get(0).getEmail();
        }
        delegateExecution.setVariable("chosenEditor", mail.split("@")[0]);
        System.out.println(name + ", " + mail);
        StringBuilder message = new StringBuilder();
        message.append("Dear ");
        message.append(editors.get(0).getName());
        message.append("\n\n");
        message.append("You are chosen by knowledge about scientific area related to paper ");
        message.append(paper.getTitle());
        message.append(" which are currently uploaded.\n");
        message.append("Action that you need to do is to choose at least two reviewers.\n\n");
        message.append("Sincerely,\nScientific center");
        this.emailService.sendMail(Collections.singletonList(mail), message.toString(), name);

    }
}
