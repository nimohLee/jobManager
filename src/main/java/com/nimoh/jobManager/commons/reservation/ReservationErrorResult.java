package com.nimoh.jobManager.commons.reservation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ReservationErrorResult {

    RESERVATION_NOT_FOUND(HttpStatus.NO_CONTENT,"예약이 없습니다"),
    USER_NOT_MATCHED(HttpStatus.FORBIDDEN,"해당하는 유저가 존재하지 않습니다"),
    NO_EMPTY_ROOM(HttpStatus.CONFLICT,"빈 방이 없습니다"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST,"존재하지 않는 유저이거나 존재하지 않는 방입니다")
;
    private final HttpStatus httpStatus;
    private final String message;
}
