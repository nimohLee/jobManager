package com.nimoh.jobManager.exception.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserException extends RuntimeException {
    private final UserErrorResult errorResult;
}
