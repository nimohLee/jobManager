package com.nimoh.jobManager.service.reservation;

import com.nimoh.jobManager.data.dto.reservation.ReservationRequest;
import com.nimoh.jobManager.data.dto.reservation.ReservationResponse;

import java.util.List;

public interface ReservationService {
    List<ReservationResponse> findByUserId(Long userId);

    ReservationResponse create(ReservationRequest reservationRequest, Long userId);

    ReservationResponse delete(Long reservationId, Long userId);
}
