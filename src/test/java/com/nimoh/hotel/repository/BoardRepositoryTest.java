package com.nimoh.hotel.repository;

import com.nimoh.hotel.domain.Board;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;

    @Test
    public void 게시글모두조회() {
        //given
         final Board board = Board.builder()
                 .id(1L)
                 .title("test")
                 .content("12345")
                 .writer("nimoh")
                 .category("free")
                 .regDate(new Date())
                 .build();
         final Board board2 = Board.builder()
                         .id(2L).title("test2").content("12345").writer("nimoh").category("free").regDate(new Date()).build();

        boardRepository.save(board);
        boardRepository.save(board2);
        //when
        final List<Board> findBoardResult = boardRepository.findAll();

        //then
        assertThat(findBoardResult.size()).isEqualTo(2);
    }

}
