package com.nimoh.jobManager.exception.crawler;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum CrawlerErrorResult {
    UNKNOWN_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR,"알 수 없는 에러가 발생하였습니다."),
    OPTION_NULL_EXCEPTION(HttpStatus.BAD_REQUEST, "검색 옵션은 NULL이 될 수 없습니다"),
    OPTION_BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 검색 옵션이 있습니다"),
    RESULT_NOT_FOUND(HttpStatus.NO_CONTENT,"검색결과가 없습니다"),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
