package com.nimoh.hotel.commons.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum UserErrorResult {
    DUPLICATED_USER_ID(HttpStatus.CONFLICT,"duplicated user id"),
    ;
    private final HttpStatus status;
    private final String message;
}
