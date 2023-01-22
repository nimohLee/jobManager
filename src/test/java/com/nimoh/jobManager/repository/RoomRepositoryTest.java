package com.nimoh.jobManager.repository;

import com.nimoh.jobManager.data.entity.Amenity;
import com.nimoh.jobManager.data.entity.Room;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RoomRepositoryTest {

    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private AmenityRepository amenityRepository;

    @Test
    public void 방ID로특정방정보가져오기(){
        //given
        Room room = room();
        //when
        roomRepository.save(room);
        Optional<Room> result = roomRepository.findById(1L);
        //then
        assertThat(result.isPresent()).isTrue();
    }

    @Test
    public void 모든방정보가져오기() {
        //given
        List<Room> rooms = Arrays.asList(
                Room.builder().build(),
                Room.builder().build(),
                Room.builder().build()
        );
        //when
        roomRepository.saveAll(rooms);
        List<Room> result = roomRepository.findAll();
        //then
        assertThat(result.size()).isEqualTo(3);

    }

    @Test
    public void 방이름으로특정방정보가져오기() {
        //given
        Room room = room();
        //when
        roomRepository.save(room);
        List<Room> result = roomRepository.findByNameContainingIgnoreCase("swee"); // containing 메서드(%Like%)와 IgnoreCase를 테스트하기 위해 약간 Sweet을 sweet으로 조회
        //then
        assertThat(result.size()).isGreaterThan(0);
    }

    @Test
    public void 기준인원으로방가져오기() {
        //given
        Room room = room();
        Room room2 = Room.builder()
                .id(2L)
                .name("test")
                .maxPeople(4)
                .standardPeople(2)
                .description("description")
                .build();
        Room room3 = Room.builder()
                .id(3L)
                .name("4인용 방")
                .maxPeople(4)
                .standardPeople(4)
                .description("description")
                .build();
        //when
        roomRepository.save(room);
        roomRepository.save(room2);
        roomRepository.save(room3);
        List<Room> result = roomRepository.findByStandardPeople(2);
        //then
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    public void 최대인원으로방가져오기() {
        //given
        Room room = room();
        Room room2 = Room.builder()
                .id(2L)
                .name("test")
                .maxPeople(4)
                .standardPeople(2)
                .description("description")
                .build();
        Room room3 = Room.builder()
                .id(3L)
                .name("4인용 방")
                .maxPeople(6)
                .standardPeople(4)
                .description("description")
                .build();
        //when
        roomRepository.save(room);
        roomRepository.save(room2);
        roomRepository.save(room3);
        List<Room> result = roomRepository.findByMaxPeople(4);
        //then
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    public void 방어메니티가져오기() {
        //given
        final List<Amenity> amenities = new ArrayList<>();
        amenities.add(Amenity.builder().id(1L).name("TV").build());
        amenities.add(Amenity.builder().id(2L).name("Bed").build());

        amenityRepository.save(Amenity.builder().id(1L).name("TV").build());
        amenityRepository.save(Amenity.builder().id(2L).name("Bed").build());

        final Room room = Room.builder()
                .id(1L)
                .name("Rose")
                .countOfRooms(2)
                .description("hello")
                .standardPeople(2)
                .maxPeople(4)
                .amenities(amenities)
                .build();

        roomRepository.save(room);
        //when
        Optional<Room> result = roomRepository.findById(1L);
        //then
        assertThat(result.get().getAmenities().size()).isEqualTo(2);
    }

    private static Room room() {
        return Room.builder()
                .id(1L)
                .name("Sweet")
                .maxPeople(4)
                .standardPeople(2)
                .countOfRooms(5)
                .description("이런 방입니다.")
                .build();
    }


}
