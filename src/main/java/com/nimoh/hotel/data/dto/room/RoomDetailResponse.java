package com.nimoh.hotel.data.dto.room;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class RoomDetailResponse {
    private String name;
    private int maxPeople;
    private int standardPeople;
    private int countOfRooms;
    private String description;
    private String image;
}
