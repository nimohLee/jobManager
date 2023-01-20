package com.nimoh.hotel.service;

import com.nimoh.hotel.data.entity.Board;
import com.nimoh.hotel.data.dto.board.BoardResponse;
import com.nimoh.hotel.data.dto.board.BoardRequest;
import com.nimoh.hotel.commons.board.BoardErrorResult;
import com.nimoh.hotel.commons.board.BoardException;
import com.nimoh.hotel.data.entity.User;
import com.nimoh.hotel.repository.BoardRepository;
import com.nimoh.hotel.repository.UserRepository;
import com.nimoh.hotel.service.board.BoardServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
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

    @Mock
    private UserRepository userRepository;

    @Test
    public void 요청에null값이있어서추가실패() {
        //given
        BoardRequest boardRequest = BoardRequest.builder().build();
        Long userId = 1L;
        //when
        final BoardException result = assertThrows(BoardException.class, ()->boardService.save(boardRequest,userId));

        //then
        assertThat(result.getErrorResult()).isEqualTo(BoardErrorResult.REQUEST_VALUE_INVALID);
    }

    @Test
    public void 게시글추가성공() {
        //given
        Long userId = 1L;
        doReturn(Optional.of(user(1L))).when(userRepository).findById(any());
        doReturn(board(2L)).when(boardRepository).save(ArgumentMatchers.any(Board.class));
        //when
        BoardResponse result = boardService.save(boardRequest(),userId);
        //then
        assertThat(result.getTitle()).isEqualTo("test");
    }

    @Test
    public void 해당게시글이존재하지않는경우삭제실패(){
        //given
        final Long boardId = -1L;
        final Long userId = 1L;
        //when
        final BoardException result = assertThrows(BoardException.class,()->boardService.delete(boardId,userId));
        //then
        assertThat(result.getErrorResult()).isEqualTo(BoardErrorResult.BOARD_NOT_FOUND);
    }

    @Test
    public void 게시글삭제실패_해당게시글ID와현재유저ID가다름() {
        //given
        final Long boardId = 1L;
        final Long userId = 2L;
        doReturn(Optional.of(user(2L))).when(userRepository).findById(any());
        doReturn(Optional.of(board(1L))).when(boardRepository).findById(boardId);
        //when
        final BoardException result = assertThrows(BoardException.class, () -> boardService.delete(boardId,userId));
        //then
        assertThat(result.getErrorResult()).isEqualTo(BoardErrorResult.NO_PERMISSION);
    }

    @Test
    public void 게시글삭제성공() {
        //given
        final Long boardId = 2L;
        final User user = user(1L);
        final Long userId = 1L;
        final Board board = board(2L);
        doReturn(Optional.of(user)).when(userRepository).findById(any());
        doReturn(Optional.of(board)).when(boardRepository).findById(boardId);

        //when
        boardService.delete(boardId,userId);
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
        doReturn(Optional.of(board(1L))).when(boardRepository).findById(boardIdx);

        //when
        BoardResponse result = boardService.findById(boardIdx);
        //then
        assertThat(result).isNotNull();
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
        List<BoardResponse> result = boardService.findAll();
        //then
        assertThat(result.size()).isEqualTo(2);    }

    public BoardRequest boardRequest(){
        return BoardRequest.builder()
                .title("test").content("hello").writer(1L).category("free").build();
    }

    public Board board(Long boardId) {
        return Board.builder()
                .id(boardId)
                .title("test")
                .content("hello")
                .user(user(1L))
                .category("free")
                .regDate(LocalDateTime.now())
                .build();
    }
    public User user(Long userId){
        return User.builder()
                .id(userId)
                .uid("nimoh")
                .name("nimoh")
                .password("12345678")
                .email("test@test.com")
                .build();
    }
}
