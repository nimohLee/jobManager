package com.nimoh.jobManager.repository;

import com.nimoh.jobManager.data.entity.Job;
import com.nimoh.jobManager.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 직무 지원 레퍼지토리
 * @author nimoh
 * */

public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findAllByUser(User user);

    List<Job> findAllByCompanyNameContaining(String companyName);
}
