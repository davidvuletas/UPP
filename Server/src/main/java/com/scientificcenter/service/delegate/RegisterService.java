package com.scientificcenter.service.delegate;

import com.scientificcenter.exceptions.AlreadyExistsException;
import com.scientificcenter.model.CamundaConstants;
import com.scientificcenter.model.users.User;
import com.scientificcenter.repository.jpa.UserRepository;
import com.scientificcenter.service.ProcessService;
import com.scientificcenter.util.HandlerFunctions;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service("registerService")
public class RegisterService implements JavaDelegate {
    private final HandlerFunctions handlerFunctions;
    private final RestTemplate restTemplate;
    private final UserRepository userRepository;
    private final ProcessService processService;

    //@Autowired
    public RegisterService(HandlerFunctions handlerFunctions, RestTemplate restTemplate, UserRepository userRepository, ProcessService processService) {
        this.handlerFunctions = handlerFunctions;
        this.restTemplate = restTemplate;
        this.userRepository = userRepository;
        this.processService = processService;
    }


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        User user = (User) handlerFunctions.
                deserialize(delegateExecution.
                                getVariable("register").toString(),
                        User.class);
        boolean exists = this.userRepository.existsUserByEmail(user.getEmail());
        if (exists) {
            throw new AlreadyExistsException(user.getEmail());
        } else {
            user = this.userRepository.save(user);
            this.restTemplate.postForEntity(CamundaConstants.URL_FOR_CREATE_USER,
                    this.handlerFunctions.mapUserToCamundaUser(user), Void.class);
        }
    }
}
