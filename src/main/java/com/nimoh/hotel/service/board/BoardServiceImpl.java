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
        Optional<Board> result;
        try{
             result = boardRepository.findById(boardIdx);
             return result;
        }catch (Exception e){
            throw new Error("findById is failed");
        }
    }

    @Override
    public BoardDetailResponse save(BoardRequest boardRequest) {
        if(boardRequest.getTitle()==null || boardRequest.getWriter()==null || boardRequest.getContent()==null){
            throw new BoardException(BoardErrorResult.REQUEST_VALUE_INVALID);
        }

    return null;
    }
}
