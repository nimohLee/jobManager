package com.nimoh.hotel.service.reservation;

import com.nimoh.hotel.data.dto.reservation.ReservationRequest;
import com.nimoh.hotel.data.dto.reservation.ReservationResponse;

import java.util.List;

public interface ReservationService {
    List<ReservationResponse> findByUserId(Long userId);

    ReservationResponse create(ReservationRequest reservationRequest, Long userId);

    ReservationResponse delete(Long reservationId, Long userId);
}
