package com.nimoh.jobManager.repository;

import com.nimoh.jobManager.data.entity.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmenityRepository extends JpaRepository<Amenity,Long> {
}
