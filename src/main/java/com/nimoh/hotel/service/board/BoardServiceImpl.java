package com.nimoh.hotel.service.board;

import com.nimoh.hotel.domain.Board;
import com.nimoh.hotel.dto.board.BoardDetailResponse;
import com.nimoh.hotel.dto.board.BoardRequest;
import com.nimoh.hotel.errors.board.BoardErrorResult;
import com.nimoh.hotel.errors.board.BoardException;
import com.nimoh.hotel.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 게시판 서비스
 * @author nimoh
 * */
@Service
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;

    @Autowired
    public BoardServiceImpl(BoardRepository boardRepository){
        this.boardRepository = boardRepository;
    }


    @Override
    public List<BoardDetailResponse> findAll() {
        final List<Board> findResult = boardRepository.findAll();

        return findResult.stream().map(v -> BoardDetailResponse.builder()
                        .id(v.getId())
                        .title(v.getTitle())
                        .writer(v.getWriter())
                        .content(v.getContent())
                        .regDate(v.getRegDate())
                        .category(v.getCategory())
                        .build())
                .collect(Collectors.toList());

    }

    @Override
    public BoardDetailResponse findById(Long boardIdx) {
        Optional<Board> result = boardRepository.findById(boardIdx);

        if (result.isEmpty()){
            throw new BoardException(BoardErrorResult.BOARD_NOT_FOUND);
        }
        return BoardDetailResponse.builder()
                .id(result.get().getId())
                .title(result.get().getTitle())
                .content(result.get().getContent())
                .writer(result.get().getWriter())
                .category(result.get().getCategory())
                .regDate(result.get().getRegDate())
                .build();
    }

    @Override
    public BoardDetailResponse save(BoardRequest boardRequest,Long userId) {
        if (boardRequest.getTitle()==null || boardRequest.getWriter()==null || boardRequest.getContent()==null){
            throw new BoardException(BoardErrorResult.REQUEST_VALUE_INVALID);
        }
        final Board board = Board.builder()
                .title(boardRequest.getTitle())
                .content(boardRequest.getContent())
                .writer(userId)
                .category(boardRequest.getCategory())
                .regDate(new Date())
                .build();

        final Board savedBoard = boardRepository.save(board);

    return BoardDetailResponse.builder()
            .id(savedBoard.getId())
            .title(savedBoard.getTitle())
            .content(savedBoard.getContent())
            .writer(savedBoard.getWriter())
            .category(savedBoard.getCategory())
            .build();
    }

    public BoardDetailResponse update(BoardRequest boardRequest,Long userId,Long boardId){
        final Optional<Board> targetBoard = boardRepository.findById(boardId);
        if(targetBoard.isEmpty()){
            throw new BoardException(BoardErrorResult.BOARD_NOT_FOUND);
        }
        if(!targetBoard.get().getWriter().equals(userId)){
            throw new BoardException(BoardErrorResult.NO_PERMISSION);
        }

        Board board = Board.builder()
                        .id(boardId)
                                .writer(userId)
                .title(boardRequest.getTitle())
                                        .content(boardRequest.getContent())
                                                .build();
        Board result = boardRepository.save(board);

        return BoardDetailResponse.builder()
                .id(result.getId())
                .title(result.getTitle())
                .content(result.getContent())
                .writer(result.getWriter())
                .category(result.getCategory())
                .build();

    }

    public boolean delete(Long boardId,Long userId) {
        final Optional<Board> targetBoard = boardRepository.findById(boardId);

        if (targetBoard.isEmpty()){
            throw new BoardException(BoardErrorResult.BOARD_NOT_FOUND);
        }
        if(!targetBoard.get().getWriter().equals(userId)){
            throw new BoardException(BoardErrorResult.NO_PERMISSION);
        }
        boardRepository.deleteById(boardId);
        return true;
    }


}
