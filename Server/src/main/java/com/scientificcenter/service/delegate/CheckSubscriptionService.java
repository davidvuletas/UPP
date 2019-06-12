package com.scientificcenter.service.delegate;

import com.scientificcenter.repository.jpa.JournalRepository;
import com.scientificcenter.repository.jpa.UserRepository;
import com.scientificcenter.service.ProcessService;
import com.scientificcenter.util.HandlerFunctions;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

@Service("checkSubscriptionService")
public class CheckSubscriptionService implements JavaDelegate {

    private final String NOT_FOUND_MESSAGE = "Subscription not active";
    private final HandlerFunctions handlerFunctions;
    private final RestTemplate restTemplate;
    private final UserRepository userRepository;
    private final ProcessService processService;
    private final JournalRepository journalRepository;


    @Autowired
    public CheckSubscriptionService(HandlerFunctions handlerFunctions, RestTemplate restTemplate, UserRepository userRepository, ProcessService processService, JournalRepository journalRepository) {
        this.handlerFunctions = handlerFunctions;
        this.restTemplate = restTemplate;
        this.userRepository = userRepository;
        this.processService = processService;
        this.journalRepository = journalRepository;
    }


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Random r = new Random();
        if(r.nextInt() %2  == 0) {
            delegateExecution.setVariable("active", true);
        }else {
            delegateExecution.setVariable("active", false);

            System.out.println("OK");
        }
    }
}
