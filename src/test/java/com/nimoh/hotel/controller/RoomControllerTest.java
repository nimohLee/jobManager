package com.nimoh.hotel.controller;

import com.google.gson.Gson;
import com.nimoh.hotel.dto.room.RoomDetailResponse;
import com.nimoh.hotel.errors.GlobalExceptionHandler;
import com.nimoh.hotel.errors.room.RoomErrorResult;
import com.nimoh.hotel.errors.room.RoomException;
import com.nimoh.hotel.service.room.RoomServiceImpl;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class RoomControllerTest {
    private MockMvc mockMvc;
    private Gson gson;

    @InjectMocks
    private RoomController roomController;
    @Mock
    private RoomServiceImpl roomService;

    @BeforeEach
    public void init(){
        gson = new Gson();
        mockMvc = MockMvcBuilders.standaloneSetup(roomController).setControllerAdvice(new GlobalExceptionHandler()).build();
    }

    @Test
    public void 방id로조회실패_방id없음() throws Exception {
        //given
        final String url = "/api/v1/room/1";
        doThrow(new RoomException(RoomErrorResult.ROOM_NOT_FOUND)).when(roomService).findById(1L);

        //when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
        );

        //then
        resultActions.andExpect(status().isNoContent());
    }

    @Test
    public void 방id로조회실패_service에서런타임에러throw() throws Exception {
        //given
        final String url = "/api/v1/room/1";
        doThrow(new RuntimeException()).when(roomService).findById(1L);

        //when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
        );

        //then
        resultActions.andExpect(status().isInternalServerError());
    }

    @Test
    public void 방id로조회성공() throws Exception {
        //given
        final String url = "/api/v1/room/1";
        doReturn(RoomDetailResponse.builder().build()).when(roomService).findById(1L);
        //when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
        );
        //then
        resultActions.andExpect(status().isOk());
    }

    @Test
    public void 방조회실패_방이름없음() throws Exception {
        //given
        final String url = "/api/v1/room";
        final String roomName = "none";
        final MultiValueMap<String,String> linkedMap = new LinkedMultiValueMap<>();
        linkedMap.add("search","name");
        linkedMap.add("value","none");
        doThrow(new RoomException(RoomErrorResult.ROOM_NOT_FOUND)).when(roomService).findByName(roomName);
        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .params(linkedMap)
        );
        //then
        resultActions.andExpect(status().isNoContent());
    }
    @Test
    public void 방조회실패_service에서런타임에러throw() throws Exception {
        //given
        final String url = "/api/v1/room";
        final String roomName = "none";
        final MultiValueMap<String,String> linkedMap = new LinkedMultiValueMap<>();
        linkedMap.add("search","name");
        linkedMap.add("value","none");
        doThrow(new RuntimeException()).when(roomService).findByName(roomName);
        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .params(linkedMap)
        );
        //then
        resultActions.andExpect(status().isInternalServerError());
    }

}
