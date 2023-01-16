package com.nimoh.hotel.service.reservation;

import com.nimoh.hotel.commons.reservation.ReservationErrorResult;
import com.nimoh.hotel.commons.reservation.ReservationException;
import com.nimoh.hotel.data.dto.reservation.ReservationResponse;
import com.nimoh.hotel.data.entity.Reservation;
import com.nimoh.hotel.data.entity.Room;
import com.nimoh.hotel.data.entity.User;
import com.nimoh.hotel.repository.ReservationRepository;
import com.nimoh.hotel.repository.RoomRepository;
import com.nimoh.hotel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
                        .date(v.getDate())
                        .build()
        ).collect(Collectors.toList());
    }

    @Override
    public ReservationResponse create(Long roomId, Long userId) {
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
        final Reservation reservation = Reservation.builder()
                .user(user.get())
                .room(room.get())
                .date(new Date())
                .build();
        final Reservation result = reservationRepository.save(reservation);

        return ReservationResponse.builder()
                .id(result.getId())
                .user(result.getUser())
                .room(result.getRoom())
                .date(result.getDate())
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
                .date(targetReservation.get().getDate())
                .build();
    }
}
