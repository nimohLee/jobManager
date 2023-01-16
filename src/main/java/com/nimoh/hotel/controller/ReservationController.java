package com.nimoh.hotel.controller;

import com.nimoh.hotel.commons.reservation.ReservationException;
import com.nimoh.hotel.data.dto.reservation.ReservationResponse;
import com.nimoh.hotel.service.reservation.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.nimoh.hotel.constants.Headers.USER_ID_HEADER;

@RestController
@RequestMapping("/api/v1/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("")
    public ResponseEntity<List<ReservationResponse>> getReservations(
            @RequestHeader(USER_ID_HEADER) final Long userId
            )
    {
        try{
            List<ReservationResponse> result = reservationService.findByUserId(userId);
            return ResponseEntity.ok().body(result);
        }catch (ReservationException e){
            return ResponseEntity.noContent().build();
        }
    }
}
