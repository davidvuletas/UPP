package com.scientificcenter.service.delegate;

import com.scientificcenter.model.dto.entity.JournalViewDto;
import com.scientificcenter.model.journal.ScientificAreaCodeBook;
import com.scientificcenter.model.users.User;
import com.scientificcenter.repository.jpa.ScientificAreaCodeBookRepository;
import com.scientificcenter.service.ProcessService;
import com.scientificcenter.service.UserService;
import com.scientificcenter.service.form.CamundaFormService;
import com.scientificcenter.util.HandlerFunctions;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import org.camunda.bpm.engine.variable.Variables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static org.camunda.bpm.engine.variable.Variables.objectValue;
import static org.camunda.bpm.engine.variable.Variables.serializedObjectValue;

@Service("getDataFormService")
public class GetDataUploadFormService implements JavaDelegate {

    private final HandlerFunctions handlerFunctions;
    private final ProcessService processService;
    private final CamundaFormService camundaFormService;
    private final UserService userService;
    private final ScientificAreaCodeBookRepository areaCodeBookRepository;
    private FormService formService;

    @Autowired
    public GetDataUploadFormService(HandlerFunctions handlerFunctions, ProcessService processService,
                                    CamundaFormService camundaFormService, UserService userService, ScientificAreaCodeBookRepository areaCodeBookRepository, FormService formService) {
        this.handlerFunctions = handlerFunctions;
        this.processService = processService;
        this.camundaFormService = camundaFormService;
        this.userService = userService;
        this.areaCodeBookRepository = areaCodeBookRepository;
        this.formService = formService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        List<User> authors = this.userService.getAllAuthors();
        this.processService.serializeObjectAndSetAsVariable(authors,
                User.class.getTypeName(),
                delegateExecution.getProcessInstanceId(), "authors");

        List<ScientificAreaCodeBook> codeBooks = this.areaCodeBookRepository.findAll();
        this.processService.serializeObjectAndSetAsVariable(codeBooks,
                ScientificAreaCodeBook.class.getTypeName(),
                delegateExecution.getProcessInstanceId(), "codeBooks");

    }
}
