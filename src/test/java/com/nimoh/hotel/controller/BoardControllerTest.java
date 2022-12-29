package com.nimoh.hotel.controller;
import com.google.gson.Gson;
import com.nimoh.hotel.domain.Board;
import com.nimoh.hotel.dto.BoardDto;

import com.nimoh.hotel.dto.board.BoardDetailResponse;
import com.nimoh.hotel.dto.board.BoardRequest;
import com.nimoh.hotel.errors.BoardErrorResult;
import com.nimoh.hotel.errors.BoardException;
import com.nimoh.hotel.errors.GlobalExceptionHandler;
import com.nimoh.hotel.service.board.BoardServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class BoardControllerTest {

    private MockMvc mockMvc;
    private Gson gson;

    @InjectMocks
    private BoardController boardController;
    @Mock
    private BoardServiceImpl boardService;

    private final BoardDto boardDto = BoardDto.builder()
                        .title("title")
                        .writer("nimoh")
                        .content("hello")
                        .category("free")
                        .build();
    private Board board;
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
