package com.nimoh.hotel.controller;

import com.nimoh.hotel.domain.Board;
import com.nimoh.hotel.dto.BoardDto;
import com.nimoh.hotel.service.board.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 게시판 컨트롤러
 * @author nimoh
 */
@RestController
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService){
        this.boardService = boardService;
    }

    /**
     * 게시판 목록 리턴
     * @return
     */
    @GetMapping("")
    public List<Board> getList() {
        return boardService.getList();
    }

    /**
     * 게시판 상세정보 리턴
     * @param boardIdx
     * @return
     */
    @GetMapping("/{boardIdx}")
    public Board get(@PathVariable int boardIdx) {
        return boardService.get(boardIdx);
    }

    /**
     * 게시글 등록
     * @param board
     */
    @PostMapping("")
    public void save(BoardDto boardDto) {
        boardService.save(boardDto);
    }

    /**
     * 게시글 수정
     * @param board
     */
    @PutMapping("")
    public void update(Board board) {
        boardService.update(board);
    }

    /**
     * 게시글 삭제
     * @param boardIdx
     */
    @DeleteMapping("/{boardIdx}")
    public void delete(@PathVariable int boardIdx) {
        boardService.delete(boardIdx);
    }
}
