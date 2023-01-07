package com.nimoh.hotel.controller;

import com.nimoh.hotel.dto.board.BoardResponse;
import com.nimoh.hotel.dto.board.BoardRequest;
import com.nimoh.hotel.service.board.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger LOGGER = LoggerFactory.getLogger(BoardController.class);

    @Autowired
    public BoardController(BoardService boardService){
        this.boardService = boardService;
    }

    /**
     * 게시판 목록 리턴
     * @return
     */
    @Operation(summary = "게시글 목록 조회", description = "게시글 목록을 모두 조회합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 목록 조회 성공"),
            @ApiResponse(responseCode = "204", description = "게시글이 없습니다"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다")
    })
    @GetMapping("")
    public ResponseEntity<List<BoardResponse>> getList() {
        LOGGER.info("getList 메서드가 호출되었습니다");
        return ResponseEntity.status(HttpStatus.OK).body(boardService.findAll());
    }

    /**
     * 게시판 상세정보 리턴
     *
     * @param boardIdx
     * @return
     */
    @Operation(summary = "게시글 하나 조회",description = "게시글 id로 게시글 하나 조회합니다")
    @GetMapping("/{boardIdx}")
    public ResponseEntity<BoardResponse> get(
            @PathVariable Long boardIdx
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                    .body(boardService.findById(boardIdx));
    }

    /**
     * 게시글 등록
     * @param boardDto
     */
    @Operation(summary = "게시글 등록",description = "게시글을 작성합니다.")
    @PostMapping("")
    public ResponseEntity<BoardResponse> save(
            @RequestHeader(USER_ID_HEADER) final Long userId,
            @RequestBody final BoardRequest boardRequest

    ) {
            BoardResponse result = boardService.save(boardRequest,userId);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    /**
     * 게시글 수정
     * @param board
     */
    @Operation(summary = "게시글 수정",description = "게시글 id로 게시글을 수정합니다")
    @PutMapping("/{boardId}")
    public ResponseEntity<BoardResponse> update(
            @RequestHeader(USER_ID_HEADER) Long userId,
            @PathVariable Long boardId,
            @RequestBody BoardRequest boardRequest
    ) {
        BoardResponse result = boardService.update(boardRequest,userId,boardId);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    /**
     * 게시글 삭제
     * @param boardIdx
     */
    @Operation(summary = "게시글 삭제",description = "게시글 id로 게시글을 삭제합니다")
    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> delete(
            @RequestHeader(USER_ID_HEADER) final Long userId,
            @PathVariable Long boardId
    ) {
        boardService.delete(boardId,userId);
        return ResponseEntity.noContent().build();
    }
}
