package com.nimoh.hotel.service.board;

import com.nimoh.hotel.data.entity.Board;
import com.nimoh.hotel.data.dto.board.BoardResponse;
import com.nimoh.hotel.data.dto.board.BoardRequest;
import com.nimoh.hotel.commons.board.BoardErrorResult;
import com.nimoh.hotel.commons.board.BoardException;
import com.nimoh.hotel.data.entity.User;
import com.nimoh.hotel.repository.BoardRepository;
import com.nimoh.hotel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 게시판 서비스 구현체
 * @author nimoh
 * */
@Service
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Autowired
    public BoardServiceImpl(BoardRepository boardRepository,
                            UserRepository userRepository){
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
    }


    @Override
    public List<BoardResponse> findAll() {
        final List<Board> findResult = boardRepository.findAll();

        return findResult.stream().map(v -> BoardResponse.builder()
                        .id(v.getId())
                        .title(v.getTitle())
                        .user(v.getUser())
                        .content(v.getContent())
                        .category(v.getCategory())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public BoardResponse findById(Long boardIdx) {
        Optional<Board> result = boardRepository.findById(boardIdx);

        if (result.isEmpty()){
            throw new BoardException(BoardErrorResult.BOARD_NOT_FOUND);
        }
        return BoardResponse.builder()
                .id(result.get().getId())
                .title(result.get().getTitle())
                .content(result.get().getContent())
                .user(result.get().getUser())
                .category(result.get().getCategory())
                .build();
    }

    @Override
    public BoardResponse save(BoardRequest boardRequest, Long userId) {
        if (boardRequest.getTitle()==null || boardRequest.getWriter()==null || boardRequest.getContent()==null){
            throw new BoardException(BoardErrorResult.REQUEST_VALUE_INVALID);
        }
        final Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()){
            throw new BoardException(BoardErrorResult.REQUEST_VALUE_INVALID);
        }
        final Board board = Board.builder()
                .title(boardRequest.getTitle())
                .content(boardRequest.getContent())
                .user(user.get())
                .category(boardRequest.getCategory())
                .build();

        final Board savedBoard = boardRepository.save(board);

    return BoardResponse.builder()
            .id(savedBoard.getId())
            .title(savedBoard.getTitle())
            .content(savedBoard.getContent())
            .user(savedBoard.getUser())
            .category(savedBoard.getCategory())
            .build();
    }

    public BoardResponse update(BoardRequest boardRequest, Long userId, Long boardId){
        final Optional<Board> targetBoard = boardRepository.findById(boardId);
        if(targetBoard.isEmpty()){
            throw new BoardException(BoardErrorResult.BOARD_NOT_FOUND);
        }
        Optional<User> user = userRepository.findById(userId);
        if(!targetBoard.get().getUser().equals(user.get())){
            throw new BoardException(BoardErrorResult.NO_PERMISSION);
        }

        Board board = Board.builder()
                        .id(boardId)
                                .user(user.get())
                .title(boardRequest.getTitle())
                                        .content(boardRequest.getContent())
                                                .build();
        Board result = boardRepository.save(board);

        return BoardResponse.builder()
                .id(result.getId())
                .title(result.getTitle())
                .content(result.getContent())
                .user(result.getUser())
                .category(result.getCategory())
                .build();

    }

    public boolean delete(Long boardId,Long userId) {
        final Optional<Board> targetBoard = boardRepository.findById(boardId);
        final Optional<User> user = userRepository.findById(userId);
        if (targetBoard.isEmpty()){
            throw new BoardException(BoardErrorResult.BOARD_NOT_FOUND);
        }
        if (user.isEmpty()){
            throw new BoardException(BoardErrorResult.REQUEST_VALUE_INVALID);
        }
        if(!targetBoard.get().getUser().equals(user.get())){
            throw new BoardException(BoardErrorResult.NO_PERMISSION);
        }
        boardRepository.deleteById(boardId);
        return true;
    }


}
