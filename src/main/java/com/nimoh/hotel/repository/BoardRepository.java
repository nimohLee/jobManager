package com.nimoh.hotel.repository;

import com.nimoh.hotel.data.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 게시판 레퍼지토리
 * @author nimoh
 * */
@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    public List<Board> findAllByWriter(Long writer);
    public List<Board> findAllByTitle(String title);
}
