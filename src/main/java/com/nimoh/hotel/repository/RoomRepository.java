package com.nimoh.hotel.repository;


import com.nimoh.hotel.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    public Optional<Room> findByName(String roomName);

    public List<Room> findByStandardPeople(Integer standardPeople);

    public List<Room> findByMaxPeople(Integer maxPeople);
}
