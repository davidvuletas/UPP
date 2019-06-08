package com.scientificcenter.service.impl;

import com.scientificcenter.model.CamundaConstants;
import com.scientificcenter.model.dto.process.*;
import com.scientificcenter.model.enums.Role;
import com.scientificcenter.model.users.User;
import com.scientificcenter.service.ProcessService;
import com.scientificcenter.util.HandlerFunctions;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.rest.dto.VariableValueDto;
import org.camunda.bpm.engine.rest.dto.runtime.ProcessInstanceDto;
import org.camunda.bpm.engine.rest.dto.task.TaskDto;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Service
public class ProcessServiceImpl implements ProcessService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private FormService formService;

    @Autowired
    private HandlerFunctions handlerFunctions;

    @Override
    public ProcessInstanceDto startProcess(boolean main) {
        ProcessInstanceDto pi;
        VariableMap variables = Variables.createVariables();
        variables.putValue("isValid", true);
        if(!main) {
            pi = restTemplate.postForEntity(
                    CamundaConstants.URL_FOR_USER_PROCESS, variables, ProcessInstanceDto.class).getBody();
        } else {
            pi = restTemplate.postForEntity(
                    CamundaConstants.URL_FOR_MAIN_START_PROCESS, variables, ProcessInstanceDto.class).getBody();
        }

        return pi;
    }

    @Override
    public TaskDto getTaskByProcessId(String processId) {
        ResponseEntity<TaskDto[]> tasksResponse = restTemplate.getForEntity(CamundaConstants.URL_FOR_GET_PROCESS_BY_ID.concat(processId),
                TaskDto[].class);
        List<TaskDto> taskDtoList = Arrays.asList(tasksResponse.getBody());
        return taskDtoList.get(0);
    }

    @Override
    public FormFieldsDto getFormDataForTask(String processId, String taskId) {
        TaskFormData tfd = formService.getTaskFormData(taskId);
        List<FormField> properties = tfd.getFormFields();
        return new FormFieldsDto(taskId, processId, properties);
    }

    @Override
    public TaskDto getCurrentTask() {
        ResponseEntity<TaskDto[]> tasksResponse = restTemplate.getForEntity(CamundaConstants.URL_FOR_TASK, TaskDto[].class);
        List<TaskDto> taskDtoList = Arrays.asList(tasksResponse.getBody());
        return taskDtoList.get(0);
    }

    @Override
    public TaskDto getTaskByTaskId(String taskId) {
        return this.restTemplate.getForEntity(
                CamundaConstants.URL_FOR_TASK.concat(taskId), TaskDto.class)
                .getBody();
    }

    @Override
    public void putVariableForProcess(String name, String processId, VariableValueDto variable) {
        this.restTemplate.put(CamundaConstants.URL_FOR_PROCESS_INSTANCE
                .concat(processId)
                .concat("/variables/")
                .concat(name), variable);
    }

    @Override
    public void putFormVariableForProcess(String name, String processId, String variable) {
        this.restTemplate.put(CamundaConstants.URL_FOR_PROCESS_INSTANCE
                .concat(processId)
                .concat("/variables/")
                .concat(name), variable);
    }

    @Override
    public VariableValueDto getVariableForProcess(String name, String processId) {
        return this.restTemplate.getForEntity(CamundaConstants.URL_FOR_PROCESS_INSTANCE
                .concat(processId)
                .concat("/variables/")
                .concat(name), VariableValueDto.class).getBody();
    }

    @Override
    public void submitForm(String taskId, List<FormSubmissionDto> form) {
        HashMap<String, Object> map = this.handlerFunctions.mapListToDto(form);
        this.restTemplate.postForEntity(CamundaConstants.URL_FOR_TASK
                .concat(taskId)
                .concat("/submit-form"), map, Void.class);
    }

    @Override
    public void deleteVariableFromProcess(String name, String processId) {
        this.restTemplate.delete(CamundaConstants.URL_FOR_PROCESS_INSTANCE
                .concat(processId)
                .concat("/variables/")
                .concat(name));
    }

    @Override
    public void serializeObjectAndSetAsVariable(Object object, String classType, String processId, String variableName) {
        ObjectValue customerDataValue = Variables.objectValue(object)
                .serializationDataFormat(Variables.SerializationDataFormats.JAVA)
                .create();
        String serializedText = handlerFunctions.serialize(customerDataValue);
        VariableValueDto varForm = new VariableValueDto();
        varForm.setValue(serializedText);
        varForm.setType("String");
        varForm.setValueInfo(handlerFunctions.createValueInfo(classType));
        this.putVariableForProcess(variableName, processId, varForm);
    }

    @Override
    public IdentityResponseDto loginUserToCamunda(String username, String password) {
        return this.restTemplate.postForEntity(CamundaConstants.URL_FOR_LOGIN_USER,
                new CamundaLoginDto(username, password), IdentityResponseDto.class).getBody();

    }

    @Override
    public void setVariableForAssignee(User user, String processInstanceId) {
        String userName = user.getEmail().split("@")[0];


        if (user.getRoles().contains(Role.AUTHOR)) {
            VariableValueDto author = new VariableValueDto();
            author.setType("String");
            author.setValue(userName);
            this.putVariableForProcess("loggedInAuthor", processInstanceId, author);
        } else if (user.getRoles().contains(Role.EDITOR)) {
            VariableValueDto editor = new VariableValueDto();
            editor.setType("String");
            editor.setValue(userName);
            this.putVariableForProcess("loggedInEditor", processInstanceId, editor);
        }
    }

    @Override
    public List<TaskDto> getTaskByAssignee(String assignee) {
        ResponseEntity<TaskDto[]> tasksResponse =this.restTemplate.getForEntity(
                CamundaConstants.URL_FOR_ASSIGNE_TASK
                        .concat(assignee),
                TaskDto[].class);
        List<TaskDto> taskDtoList = Arrays.asList(tasksResponse.getBody());
        return taskDtoList;
    }
}
