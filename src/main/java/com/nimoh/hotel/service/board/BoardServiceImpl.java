package com.nimoh.hotel.service.board;

import com.nimoh.hotel.domain.Board;
import com.nimoh.hotel.dto.BoardDto;
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
        Board board = new Board();
        board.setTitle(boardDto.getTitle());
        board.setContent(boardDto.getContent());
        board.setWriter(boardDto.getWriter());
        board.setCategory(boardDto.getCategory());
        board.setRegDate(new Date());
        try {
            boardRepository.save(board);
            return board;
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }


    }
}
