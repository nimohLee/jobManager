package com.nimoh.hotel.repository;

import com.nimoh.hotel.domain.Board;
import com.nimoh.hotel.dto.BoardDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 게시판 레퍼지토리
 * @author nimoh
 * */
@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
}
