package com.nimoh.hotel.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Reservation {
    private Long id;
    private User user;
    private Room room;
    private Date date;
}
