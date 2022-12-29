package com.nimoh.hotel.service.board;

import com.nimoh.hotel.domain.Board;
import com.nimoh.hotel.dto.BoardDto;
import com.nimoh.hotel.dto.board.BoardRequest;

import java.util.List;
import java.util.Optional;

public interface BoardService {

    List<Board> findAll();
    Optional<Board> findById(Long boardIdx);
    Board save(BoardDto boardDto);
}
