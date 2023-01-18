package com.nimoh.hotel.data.dto.reservation;

import com.nimoh.hotel.data.entity.Room;
import com.nimoh.hotel.data.entity.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
public class ReservationResponse {
    private Long id;

    private User user;

    private Room room;

    private LocalDate checkIn;

    private LocalDate checkOut;

    private int totalPrice;
}
