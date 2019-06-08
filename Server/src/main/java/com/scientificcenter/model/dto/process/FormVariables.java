package com.scientificcenter.model.dto.process;

import lombok.Builder;
import lombok.Data;
import org.camunda.bpm.engine.form.TaskFormData;

import java.util.HashMap;
import java.util.Map;


public class FormVariables {
    private Map<String, VariableForm> formVariables;

    public FormVariables() {
        this.formVariables = new HashMap<>();
    }

    public Map<String, VariableForm> getFormVariables() {
        return formVariables;
    }

    public void setFormVariables(Map<String, VariableForm> formVariables) {
        this.formVariables = formVariables;
    }
}
