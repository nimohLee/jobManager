package com.nimoh.jobManager.data.dto.reservation;

import com.nimoh.jobManager.data.entity.Room;
import com.nimoh.jobManager.data.entity.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

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
