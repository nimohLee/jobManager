package com.nimoh.hotel.repository;


import com.nimoh.hotel.data.entity.Reservation;
import com.nimoh.hotel.data.entity.Room;
import com.nimoh.hotel.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUser(Optional<User> user);

    Integer countByRoom(Optional<Room> room);
}
