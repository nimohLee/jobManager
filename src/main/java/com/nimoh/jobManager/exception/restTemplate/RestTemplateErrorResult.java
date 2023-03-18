package com.nimoh.jobManager.exception.restTemplate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * 외부 API 연동 관련 에러 결과
 *
 * @author nimoh
 */
@Getter
@RequiredArgsConstructor
public enum RestTemplateErrorResult {
    LOCATION_IS_NULL(HttpStatus.BAD_REQUEST, "location is null");

    private final HttpStatus httpStatus;
    private final String message;
}
