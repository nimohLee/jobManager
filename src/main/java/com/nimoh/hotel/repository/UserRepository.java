package com.nimoh.hotel.repository;

import com.nimoh.hotel.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface UserRepository extends JpaRepository<User,Long> {

    User getByUid(String uid);
}
