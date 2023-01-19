package com.nimoh.hotel.controller;
import com.nimoh.hotel.data.dto.reservation.ReservationRequest;
import com.nimoh.hotel.data.dto.reservation.ReservationResponse;
import com.nimoh.hotel.data.dto.user.UserResponse;
import com.nimoh.hotel.service.reservation.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


/**
 * 예약 컨트롤러
 * @author nimoh
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Operation(summary = "예약 조회", description = "예약을 모두 조회합니다")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "예약 조회에 성공했습니다"),
            @ApiResponse(responseCode = "204", description = "조회 결과가 존재하지 않습니다"),
            @ApiResponse(responseCode = "400", description = "요청값에 문제가 있습니다")
    })
    @GetMapping("")
    public ResponseEntity<List<ReservationResponse>> getReservations(
            @SessionAttribute(name = "sid", required = false) UserResponse loginUser
            )
    {
            List<ReservationResponse> result = reservationService.findByUserId(loginUser.getId());
            return ResponseEntity.ok().body(result);
    }

    @Operation(summary = "예약 등록", description = "예약을 등록합니다")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "201", description = "예약 등록에 성공했습니다"),
            @ApiResponse(responseCode = "400", description = "해당 방이 존재하지 않거나 해당 유저가 존재하지 않습니다"),
            @ApiResponse(responseCode = "409", description = "해당 방이 꽉 차있습니다")
    })
    @PostMapping("")
    public ResponseEntity<ReservationResponse> postReservation(
            @SessionAttribute(name = "sid", required = false) UserResponse loginUser,
            @RequestBody final ReservationRequest reservationRequest
            ){
            ReservationResponse result = reservationService.create(reservationRequest, loginUser.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @Operation(summary = "예약 취소", description = "예약을 취소합니다")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "204", description = "예약 취소에 성공했습니다"),
            @ApiResponse(responseCode = "400", description = "요청값에 문제가 발생했습니다. 다시 확인해주세요"),
            @ApiResponse(responseCode = "403", description = "예약에 해당하는 유저가 아닙니다")
    })
    @DeleteMapping("")
    public ResponseEntity<ReservationResponse> deleteReservation(
            @SessionAttribute(name = "sid", required = false) UserResponse loginUser,
            @RequestParam final Long reservationId
    ) {
        ReservationResponse result = reservationService.delete(loginUser.getId(), reservationId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(result);
    }
}
