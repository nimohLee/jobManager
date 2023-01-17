package com.nimoh.hotel.commons.room;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RoomException extends RuntimeException{
    private final RoomErrorResult errorResult;
}
