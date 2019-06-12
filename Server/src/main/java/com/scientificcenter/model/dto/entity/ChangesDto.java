package com.scientificcenter.model.dto.entity;

import com.scientificcenter.model.enums.Suggestion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ChangesDto {

    private Suggestion changes;
}
