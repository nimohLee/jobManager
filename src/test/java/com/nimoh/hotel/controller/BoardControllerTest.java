package com.nimoh.hotel.controller;
import com.google.gson.Gson;

import com.nimoh.hotel.dto.board.BoardDetailResponse;
import com.nimoh.hotel.errors.BoardErrorResult;
import com.nimoh.hotel.errors.BoardException;
import com.nimoh.hotel.errors.GlobalExceptionHandler;
import com.nimoh.hotel.service.board.BoardServiceImpl;
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

import java.util.Arrays;
import java.util.Date;

import static com.nimoh.hotel.constants.Headers.USER_ID_HEADER;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class BoardControllerTest {

    private MockMvc mockMvc;
    private Gson gson;

    @InjectMocks
    private BoardController boardController;
    @Mock
    private BoardServiceImpl boardService;

    @BeforeEach
    public void init() {
        gson = new Gson();
        mockMvc = MockMvcBuilders.standaloneSetup(boardController).setControllerAdvice(new GlobalExceptionHandler()).build();
    }

    @Test
    public void 게시글하나조회실패_해당게시글없음() throws Exception {
        //given
        final String url = "/api/v1/board/-1";

        doThrow(new BoardException(BoardErrorResult.BOARD_NOT_FOUND)).when(boardService).findById(-1L);
        //when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .header(USER_ID_HEADER,"123")
        );
        //then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    public void 게시글하나조회성공() throws Exception{
        //given
        final String url = "/api/v1/board/1";
        doReturn(boardDetailResponse()).when(boardService).findById(1L);
        //when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
        );
        //then
        resultActions.andExpect(status().isOk());
    }

    @Test
    public void 게시글모두조회성공() throws Exception {
        //given
        final String url = "/api/v1/board";
        doReturn(Arrays.asList(
                BoardDetailResponse.builder().build(),
                BoardDetailResponse.builder().build(),
                BoardDetailResponse.builder().build()
        )).when(boardService).findAll();
        //when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
        );
        //then
        resultActions.andExpect(status().isOk());
    }

    @Test
    public void 게시글등록실패_유저헤더없음() throws Exception {
        //given
        final String url = "/api/v1/board/";

        //when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
        );
        //then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    public void 게시글등록성공() throws Exception{
        //given
        final String url = "/api/v1/board";
        //when
        //then
    }

    @Test
    public void 게시글삭제실패_유저헤더없음() throws Exception{
        //given
        final String url = "/api/v1/board/1";

        //when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.delete(url)
        );
        //then
        resultActions.andExpect(status().isBadRequest());
    }


    private BoardDetailResponse boardDetailResponse(){
        return BoardDetailResponse.builder()
                .id(1L)
                .title("test")
                .writer("nimoh")
                .content("hello")
                .category("free")
                .regDate(new Date())
                .build();
    }
}
