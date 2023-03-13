package com.nimoh.jobManager.exception.token;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class TokenException extends RuntimeException{
    private final TokenErrorResult errorResult;
}
