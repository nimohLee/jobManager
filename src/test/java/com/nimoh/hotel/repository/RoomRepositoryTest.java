package com.nimoh.hotel.repository;

import com.nimoh.hotel.domain.Room;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RoomRepositoryTest {

    @Autowired
    private RoomRepository roomRepository;

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
        Optional<Room> result = roomRepository.findByName("Sweet");
        //then
        assertThat(result.isPresent()).isTrue();
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
