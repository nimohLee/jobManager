package com.nimoh.hotel.service;

import com.nimoh.hotel.domain.Board;
import com.nimoh.hotel.dto.board.BoardDetailResponse;
import com.nimoh.hotel.dto.board.BoardRequest;
import com.nimoh.hotel.errors.BoardErrorResult;
import com.nimoh.hotel.errors.BoardException;
import com.nimoh.hotel.repository.BoardRepository;
import com.nimoh.hotel.service.board.BoardService;
import com.nimoh.hotel.service.board.BoardServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BoardServiceTest {

    @InjectMocks
    private BoardServiceImpl boardService;

    @Mock
    private BoardRepository boardRepository;

    @Test
    public void 요청에null값이있어서추가실패() {
        //given
        BoardRequest boardRequest = BoardRequest.builder()
                .content("hello").writer("nimoh").category("free").build();
        //when
        final BoardException result = assertThrows(BoardException.class, ()->boardService.save(boardRequest));

        //then
        assertThat(result.getErrorResult()).isEqualTo(BoardErrorResult.REQUEST_VALUE_INVALID);
    }

    @Test
    public void 게시글추가성공() {
        //given

        doReturn(board()).when(boardRepository).save(ArgumentMatchers.any(Board.class));
        //when
        BoardDetailResponse result = boardService.save(boardRequest());
        //then
        assertThat(result.getTitle()).isEqualTo("test");
    }

    public BoardRequest boardRequest(){
        return BoardRequest.builder()
                .title("test").content("hello").writer("nimoh").category("free").build();
    }

    public Board board() {
        return Board.builder()
                .title("test")
                .content("hello")
                .writer("nimoh")
                .category("free")
                .regDate(new Date())
                .build();
    }
}
