package com.nimoh.hotel.controller;

import com.google.gson.Gson;
import com.nimoh.hotel.commons.GlobalExceptionHandler;
import com.nimoh.hotel.service.reservation.ReservationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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

}
