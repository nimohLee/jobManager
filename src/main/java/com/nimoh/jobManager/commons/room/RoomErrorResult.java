package com.nimoh.jobManager.commons.room;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum RoomErrorResult {
    REQUEST_VALUE_INVALID(HttpStatus.BAD_REQUEST,"request value invalid"),
    ROOM_NOT_FOUND(HttpStatus.NO_CONTENT,"ROOM_NOT_FOUND")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
