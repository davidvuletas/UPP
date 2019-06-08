package com.scientificcenter.service.delegate;

import com.scientificcenter.exceptions.NotFoundException;
import com.scientificcenter.model.CamundaConstants;
import com.scientificcenter.model.dto.entity.JournalViewDto;
import com.scientificcenter.model.dto.entity.LoginDto;
import com.scientificcenter.model.enums.Role;
import com.scientificcenter.model.journal.ScientificJournal;
import com.scientificcenter.model.users.User;
import com.scientificcenter.repository.jpa.JournalRepository;
import com.scientificcenter.repository.jpa.UserRepository;
import com.scientificcenter.service.ProcessService;
import com.scientificcenter.util.HandlerFunctions;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.rest.dto.VariableValueDto;
import org.camunda.bpm.engine.rest.dto.task.UserIdDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service("loginService")
public class LoginService implements JavaDelegate {

    private final HandlerFunctions handlerFunctions;
    private final RestTemplate restTemplate;
    private final UserRepository userRepository;
    private final ProcessService processService;
    private final JournalRepository journalRepository;


    @Autowired
    public LoginService(HandlerFunctions handlerFunctions, RestTemplate restTemplate, UserRepository userRepository, ProcessService processService, JournalRepository journalRepository) {
        this.handlerFunctions = handlerFunctions;
        this.restTemplate = restTemplate;
        this.userRepository = userRepository;
        this.processService = processService;
        this.journalRepository = journalRepository;
    }


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        LoginDto loginDto = (LoginDto) handlerFunctions.
                deserialize(delegateExecution.
                                getVariable("login").toString(),
                        LoginDto.class);
        Boolean exists = this.userRepository.existsUserByEmailAndPassword(loginDto.getEmail(), loginDto.getPassword());
        if (!exists) {
            throw new NotFoundException("Invalid login, email or password are not correct.\\nPlease try again.");
            /* throw new BpmnError("INV_1","Invalid login, email or password are not correct.\nPlease try again.");*/
        }
    }
}
