package com.nimoh.hotel.service;

import com.nimoh.hotel.domain.Room;
import com.nimoh.hotel.dto.room.RoomDetailResponse;
import com.nimoh.hotel.errors.RoomErrorResult;
import com.nimoh.hotel.errors.RoomException;
import com.nimoh.hotel.repository.RoomRepository;
import com.nimoh.hotel.service.room.RoomService;
import com.nimoh.hotel.service.room.RoomServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

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
        assertThat(result.getErrorResult()).isEqualTo(RoomErrorResult.REQUEST_VALUE_INVALID);
    }

    @Test
    public void id로방하나조회성공() {
        //given
        doReturn(Optional.of(Room.builder().build())).when(roomRepository).findById(any());
        //when
        RoomDetailResponse result = roomService.findById(1L);
        //then
        assertThat(result).isNotNull();

    }

}
