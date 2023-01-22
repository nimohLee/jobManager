package com.nimoh.jobManager.commons.reservation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReservationException extends RuntimeException{
    private final ReservationErrorResult errorResult;
}
