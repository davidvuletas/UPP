package com.scientificcenter.model.dto.process;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class IdentityResponseDto {

    private String authenticatedUser;
    private boolean isAuthenticated;
}
