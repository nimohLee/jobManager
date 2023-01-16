package com.nimoh.hotel.repository;

import com.nimoh.hotel.data.entity.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmenityRepository extends JpaRepository<Amenity,Long> {
}
