package com.nimoh.jobManager.commons.token;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum TokenErrorResult {
    REFRESH_TOKEN_IS_NULL(HttpStatus.BAD_REQUEST, "Refresh token is null");

    private final HttpStatus httpStatus;
    private final String message;
}
