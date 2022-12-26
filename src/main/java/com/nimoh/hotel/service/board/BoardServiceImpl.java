package com.nimoh.hotel.service.board;

import com.nimoh.hotel.domain.Board;
import com.nimoh.hotel.dto.BoardDto;
import com.nimoh.hotel.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    /**
     * 게시판 목록 리턴
     * @return
     */
    @Override
    public List<Board> getList() {
        return boardRepository.getList();
    }

    /**
     * 게시판 상세정보 리턴
     * @param boardIdx
     * @return
     */
    @Override
    public Board get(int boardIdx) {
        return boardRepository.get(boardIdx);
    }

    /**
     * 게시글 등록
     * @param board
     */
    @Override
    public void save(BoardDto boardDto) {
        boardRepository.save(boardDto);
    }

    /**
     * 게시글 수정
     * @param board
     */
    @Override
    public void update(Board board) {
        boardRepository.update(board);
    }

    /**
     * 게시글 삭제
     * @param boardIdx
     */
    @Override
    public void delete(int boardIdx) {
        boardRepository.delete(boardIdx);
    }
}
