package com.scientificcenter.model.dto.entity;

import com.scientificcenter.model.enums.Suggestion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ReviewDto {
    private String username;
    private String commentForAuthor;
    private String commentForEditor;
    private Suggestion suggestionForAccepting;

}
