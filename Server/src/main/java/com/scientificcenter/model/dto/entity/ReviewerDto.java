package com.scientificcenter.model.dto.entity;

import com.scientificcenter.model.users.Reviewer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ReviewerDto {

    private String name;
    private String lastname;
    private Long id;
}
