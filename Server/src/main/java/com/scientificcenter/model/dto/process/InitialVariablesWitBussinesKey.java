package com.scientificcenter.model.dto.process;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.model.dmn.instance.Variable;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class InitialVariablesWitBussinesKey {
    VariableMap variables;
    String bussinesKey;


}
