package com.nimoh.hotel.repository;

import com.nimoh.hotel.domain.Board;
import com.nimoh.hotel.dto.BoardDto;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 게시판 레퍼지토리
 * @author nimoh
 * */
@Repository
public interface BoardRepository {

    List<Board> getList();

    Board get(int boardIdx);

    void save(BoardDto boardDto);

    void delete(int boardIdx);

    void update(Board board);

}
