package com.nimoh.hotel.service.board;

import com.nimoh.hotel.domain.Board;
import com.nimoh.hotel.dto.BoardDto;
import com.nimoh.hotel.repository.BoardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

public interface BoardService {

    public List<Board> getList();

    public Board get(int boardIdx);

    public void save(BoardDto boardDto);

    public void update(Board board);

    public void delete(int boardIdx);



}
