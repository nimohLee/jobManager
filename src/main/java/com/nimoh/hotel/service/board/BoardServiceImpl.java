package com.nimoh.hotel.service.board;

import com.nimoh.hotel.domain.Board;
import com.nimoh.hotel.dto.BoardDto;
import com.nimoh.hotel.dto.board.BoardDetailResponse;
import com.nimoh.hotel.dto.board.BoardRequest;
import com.nimoh.hotel.errors.BoardErrorResult;
import com.nimoh.hotel.errors.BoardException;
import com.nimoh.hotel.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    @Override
    public Optional<Board> findById(Long boardIdx) {
        Optional<Board> result = boardRepository.findById(boardIdx);

        if (result.isEmpty()){
            throw new BoardException(BoardErrorResult.BOARD_NOT_FOUND);
        }
        return result;
    }

    @Override
    public BoardDetailResponse save(BoardRequest boardRequest) {
        if (boardRequest.getTitle()==null || boardRequest.getWriter()==null || boardRequest.getContent()==null){
            throw new BoardException(BoardErrorResult.REQUEST_VALUE_INVALID);
        }
        final Board board = Board.builder()
                .title(boardRequest.getTitle())
                .content(boardRequest.getContent())
                .writer(boardRequest.getWriter())
                .category(boardRequest.getCategory())
                .regDate(new Date())
                .build();

        final Board savedBoard = boardRepository.save(board);

    return BoardDetailResponse.builder()
            .title(savedBoard.getTitle())
            .content(savedBoard.getContent())
            .writer(savedBoard.getWriter())
            .category(savedBoard.getCategory())
            .build();
    }

    public void delete(Long boardId) {
        final Optional<Board> targetBoard = boardRepository.findById(boardId);

        if (targetBoard.isEmpty()){
            throw new BoardException(BoardErrorResult.BOARD_NOT_FOUND);
        }

        boardRepository.deleteById(boardId);
    }


}
