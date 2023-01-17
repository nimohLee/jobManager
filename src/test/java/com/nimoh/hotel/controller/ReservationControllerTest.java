package com.nimoh.hotel.controller;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.google.gson.Gson;
import com.nimoh.hotel.commons.GlobalExceptionHandler;
import com.nimoh.hotel.commons.reservation.ReservationErrorResult;
import com.nimoh.hotel.commons.reservation.ReservationException;
import com.nimoh.hotel.data.dto.reservation.ReservationRequest;
import com.nimoh.hotel.data.entity.Reservation;
import com.nimoh.hotel.service.reservation.ReservationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;

import static com.nimoh.hotel.constants.Headers.USER_ID_HEADER;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ReservationControllerTest {

    private MockMvc mockMvc;
    private Gson gson;

    @InjectMocks
    private ReservationController reservationController;

    @Mock
    private ReservationServiceImpl reservationService;

    @BeforeEach
    public void init() {
        gson = new Gson();
        mockMvc = MockMvcBuilders.standaloneSetup(reservationController).setControllerAdvice(new GlobalExceptionHandler()).build();
    }

    @Test
    public void 예약조회실패_유저헤더없음() throws Exception {
        //given
        final String url = "/api/v1/reservation";
        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
        );
        //then
        resultActions.andExpect(status().isBadRequest());
    }
    @Test
    public void 예약조회실패_예약내역없음() throws Exception {
        //given
        final String url = "/api/v1/reservation";
        doThrow(new ReservationException(ReservationErrorResult.RESERVATION_NOT_FOUND)).when(reservationService).findByUserId(any());
        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .header(USER_ID_HEADER,"1234")
        );
        //then
        resultActions.andExpect(status().isNoContent());
    }

    @Test
    public void 예약조회성공() throws Exception {
        //given
        final String url = "/api/v1/reservation";
        doReturn(Arrays.asList(Reservation.builder().build())).when(reservationService).findByUserId(any());
        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .header(USER_ID_HEADER,"1234")
        );
        //then
        resultActions.andExpect(status().isOk());
    }

    @Test
    public void 예약등록실패_유저헤더없음() throws Exception{
        //given
        final String url = "/api/v1/reservation";
        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
        );
        //then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    public void 예약등록실패_요청값없음() throws Exception{
        //given
        final String url = "/api/v1/reservation";
        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .header(USER_ID_HEADER, "1234")
        );
        //then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    public void 예약등록성공() throws Exception{
        //given
        final String url = "/api/v1/reservation";
        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .header(USER_ID_HEADER, "1234")
                        .content("{\"roomId\":\"1\",\"checkIn\":\"2022-11-13\",\"checkOut\":\"2022-12-30\"}")
                        .contentType(MediaType.APPLICATION_JSON)
        );
        //then
        resultActions.andExpect(status().isCreated());
    }


}
