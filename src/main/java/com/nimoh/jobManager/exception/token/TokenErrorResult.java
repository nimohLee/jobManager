package com.nimoh.jobManager.exception.token;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * 토큰 관련 에러 결과
 *
 * @author nimoh
 */
@RequiredArgsConstructor
@Getter
public enum TokenErrorResult {
    REFRESH_TOKEN_IS_NULL(HttpStatus.BAD_REQUEST, "Refresh token is null"),
    INVALID_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "Invalid refresh token"),
    INVALID_ACCESS_TOKEN(HttpStatus.BAD_REQUEST, "Invalid access token");

    private final HttpStatus httpStatus;
    private final String message;
}
