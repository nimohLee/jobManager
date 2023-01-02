package com.nimoh.hotel.errors;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum RoomErrorResult {
    REQUEST_VALUE_INVALID(HttpStatus.BAD_REQUEST,"request value invalid")
    ;

    private final HttpStatus status;
    private final String message;
}
