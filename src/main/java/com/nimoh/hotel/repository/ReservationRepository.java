package com.nimoh.hotel.repository;


import com.nimoh.hotel.data.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
