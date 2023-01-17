package com.nimoh.hotel.commons.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserException extends RuntimeException {
    private final UserErrorResult errorResult;
}
