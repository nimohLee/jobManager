package com.nimoh.hotel.repository;


import com.nimoh.hotel.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
