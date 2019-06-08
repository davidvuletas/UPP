package com.scientificcenter.model.dto.process;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class VariableForm {
    private String type;
    private Object value;
    private Object valueInfo;
}
