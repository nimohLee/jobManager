package com.nimoh.hotel.service.room;

import com.nimoh.hotel.domain.Room;
import com.nimoh.hotel.dto.room.RoomDetailResponse;

import java.util.List;

public interface RoomService {
    public RoomDetailResponse findById(Long roomId);

    public List<Room> findAll();

    public List<RoomDetailResponse> findByName(String roomName);
    public List<RoomDetailResponse> findByMaxPeople(int maxPeople);
}
