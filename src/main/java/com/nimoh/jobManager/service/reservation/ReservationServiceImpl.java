package com.nimoh.jobManager.service.reservation;

import com.nimoh.jobManager.commons.reservation.ReservationErrorResult;
import com.nimoh.jobManager.commons.reservation.ReservationException;
import com.nimoh.jobManager.data.dto.reservation.ReservationRequest;
import com.nimoh.jobManager.data.dto.reservation.ReservationResponse;
import com.nimoh.jobManager.data.entity.Reservation;
import com.nimoh.jobManager.data.entity.Room;
import com.nimoh.jobManager.data.entity.User;
import com.nimoh.jobManager.repository.ReservationRepository;
import com.nimoh.jobManager.repository.RoomRepository;
import com.nimoh.jobManager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 예약 서비스 구현체
 * @author nimoh
 */
@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository,
                                  UserRepository userRepository,
                                  RoomRepository roomRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public List<ReservationResponse> findByUserId(Long userId) {
        Optional<User> user = userRepository.findById(userId);

        List<Reservation> reservations = reservationRepository.findByUser(user);

        if(reservations.size() < 1){
            throw new ReservationException(ReservationErrorResult.RESERVATION_NOT_FOUND);
        }

        return reservations.stream().map(
                v -> ReservationResponse.builder()
                        .id(v.getId())
                        .room(v.getRoom())
                        .user(v.getUser())
                        .checkIn(v.getCheckIn())
                        .checkOut(v.getCheckOut())
                        .totalPrice(v.getTotalPrice())
                        .build()
        ).collect(Collectors.toList());
    }

    @Override
    public ReservationResponse create(ReservationRequest reservationRequest, Long userId) {
        final Long roomId = reservationRequest.getRoomId();
        final Optional<Room> room = roomRepository.findById(roomId);
        final Optional<User> user = userRepository.findById(userId);
        final Integer reservationedRoomCount = reservationRepository.countByRoom(room);
        final Integer roomCount = roomRepository.findCountOfRoomsById(roomId);

        if (room.isEmpty() || user.isEmpty()) {
            throw new ReservationException(ReservationErrorResult.BAD_REQUEST);
        }

        if (reservationedRoomCount >= roomCount) {
            throw new ReservationException(ReservationErrorResult.NO_EMPTY_ROOM);
        }

        final long period = ChronoUnit.DAYS.between(reservationRequest.getCheckIn(), reservationRequest.getCheckOut());
        final int totalPrice = (int) period * room.get().getPrice();
        final Reservation reservation = Reservation.builder()
                .user(user.get())
                .room(room.get())
                .checkIn(reservationRequest.getCheckIn())
                .checkOut(reservationRequest.getCheckOut())
                .totalPrice(totalPrice)
                .build();

        final Reservation result = reservationRepository.save(reservation);

        return ReservationResponse.builder()
                .id(result.getId())
                .user(result.getUser())
                .room(result.getRoom())
                .checkIn(result.getCheckIn())
                .checkOut(result.getCheckOut())
                .totalPrice(result.getTotalPrice())
                .build();
    }

    @Override
    public ReservationResponse delete(Long reservationId, Long userId) {
        Optional<User> currentUser = userRepository.findById(userId);
        Optional<Reservation> targetReservation = reservationRepository.findById(reservationId);

        if(!currentUser.get().getId().equals(targetReservation.get().getUser().getId())){
            throw new ReservationException(ReservationErrorResult.USER_NOT_MATCHED);
        }
        reservationRepository.deleteById(reservationId);

        return ReservationResponse.builder()
                .id(targetReservation.get().getId())
                .user(targetReservation.get().getUser())
                .room(targetReservation.get().getRoom())
                .checkIn(targetReservation.get().getCheckIn())
                .checkOut(targetReservation.get().getCheckOut())
                .build();
    }
}
