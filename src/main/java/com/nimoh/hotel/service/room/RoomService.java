package com.nimoh.hotel.service.room;

import com.nimoh.hotel.data.dto.room.RoomDetailResponse;

import java.util.List;

public interface RoomService {
    public RoomDetailResponse findById(Long roomId);

    public List<RoomDetailResponse> findAll();

    public List<RoomDetailResponse> findByName(String roomName);
    public List<RoomDetailResponse> findByMaxPeople(String maxPeople);

    List<RoomDetailResponse> findByStandardPeople(String StandardPeople);
}
