package com.nimoh.hotel.service;

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
    public void 방하나조회실패_방id에해당하는방이없음() {
        //given
        //when
        final RoomException result = assertThrows(RoomException.class, () -> roomService.findById(any()));
        //then
        assertThat(result.getErrorResult()).isEqualTo(RoomErrorResult.REQUEST_VALUE_INVALID);
    }
}
