package com.nimoh.jobManager.repository;

import com.nimoh.jobManager.data.entity.Amenity;
import com.nimoh.jobManager.data.entity.Reservation;
import com.nimoh.jobManager.data.entity.Room;
import com.nimoh.jobManager.data.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ReservationRepositoryTest {

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private AmenityRepository amenityRepository;

    @Test
    public void 예약성공() {
        //given
        Reservation reservation = Reservation.builder()
                .id(1L)
                .user(User.builder().build())
                .room(Room.builder().build())
                .checkIn(LocalDate.now())
                .checkOut(LocalDate.now())
                .build();
        //when
        final Reservation result = reservationRepository.save(reservation);
        //then
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    public void 예약id로예약조회실패_해당예약없음() {
        //given
        //when
        final Optional<Reservation> result = reservationRepository.findById(1L);
        //then
        assertThat(result).isEmpty();
    }

    @Test
    public void 예약id로예약조회성공() {
        //given
        Reservation reservation = Reservation.builder()
                .id(1L)
                .user(User.builder().build())
                .room(Room.builder().build())
                .checkIn(LocalDate.now())
                .checkOut(LocalDate.now())
                .build();
        //when
        reservationRepository.save(reservation);
        final Optional<Reservation> result = reservationRepository.findById(1L);
        //then
        assertThat(result.isPresent()).isTrue();
    }

    @Test
    public void 예약유저로조회성공() {
        //given
        final User user = user();
        userRepository.save(user());

        final List<Amenity> amenities = new ArrayList<>();
        final Amenity tv = Amenity.builder().id(1L).name("TV").build();
        final Amenity bed = Amenity.builder().id(2L).name("Bed").build();
        amenities.add(tv);
        amenities.add(bed);

        amenityRepository.save(tv);
        amenityRepository.save(bed);

        Room room = room();
        room.setAmenities(amenities);
        roomRepository.save(room);

        final Reservation reservation = Reservation.builder()
                .user(user)
                .room(room)
                .checkIn(LocalDate.now())
                .checkOut(LocalDate.now())
                .build();
        //when
        reservationRepository.save(reservation);

        Optional<List<Reservation>> result = Optional.ofNullable(reservationRepository.findByUser(Optional.ofNullable(user)));
        //then
        assertThat(result.get().size()).isEqualTo(1);
    }

    @Test
    public void 해당일에예약된방개수조회() {
        final User user = user();
        userRepository.save(user());

        final List<Amenity> amenities = new ArrayList<>();
        final Amenity tv = Amenity.builder().id(1L).name("TV").build();
        final Amenity bed = Amenity.builder().id(2L).name("Bed").build();
        amenities.add(tv);
        amenities.add(bed);

        amenityRepository.save(tv);
        amenityRepository.save(bed);

        Room room = room();
        room.setAmenities(amenities);
        roomRepository.save(room);

        //when
        reservationRepository.save(Reservation.builder()
                .user(user)
                .room(room)
                .checkIn(LocalDate.now())
                .checkOut(LocalDate.now())
                .build());
        reservationRepository.save(Reservation.builder()
                .user(user)
                .room(room)
                .checkIn(LocalDate.now())
                .checkOut(LocalDate.now())
                .build());
        reservationRepository.save(Reservation.builder()
                .user(user)
                .room(room)
                .checkIn(LocalDate.now())
                .checkOut(LocalDate.now())
                .build());

        Integer result = reservationRepository.countByRoom(Optional.of(room));
        //then
        assertThat(result).isEqualTo(3);
    }

    private Room room(){
        return Room.builder()
                .id(1L)
                .description("testRoom")
                .countOfRooms(4)
                .maxPeople(4)
                .standardPeople(2)
                .name("Rose")
                .build();
    }

    private User user(){
        return User.builder()
                .id(1L)
                .uid("nimoh123")
                .name("nimoh")
                .password("12345678")
                .email("test@test.com")
                .build();
    }
}
