package com.nimoh.jobManager.exception.job;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class JobException extends RuntimeException {
    private final JobErrorResult errorResult;
}
