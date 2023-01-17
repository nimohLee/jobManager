package com.nimoh.hotel.commons.board;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BoardException extends RuntimeException {
    private final BoardErrorResult errorResult;
}
