package com.nimoh.jobManager.service;

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
import com.nimoh.jobManager.service.reservation.ReservationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    @InjectMocks
    private ReservationServiceImpl reservationService;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoomRepository roomRepository;

    @Test
    public void 유저id로예약조회실패_해당유저예약없음() {
        //given
        doReturn(new ArrayList<>()).when(reservationRepository).findByUser(any());
        //when
        final ReservationException result = assertThrows(ReservationException.class, ()->reservationService.findByUserId(any()));
        //then
        assertThat(result.getErrorResult()).isEqualTo(ReservationErrorResult.RESERVATION_NOT_FOUND);
    }

    @Test
    public void 유저id로예약조회성공() {
        //given
        doReturn(Optional.of(User.builder().build())).when(userRepository).findById(any());
        doReturn(Arrays.asList(Reservation.builder().build(),Reservation.builder().build())).when(reservationRepository).findByUser(any());
        //when
        List<ReservationResponse> result = reservationService.findByUserId(1L);
        //then
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    public void 예약취소실패_권한없음() {
        //given
        final Long userId = 1L;
        final Long reservationId = 1L;
        User user = User.builder().id(1L).build();
        doReturn(Optional.of(user)).when(userRepository).findById(any());
        Reservation reservation = Reservation.builder()
                .id(1L)
                .user(User.builder().id(2L).build())
                .room(Room.builder().build())
                .build();
        doReturn(Optional.of(reservation)).when(reservationRepository).findById(any());
        //when
        ReservationException result = assertThrows(ReservationException.class, () -> reservationService.delete(reservationId, userId));
        //then
        assertThat(result.getErrorResult()).isEqualTo(ReservationErrorResult.USER_NOT_MATCHED);
    }

    @Test
    public void 예약취소성공() {
        //given
        final Long userId = 1L;
        final Long reservationId = 1L;
        User user = User.builder().id(1L).build();
        doReturn(Optional.of(user)).when(userRepository).findById(any());
        Reservation reservation = Reservation.builder()
                .id(1L)
                .user(user)
                .room(Room.builder().build())
                .build();
        doReturn(Optional.of(reservation)).when(reservationRepository).findById(any());
        //when
        ReservationResponse result = reservationService.delete(reservationId,userId);
        //then
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    public void 예약실패_해당일에방이없음() {
        //given
        final Long roomId = 1L;
        final Long userId = 1L;
        doReturn(Optional.of(Room.builder().build())).when(roomRepository).findById(any());
        doReturn(Optional.of(User.builder().build())).when(userRepository).findById(any());
        doReturn(3).when(reservationRepository).countByRoom(any());
        doReturn(3).when(roomRepository).findCountOfRoomsById(any());
        //when
        ReservationException result = assertThrows(ReservationException.class, ()->reservationService.create(ReservationRequest.builder().roomId(roomId).checkIn(LocalDate.now()).checkOut(LocalDate.now()).build(), userId));
        //then
        assertThat(result.getErrorResult()).isEqualTo(ReservationErrorResult.NO_EMPTY_ROOM);
    }

    @Test
    public void 예약성공() {
        //given
        final Long roomId = 1L;
        final Long userId = 1L;
        doReturn(Optional.of(room(roomId))).when(roomRepository).findById(any());
        doReturn(Optional.of(user(userId))).when(userRepository).findById(any());
        doReturn(2).when(reservationRepository).countByRoom(any());
        doReturn(3).when(roomRepository).findCountOfRoomsById(any());
        doReturn(Reservation.builder().id(1L).totalPrice(2000).build()).when(reservationRepository).save(any());
        //when
        ReservationResponse result = reservationService.create(ReservationRequest.builder().checkIn(LocalDate.now()).checkOut(LocalDate.now().plusDays(2)).build(), userId);
        //then
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getTotalPrice()).isEqualTo(2000);
    }

    private Room room(Long roomId){
        return Room.builder()
                .id(roomId)
                .name("Rose")
                .countOfRooms(3)
                .standardPeople(2)
                .maxPeople(4)
                .description("hello")
                .price(1000)
                .build();
    }

    private User user(Long userId) {
        return User.builder()
                .id(userId)
                .uid("nimoh123")
                .name("nimoh")
                .password("12345567")
                .email("test@test.com")
                .build();
    }
}
