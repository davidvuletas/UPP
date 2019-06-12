package com.scientificcenter.service;

import com.scientificcenter.model.dto.process.FormFieldsDto;
import com.scientificcenter.model.dto.process.FormSubmissionDto;
import com.scientificcenter.model.dto.process.IdentityResponseDto;
import com.scientificcenter.model.users.User;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.rest.dto.VariableValueDto;
import org.camunda.bpm.engine.rest.dto.runtime.ProcessInstanceDto;
import org.camunda.bpm.engine.rest.dto.task.TaskDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProcessService {

    ProcessInstanceDto startProcess(boolean main);

    TaskDto getTaskByProcessId(String processId);

    TaskDto getTaskByTaskId(String taskId);

    void putVariableForProcess(String name, String processId, VariableValueDto variable);

    void putFormVariableForProcess(String name, String processId, String variable);

    VariableValueDto getVariableForProcess(String name, String processId);

    void deleteVariableFromProcess(String name, String processId);

    TaskDto getCurrentTask();

    List<TaskDto> getTasksByProcessId(String processId);

    FormFieldsDto getFormDataForTask(String processId, String taskId);

    void submitForm(String taskId, List<FormSubmissionDto> form);

    void serializeObjectAndSetAsVariable(Object object, String classType, String processId, String variableName);

    IdentityResponseDto loginUserToCamunda(String username, String password);

    void setVariableForAssignee(User user, String processInstanceId);

    List<TaskDto> getTaskByAssignee(String assignee);
}
