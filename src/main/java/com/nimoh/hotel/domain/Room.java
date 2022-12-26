package com.nimoh.hotel.domain;

import lombok.Data;

@Data
public class Room {
    private Long id;
    private String name;
    private int maxPeople;
    private int standardPeople;
    private int countOfRooms;
    private String description;
    private String[] amenity;
}
