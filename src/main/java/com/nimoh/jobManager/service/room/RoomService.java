package com.nimoh.jobManager.service.room;

import com.nimoh.jobManager.data.dto.room.RoomDetailResponse;

import java.util.List;

public interface RoomService {
    public RoomDetailResponse findById(Long roomId);

    public List<RoomDetailResponse> findAll();

    public List<RoomDetailResponse> findByName(String roomName);
    public List<RoomDetailResponse> findByMaxPeople(String maxPeople);

    List<RoomDetailResponse> findByStandardPeople(String StandardPeople);
}
