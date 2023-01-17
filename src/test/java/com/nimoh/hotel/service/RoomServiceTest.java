package com.nimoh.hotel.service;

import com.nimoh.hotel.data.entity.Room;
import com.nimoh.hotel.data.dto.room.RoomDetailResponse;
import com.nimoh.hotel.commons.room.RoomErrorResult;
import com.nimoh.hotel.commons.room.RoomException;
import com.nimoh.hotel.repository.RoomRepository;
import com.nimoh.hotel.service.room.RoomServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {
    @Mock
    private RoomRepository roomRepository;
    @InjectMocks
    private RoomServiceImpl roomService;

    @Test
    public void id로방하나조회실패_방id에해당하는방이없음() {
        //given
        //when
        final RoomException result = assertThrows(RoomException.class, () -> roomService.findById(2L));
        //then
        assertThat(result.getErrorResult()).isEqualTo(RoomErrorResult.ROOM_NOT_FOUND);
    }

    @Test
    public void 방id로방하나조회성공() {
        //given
        doReturn(Optional.of(Room.builder().build())).when(roomRepository).findById(any());
        //when
        RoomDetailResponse result = roomService.findById(1L);
        //then
        assertThat(result).isNotNull();
    }

    @Test
    public void 방이름으로조회실패_해당방이름없음() {
        //given
        //when
        RoomException result = assertThrows(RoomException.class, ()->roomService.findByName(any()));
        //then
        assertThat(result.getErrorResult()).isEqualTo(RoomErrorResult.ROOM_NOT_FOUND);
    }

    @Test
    public void 방이름으로하나조회성공() {
        //given
        List<Room> rooms = Arrays.asList(
                room()
        );
        doReturn(rooms).when(roomRepository).findByNameContainingIgnoreCase(any());
        //when
        List<RoomDetailResponse> result = roomService.findByName("swee");
        //then
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void 최대인원으로조회실패_해당방이없음() {
        //given
        final String maxPeople = "10";
        //when
        RoomException result = assertThrows(RoomException.class, ()-> roomService.findByMaxPeople(maxPeople));
        //then
        assertThat(result.getErrorResult()).isEqualTo(RoomErrorResult.ROOM_NOT_FOUND);
    }

    @Test
    public void 최대인원으로조회성공() {
        //given
        final String maxPeople = "2";
        final List<Room> rooms = Arrays.asList(
                Room.builder().id(1L).name("sweet").maxPeople(2).standardPeople(3).countOfRooms(4).description("description").build(),
                Room.builder()
                        .id(2L).name("rose").maxPeople(2).standardPeople(3).countOfRooms(4).description("description").build()
        );
        doReturn(rooms).when(roomRepository).findByMaxPeople(2);
        //when
        final List<RoomDetailResponse> result = roomService.findByMaxPeople(maxPeople);
        //then
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    public void 기준인원으로조회실패_해당방이없음() {
        //given
        //when
        RoomException result = assertThrows(RoomException.class, ()-> roomService.findByStandardPeople("10"));
        //then
        assertThat(result.getErrorResult()).isEqualTo(RoomErrorResult.ROOM_NOT_FOUND);
    }

    @Test
    public void 기준인원으로조회성공() {
        //given
        final int standardPeople = 2;
        final List<Room> rooms = Arrays.asList(
                Room.builder().id(1L).name("sweet").maxPeople(4).standardPeople(standardPeople).countOfRooms(4).description("description").build(),
                Room.builder()
                        .id(2L).name("rose").maxPeople(4).standardPeople(standardPeople).countOfRooms(4).description("description").build()
        );
        doReturn(rooms).when(roomRepository).findByStandardPeople(standardPeople);
        //when
        final List<RoomDetailResponse> result = roomService.findByStandardPeople("2");
        //then
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    public void 모든방조회실패_방이하나도없음() {
        //given
        //when
        RoomException result = assertThrows(RoomException.class, ()->roomService.findAll());
        //then
        assertThat(result.getErrorResult()).isEqualTo(RoomErrorResult.ROOM_NOT_FOUND);
    }
    @Test
    public void 모든방조회성공() {
        //given
        final List<Room> rooms = Arrays.asList(
                Room.builder().build(),
                Room.builder().build(),
                Room.builder().build()
        );
        doReturn(rooms).when(roomRepository).findAll();
        //when
        final List<RoomDetailResponse> result = roomService.findAll();
        //then
        assertThat(result.size()).isEqualTo(3);
    }
    private Room room() {
        return Room.builder()
                .id(1L)
                .name("sweet")
                .standardPeople(2)
                .maxPeople(4)
                .description("description")
                .countOfRooms(5)
                .build();
    }
}
