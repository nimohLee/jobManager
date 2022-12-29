package com.nimoh.hotel.service.board;

import com.nimoh.hotel.domain.Board;
import com.nimoh.hotel.dto.BoardDto;
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
    public Board save(BoardDto boardDto) {
        final Board board = Board.builder()
                .title(boardDto.getTitle())
                .writer(boardDto.getWriter())
                .content(boardDto.getContent())
                .category(boardDto.getCategory())
                .regDate(new Date())
                .build();

        try {
            boardRepository.save(board);
            return board;
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
}
