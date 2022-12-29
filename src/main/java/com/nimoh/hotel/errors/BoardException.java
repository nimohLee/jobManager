package com.nimoh.hotel.errors;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BoardException extends RuntimeException {
    private final BoardErrorResult errorResult;
}
