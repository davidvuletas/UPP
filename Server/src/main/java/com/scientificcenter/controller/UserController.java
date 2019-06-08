package com.scientificcenter.controller;

import com.scientificcenter.model.dto.entity.LoginDto;
import com.scientificcenter.model.dto.process.FormSubmissionDto;
import com.scientificcenter.model.dto.process.IdentityResponseDto;
import com.scientificcenter.model.users.User;
import com.scientificcenter.repository.jpa.UserRepository;
import com.scientificcenter.service.ProcessService;
import com.scientificcenter.util.HandlerFunctions;
import io.swagger.annotations.Api;
import org.camunda.bpm.engine.rest.dto.VariableValueDto;
import org.camunda.bpm.engine.rest.dto.task.TaskDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@Api("Controller for working with users.")
public class UserController {


    private final ProcessService processService;

    private final HandlerFunctions handlerFunctions;

    private final UserRepository userRepository;

    @Autowired
    public UserController(ProcessService processService, HandlerFunctions handlerFunctions, UserRepository userRepository) {
        this.processService = processService;
        this.handlerFunctions = handlerFunctions;
        this.userRepository = userRepository;
    }


    @PostMapping("/registration/{taskId}")
    public ResponseEntity receiveFormForRegister(@RequestBody List<FormSubmissionDto> form,
                                                 @PathVariable String taskId) {
        TaskDto taskDto = processService.getTaskByTaskId(taskId);
        User user = (User) this.handlerFunctions.mapFormValuesToObject(form, User.class, new User());
        this.processService.serializeObjectAndSetAsVariable(user,
                User.class.getTypeName(), taskDto.getProcessInstanceId(), "register");
        processService.submitForm(taskId, form);
        return ResponseEntity.ok(taskDto.getProcessInstanceId());
    }

    @PostMapping("/login/{taskId}")
    public ResponseEntity receiveFormForLogin(@RequestBody List<FormSubmissionDto> form,
                                              @PathVariable String taskId) {
        TaskDto taskDto = processService.getTaskByTaskId(taskId);
        LoginDto loginDto = (LoginDto) this.handlerFunctions.mapFormValuesToObject(form,
                LoginDto.class, new LoginDto());
        processService.serializeObjectAndSetAsVariable(loginDto,
                LoginDto.class.getTypeName(), taskDto.getProcessInstanceId(), "login");
        VariableValueDto var = new VariableValueDto();
        var.setValue(taskDto.getProcessInstanceId());
        var.setType("String");
        IdentityResponseDto identityResponse = this.processService.
                loginUserToCamunda(loginDto.getEmail().split("@")[0], loginDto.getPassword());
        VariableValueDto valueDto = new VariableValueDto();
        valueDto.setType("String");
        valueDto.setValue(identityResponse.getAuthenticatedUser());
        this.processService.putVariableForProcess("loggedUser",
                taskDto.getProcessInstanceId(),valueDto);
        processService.submitForm(taskId, form);
        return ResponseEntity.ok(valueDto.getValue());
    }

    @GetMapping("/{username}/role")
    public ResponseEntity getRole(@PathVariable String username) {
        User user = this.userRepository.findUserByEmailContains(username);
        return ResponseEntity.ok(user.getRoles().get(0));
    }
}
