package com.nimoh.hotel.controller;
import com.google.gson.Gson;

import com.nimoh.hotel.dto.board.BoardDetailResponse;
import com.nimoh.hotel.dto.board.BoardRequest;
import com.nimoh.hotel.errors.BoardErrorResult;
import com.nimoh.hotel.errors.BoardException;
import com.nimoh.hotel.errors.GlobalExceptionHandler;
import com.nimoh.hotel.service.board.BoardServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import static com.nimoh.hotel.constants.Headers.USER_ID_HEADER;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;
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
        BoardRequest boardRequest = boardRequest("test",1L,"hello","free");
        given(boardService.save(any(),1L)).willReturn(boardDetailResponse());
        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .header(USER_ID_HEADER, "1234")
                        .content(gson.toJson(boardRequest))
                        .contentType(MediaType.APPLICATION_JSON)
        );
        //then
        resultActions.andExpect(status().isCreated());

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

    @Test
    public void 게시글삭제실패_삭제권한없음() throws Exception{
        //given
        final String url = "/api/v1/board/1";
        final Long boardId = 1L;
        final Long userId = 1L;
        lenient().doThrow(new BoardException(BoardErrorResult.NO_PERMISSION))
                .when(boardService)
                .delete(boardId,userId);
        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.delete(url)
                        .header(USER_ID_HEADER, 1L)
        );
        //then
        resultActions.andExpect(status().isForbidden());
    }

    @Test
    public void 게시글삭제성공() throws Exception {
        //given
        String url = "/api/v1/board/1";
        doReturn(true).when(boardService).delete(1L,1L);
        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.delete(url)
                        .header(USER_ID_HEADER,1L)

        );
        //then
        resultActions.andExpect(status().isNoContent());
    }

    @Test
    public void 게시글수정실패_유저헤더없음() throws Exception {
        //given
        final String url = "/api/v1/board/1";

        //when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.put(url)
        );
        //then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    public void 게시글수정실패_수정권한없음() throws Exception {
        //given
        final String url = "/api/v1/board/1";
        final Long userId = 1L;
        lenient().doThrow(new BoardException(BoardErrorResult.NO_PERMISSION))
                .when(boardService)
                .save(boardRequest("test",1L,"hello","free"),userId);
        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.put(url)
                        .header(USER_ID_HEADER, 1L)
        );
        //then
        resultActions.andExpect(status().isForbidden());
    }

    @Test
    public void 게시글수정성공() throws Exception{
        //given
        final String url = "/api/v1/board/1";
        final Long userId = 1L;
        //when
        doReturn(boardDetailResponse()).when(boardService).save(any(BoardRequest.class),userId);
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.put(url)
                        .header(USER_ID_HEADER,userId)
        );
        //then
        resultActions.andExpect(status().isCreated());
    }

    private BoardDetailResponse boardDetailResponse() throws ParseException {

        return BoardDetailResponse.builder()
                .id(1L)
                .title("test")
                .writer(1L)
                .content("hello")
                .category("free")
                .regDate(new Date())
                .build();
    }

    private BoardRequest boardRequest(final String title, final Long writer, final String content, final String category) {
        return BoardRequest.builder()
                .title(title)
                .content(content)
                .writer(writer)
                .category(category)
                .build();
    }
}
