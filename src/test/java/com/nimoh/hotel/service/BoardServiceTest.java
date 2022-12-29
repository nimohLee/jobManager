package com.nimoh.hotel.service;

import com.nimoh.hotel.domain.Board;
import com.nimoh.hotel.dto.board.BoardDetailResponse;
import com.nimoh.hotel.dto.board.BoardRequest;
import com.nimoh.hotel.errors.BoardErrorResult;
import com.nimoh.hotel.errors.BoardException;
import com.nimoh.hotel.repository.BoardRepository;
import com.nimoh.hotel.service.board.BoardServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    @Test
    public void 해당게시글이존재하지않는경우삭제실패(){
        //given
        final Long boardId = -1L;
        //when
        final BoardException result = assertThrows(BoardException.class,()->boardService.delete(boardId));
        //then
        assertThat(result.getErrorResult()).isEqualTo(BoardErrorResult.BOARD_NOT_FOUND);
    }
    @Test
    public void 게시글삭제성공() {
        //given
        final Long boardId = 1L;
        final Board board = board();
        doReturn(Optional.of(board)).when(boardRepository).findById(boardId);

        //when
        boardService.delete(1L);
        //then
    }

    @Test
    public void 해당게시글이없어서조회실패(){
        //given
        final Long boardIdx = -1L;
        //when
        BoardException result = assertThrows(BoardException.class,()->boardService.findById(boardIdx));
        //then
        assertThat(result.getErrorResult()).isEqualTo(BoardErrorResult.BOARD_NOT_FOUND);
    }

    @Test
    public void 게시글하나조회성공(){
        //given
        final Long boardIdx = 1L;
        doReturn(Optional.of(board())).when(boardRepository).findById(boardIdx);

        //when
        Optional<Board> result = boardService.findById(boardIdx);
        //then
        assertThat(result).isNotEmpty();
    }

    @Test
    public void 게시글전체조회성공(){
        //given
        List<Board> boards = new ArrayList<>();
        Board board = Board.builder().build();
        Board board1 = Board.builder().build();
        boards.add(board);
        boards.add(board1);
        doReturn(boards).when(boardRepository).findAll();
        //when
        List<Board> result = boardService.findAll();
        //then
        assertThat(result.size()).isEqualTo(2);    }

    public BoardRequest boardRequest(){
        return BoardRequest.builder()
                .title("test").content("hello").writer("nimoh").category("free").build();
    }

    public Board board() {
        return Board.builder()
                .id(1L)
                .title("test")
                .content("hello")
                .writer("nimoh")
                .category("free")
                .regDate(new Date())
                .build();
    }
}
