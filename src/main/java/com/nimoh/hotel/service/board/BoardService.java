package com.nimoh.hotel.service.board;

import com.nimoh.hotel.domain.Board;
import com.nimoh.hotel.dto.board.BoardDetailResponse;
import com.nimoh.hotel.dto.board.BoardRequest;

import java.util.List;
import java.util.Optional;

public interface BoardService {

    List<BoardDetailResponse> findAll();
    BoardDetailResponse findById(Long boardIdx);
    BoardDetailResponse save(BoardRequest boardRequest);

    void delete(Long boardIdx);
}
