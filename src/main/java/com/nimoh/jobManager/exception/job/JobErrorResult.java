package com.nimoh.jobManager.exception.job;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * 지원 내역 관련 에러 결과
 *
 * @author nimoh
 */
@Getter
@RequiredArgsConstructor
public enum JobErrorResult {
    UNKNOWN_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 에러가 발생하였습니다"),
    APPLY_NOT_FOUND(HttpStatus.BAD_REQUEST, "지원 내역이 없습니다"),
    REQUEST_VALUE_INVALID(HttpStatus.BAD_REQUEST, "요청값이 잘못되었습니다"),
    NO_PERMISSION(HttpStatus.FORBIDDEN, "작성자만 수정 및 삭제할 수 있습니다");
    private final HttpStatus httpStatus;
    private final String message;
}
