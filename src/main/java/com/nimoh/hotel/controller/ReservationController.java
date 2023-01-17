package com.nimoh.hotel.controller;

import com.nimoh.hotel.commons.reservation.ReservationErrorResult;
import com.nimoh.hotel.commons.reservation.ReservationException;
import com.nimoh.hotel.data.dto.reservation.ReservationRequest;
import com.nimoh.hotel.data.dto.reservation.ReservationResponse;
import com.nimoh.hotel.service.reservation.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.nimoh.hotel.constants.Headers.USER_ID_HEADER;


@Slf4j
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
            List<ReservationResponse> result = reservationService.findByUserId(userId);
            return ResponseEntity.ok().body(result);
    }

    @PostMapping("")
    public ResponseEntity<ReservationResponse> postReservation(
            @RequestHeader(USER_ID_HEADER) final Long userId,
            @RequestBody final ReservationRequest reservationRequest
            ){
            ReservationResponse result = reservationService.create(reservationRequest, userId);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}
