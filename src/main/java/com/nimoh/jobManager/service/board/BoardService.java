package com.nimoh.jobManager.service.board;

import com.nimoh.jobManager.data.dto.board.BoardResponse;
import com.nimoh.jobManager.data.dto.board.BoardRequest;

import java.util.List;

public interface BoardService {

    List<BoardResponse> findAll();
    BoardResponse findById(Long boardIdx);
    BoardResponse save(BoardRequest boardRequest, Long userId);
    BoardResponse update(BoardRequest boardRequest, Long userId, Long boardId);

    boolean delete(Long boardId,Long userId);
}
