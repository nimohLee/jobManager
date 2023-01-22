package com.nimoh.jobManager.repository;

import com.nimoh.jobManager.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUid(String uid);
}
