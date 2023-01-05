package com.nimoh.hotel.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum UserErrorResult {
    DUPLICATED_USER_ID(HttpStatus.CONFLICT,"duplicated user id"),
    ;
    private final HttpStatus status;
    private final String message;
}
