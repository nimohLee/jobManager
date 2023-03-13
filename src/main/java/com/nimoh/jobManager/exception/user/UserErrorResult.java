package com.nimoh.jobManager.exception.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum UserErrorResult {
    DUPLICATED_USER_ID(HttpStatus.CONFLICT, "duplicated user id"),
    USER_NOT_FOUND(HttpStatus.CONFLICT, "해당하는 유저가 없습니다"),
    WRONG_PASSWORD(HttpStatus.CONFLICT, "비밀번호가 틀렸습니다");
    private final HttpStatus httpStatus;
    private final String message;
}
