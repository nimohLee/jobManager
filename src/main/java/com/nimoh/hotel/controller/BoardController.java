package com.nimoh.hotel.controller;

import com.nimoh.hotel.constants.Headers;
import com.nimoh.hotel.domain.Board;
import com.nimoh.hotel.dto.BoardDto;
import com.nimoh.hotel.dto.board.BoardDetailResponse;
import com.nimoh.hotel.dto.board.BoardRequest;
import com.nimoh.hotel.service.board.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static com.nimoh.hotel.constants.Headers.USER_ID_HEADER;

/**
 * 게시판 컨트롤러
 * @author nimoh
 */
@RestController
@RequestMapping("/api/v1/board")
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
    public ResponseEntity<List<BoardDetailResponse>> getList() {
        return ResponseEntity.status(HttpStatus.OK).body(boardService.findAll());
    }

    /**
     * 게시판 상세정보 리턴
     *
     * @param boardIdx
     * @return
     */
    @GetMapping("/{boardIdx}")
    public ResponseEntity<BoardDetailResponse> get(
            @PathVariable Long boardIdx
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                    .body(boardService.findById(boardIdx));
    }

    /**
     * 게시글 등록
     * @param boardDto
     */
    @PostMapping("")
    public ResponseEntity<BoardDetailResponse> save(
            @RequestHeader(USER_ID_HEADER) final String userId,
            @RequestBody final BoardRequest boardRequest

    ) {
            BoardDetailResponse result = boardService.save(boardRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    /**
     * 게시글 수정
     * @param board
     */
//    @PutMapping("")
//    public void update(Board board) {
//        boardService.update(board);
//    }

    /**
     * 게시글 삭제
     * @param boardIdx
     */
    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> delete(
            @RequestHeader(USER_ID_HEADER) final Long userId,
            @PathVariable Long boardId
    ) {
        boardService.delete(boardId,userId);
        return ResponseEntity.noContent().build();
    }
}
