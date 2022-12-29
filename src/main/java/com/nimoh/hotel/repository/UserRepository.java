package com.nimoh.hotel.repository;

import com.nimoh.hotel.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
