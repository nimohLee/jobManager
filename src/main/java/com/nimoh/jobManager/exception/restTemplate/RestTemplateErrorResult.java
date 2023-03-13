package com.nimoh.jobManager.exception.restTemplate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum RestTemplateErrorResult {
    LOCATION_IS_NULL(HttpStatus.BAD_REQUEST, "location is null");

    private final HttpStatus httpStatus;
    private final String message;
}
