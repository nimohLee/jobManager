package com.nimoh.hotel.commons.reservation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ReservationErrorResult {

    RESERVATION_NOT_FOUND(HttpStatus.BAD_REQUEST,"Reservation not found "),
    USER_NOT_MATCHED(HttpStatus.FORBIDDEN,"User not matched"),
    NO_EMPTY_ROOM(HttpStatus.CONFLICT,"No empty room"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST,"Bad Request")
;
    private final HttpStatus httpStatus;
    private final String message;
}
