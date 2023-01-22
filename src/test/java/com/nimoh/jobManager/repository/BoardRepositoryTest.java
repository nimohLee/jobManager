package com.nimoh.jobManager.repository;

import com.nimoh.jobManager.data.entity.Board;
import com.nimoh.jobManager.data.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

//DataJpaTest에 Transactional 어노테이션이 DB 사용 후 자동 롤백해줌
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    UserRepository userRepository;

    private User user;

    @BeforeEach
    public void init(){
        // 연관관계를 위해 유저 미리 하나 생성
        user = saveUser(1L);
    }

    private User saveUser(Long userId){
        User user = User.builder()
                .id(userId)
                .uid("nimoh123")
                .name("nimoh")
                .email("test@test.com")
                .password("12345678")
                .build();
        userRepository.save(user);
        return user;
    }

    @Test
    public void 게시글작성하기() {
        //given
        final Board board = board(2L,user);
        //when
        Board result = boardRepository.save(board);
        //then
        assertThat(result).isNotNull();
    }

    @Test
    public void 게시글모두조회() {
        //given
         final Board board = board(2L,user);
         final Board board2 = board(3L,user);

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
        final Board board = board(1L,user);
        boardRepository.save(board);
        //when
        final Optional<Board> findBoardResult = boardRepository.findById(1L);

        //then
        assertThat(findBoardResult.get().getId()).isEqualTo(1L);
    }
    @Test
    public void 유저아이디로게시글조회() {
        //given
        final Board board = board(2L,user);
        final Board board2 = board(3L, user);

        boardRepository.save(board);
        boardRepository.save(board2);

        //when
        final List<Board> findBoardResult = boardRepository.findAllByUser(User.builder().id(1L).build());

        //then
        assertThat(findBoardResult.size()).isEqualTo(2);
    }

    @Test
    public void 제목으로게시글조회() {
        //given
        final Board board = board(2L, user);

        final Board board2 = board(3L, user);

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
        final Board board = board(1L, user);
        final Board updateBoard = Board.builder().id(1L).title("updated").build();

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
        final Board board =board(1L, user);
        boardRepository.save(board);
        //when
        boardRepository.deleteById(1L);
        //then
        Optional<Board> resultBoard = boardRepository.findById(1L);
        assertThat(resultBoard).isEmpty();
    }

    private Board board(Long boardId, User user){
        return Board.builder()
                .id(boardId)
                .title("test")
                .content("hello")
                .category("free")
                .user(user)
                .build();
    }
}
