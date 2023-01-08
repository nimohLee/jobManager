package com.nimoh.hotel.repository;

import com.nimoh.hotel.data.entity.Board;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

//DataJpaTest에 Transactional 어노테이션이 DB 사용 후 자동 롤백해줌
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;

    @Test
    public void 게시글작성하기() {
        //given
        final Board board = board(2L);
        //when
        Board result = boardRepository.save(board);
        //then
        assertThat(result).isNotNull();
    }

    @Test
    public void 게시글모두조회() {
        //given
         final Board board = board(2L);
         final Board board2 = board(3L);

        boardRepository.save(board);
        boardRepository.save(board2);
        //when
        final List<Board> findBoardResult = boardRepository.findAll();

        //then
        assertThat(findBoardResult.size()).isEqualTo(2);
    }

    @Test
    public void 게시글하나조회성공() {
        //given
        final Board board = board(1L);

        boardRepository.save(board);
        System.out.println(board);
        //when
        final Optional<Board> findBoardResult = boardRepository.findById(1L);

        //then
        assertThat(findBoardResult.get().getId()).isEqualTo(1L);
    }
    @Test
    public void 유저아이디로게시글조회() {
        //given
        final Board board = board(2L);

        final Board board2 = board(3L);

        boardRepository.save(board);
        boardRepository.save(board2);

        //when
        final List<Board> findBoardResult = boardRepository.findAllByWriter(1L);

        //then
        assertThat(findBoardResult.size()).isEqualTo(2);
    }

    @Test
    public void 제목으로게시글조회() {
        //given
        final Board board = board(2L);

        final Board board2 = board(3L);

        boardRepository.save(board);
        boardRepository.save(board2);

        //when
        final List<Board> findBoardResult = boardRepository.findAllByTitle("test");

        //then
        assertThat(findBoardResult.size()).isEqualTo(2);
    }

    @Test
    public void 게시글수정하기() {
        //given
        final Board board = board(1L);

        final Board updateBoard = Board.builder()
                .id(1L)
                        .title("updated")
                                .content("hello")
                                        .category("free")
                                                .regDate(new Date())
                                                        .build();

        //when
        boardRepository.save(board);
        boardRepository.save(updateBoard);
        //then
        Optional<Board> resultBoard = boardRepository.findById(1L);
        assertThat(resultBoard.get().getTitle()).isEqualTo("updated");
    }

    @Test
    public void 게시글삭제하기() {
        //given
        final Board board =board(1L);
        boardRepository.save(board);
        //when
        boardRepository.deleteById(1L);
        //then
        Optional<Board> resultBoard = boardRepository.findById(1L);
        assertThat(resultBoard).isEmpty();
    }

    private Board board(Long boardId){
        return Board.builder()
                .id(boardId)
                .title("test")
                .content("hello")
                .category("free")
                .regDate(new Date())
                .writer(1L)
                .build();
    }
}
