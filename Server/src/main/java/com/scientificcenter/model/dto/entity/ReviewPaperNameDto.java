package com.scientificcenter.model.dto.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ReviewPaperNameDto {
    private List<ReviewDto> reviews;
    private String paperName;
}
