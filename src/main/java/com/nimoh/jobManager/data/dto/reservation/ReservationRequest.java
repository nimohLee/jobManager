package com.nimoh.jobManager.data.dto.reservation;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReservationRequest {

    private static final String datePattern = "yyyy-MM-dd";

    private Long roomId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate checkIn;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate checkOut;

}
