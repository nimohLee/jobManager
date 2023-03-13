package com.nimoh.jobManager.exception.restTemplate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RestTemplateException extends RuntimeException {
    private final RestTemplateErrorResult errorResult;
}
