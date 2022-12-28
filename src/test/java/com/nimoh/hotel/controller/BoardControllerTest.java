package com.nimoh.hotel.controller;
import com.google.gson.Gson;
import com.nimoh.hotel.domain.Board;
import com.nimoh.hotel.dto.BoardDto;

import com.nimoh.hotel.service.board.BoardServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;
import java.util.Optional;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BoardController.class)
public class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BoardServiceImpl boardService;


    private final BoardDto boardDto = BoardDto.builder()
                        .title("title")
                        .writer("nimoh")
                        .content("hello")
                        .category("free")
                        .build();
    private Board board;
    @BeforeEach
    void before() {
        board = new Board();
        board.setTitle("title");
        board.setWriter("nimoh");
        board.setContent("hello");
        board.setCategory("free");
        board.setRegDate(new Date());
    }


    @Test
    @DisplayName("게시글 하나만 가져오기 성공")
    void getBoardList() throws Exception{
        //given

        given(boardService.findById(1L)).willReturn(Optional.ofNullable(board));

        //when
        Long boardIdx = 1L;
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/board/"+boardIdx))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title",notNullValue()))
                .andExpect(jsonPath("$.writer",notNullValue()))
                .andExpect(jsonPath("$.content",notNullValue()))
                .andExpect(jsonPath("$.category",notNullValue()));

        verify(boardService).findById(1L);
    }

    @Test
    @DisplayName("게시글 하나만 가져오기 실패")
    void getBoardListFail() throws Exception{

        //given
        Board board = new Board();
        board.setTitle("hello");
        board.setWriter("nimoh");
        board.setContent("content");
        board.setCategory("test");

        /* error 발생 가정하기 */
        given(boardService.findById(0L)).willThrow();

        //when
        Long boardIdx = 0L;
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/board/"+boardIdx))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Board 생성 성공 테스트")
    void createBoardSuccess() throws Exception {
        //given
        Mockito.when(boardService.save(boardDto)).thenReturn(board);

        Gson gson = new Gson();
        String content = gson.toJson(boardDto);
        //when
        //then
        mockMvc.perform(
                post("/board")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Board 생성 실패 테스트")
    void createBoardFail() throws Exception {
        //given
        given(boardService.save(boardDto)).willThrow();
        Gson gson = new Gson();
        String content = gson.toJson(boardDto);
        //when
        //then
        mockMvc.perform(
                        post("/board")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }
}
