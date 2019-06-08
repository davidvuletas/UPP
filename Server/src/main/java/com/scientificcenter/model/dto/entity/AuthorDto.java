package com.scientificcenter.model.dto.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class AuthorDto {

    private String id;
    private String name;
    private String email;
    private String city;
    private String country;
    private boolean primary;
}
