package com.nimoh.jobManager.repository;

import com.nimoh.jobManager.data.entity.Board;
import com.nimoh.jobManager.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 게시판 레퍼지토리
 * @author nimoh
 * */
@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    public List<Board> findAllByUser(User user);
    public List<Board> findAllByTitle(String title);
}
