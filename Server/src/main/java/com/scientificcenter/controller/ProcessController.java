package com.scientificcenter.controller;

import com.scientificcenter.model.dto.process.FormFieldsDto;
import com.scientificcenter.model.dto.process.FormSubmissionDto;
import com.scientificcenter.model.enums.Role;
import com.scientificcenter.model.users.User;
import com.scientificcenter.repository.jpa.UserRepository;
import com.scientificcenter.service.ProcessService;
import io.swagger.annotations.Api;
import org.camunda.bpm.engine.rest.dto.VariableValueDto;
import org.camunda.bpm.engine.rest.dto.runtime.ProcessInstanceDto;
import org.camunda.bpm.engine.rest.dto.task.TaskDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/process")
@Api("Controller for working with process.")
public class ProcessController {


    private Logger logger = LoggerFactory.getLogger(ProcessController.class);

    private final ProcessService processService;

    private final UserRepository userRepository;

    @Autowired
    public ProcessController(ProcessService processService, UserRepository userRepository) {
        this.processService = processService;
        this.userRepository = userRepository;
    }

    @GetMapping(path = "/start-login-process")
    public FormFieldsDto startProcess() {
        ProcessInstanceDto pi = processService.startProcess(false);
        TaskDto task = processService.getTaskByProcessId(pi.getId());
        return processService.getFormDataForTask(pi.getId(), task.getId());
    }

    @GetMapping(path = "/start-main-process/{username}")
    public FormFieldsDto startProcess(@PathVariable String username) {
        ProcessInstanceDto pi = processService.startProcess(true);
        User user = this.userRepository.findUserByEmailContains(username);
        if (user.getRoles().contains(Role.EDITOR)) {
            VariableValueDto editor = new VariableValueDto();
            editor.setType("String");
            editor.setValue(user.getEmail());
            this.processService.putVariableForProcess("loggedInEditor", pi.getId(), editor);
            this.processService.putVariableForProcess("loggedInAuthor", pi.getId(), new VariableValueDto());

        } else if (user.getRoles().contains(Role.AUTHOR)) {
            VariableValueDto author = new VariableValueDto();
            author.setType("String");
            author.setValue(user.getEmail());
            this.processService.putVariableForProcess("loggedInAuthor", pi.getId(), author);
            this.processService.putVariableForProcess("loggedInEditor", pi.getId(), new VariableValueDto());
        }
        this.processService.setVariableForAssignee(user, pi.getId());

        TaskDto task = processService.getTaskByProcessId(pi.getId());

        return processService.getFormDataForTask(pi.getId(), task.getId());

    }

    @PostMapping(path = "/form-chosen/{taskId}")
    public ResponseEntity submitFormLoginOrRegister(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
        TaskDto task = processService.getTaskByTaskId(taskId);
        VariableValueDto varDto = new VariableValueDto();

        if (dto.get(0).getFieldId().equals("register")) {
            varDto.setType("Boolean");
            varDto.setValue(true);
            processService.putVariableForProcess("register",
                    task.getProcessInstanceId(), varDto);
        }
        if (dto.get(0).getFieldId().equals("login")) {
            varDto.setType("Boolean");
            varDto.setValue(false);
            processService.putVariableForProcess("register",
                    task.getProcessInstanceId(), varDto);
        }
        processService.submitForm(taskId, dto);
        return ResponseEntity.ok(task.getProcessInstanceId());
    }

    @GetMapping(path = "/get-task-by-process-id/{processId}")
    public FormFieldsDto getCurrentTask(@PathVariable String processId) {
        TaskDto task = processService.getTaskByProcessId(processId);
        return processService.getFormDataForTask(task.getProcessInstanceId(), task.getId());
    }

    @GetMapping(path = "/get-task/{assignee}")
    public List<FormFieldsDto> getTaskByAssignee(@PathVariable String assignee) {
        List<FormFieldsDto> formFieldsDtos = new ArrayList<>();
        List<TaskDto> tasks = this.processService.getTaskByAssignee(assignee);
        int i = 0;
        for (TaskDto task : tasks) {
            formFieldsDtos.add(this.processService.
                    getFormDataForTask(task.getProcessInstanceId(),
                            task.getId()));
            formFieldsDtos.get(i).setTaskName(task.getName());

        }
        return formFieldsDtos;
    }

}
