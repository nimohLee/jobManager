package com.nimoh.hotel.repository;


import com.nimoh.hotel.data.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    public List<Room> findByNameContainingIgnoreCase(String roomName);

    public List<Room> findByStandardPeople(Integer standardPeople);

    public List<Room> findByMaxPeople(Integer maxPeople);
}
