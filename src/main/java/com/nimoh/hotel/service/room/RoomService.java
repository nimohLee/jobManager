package com.nimoh.hotel.service.room;

import com.nimoh.hotel.domain.Room;

import java.util.List;

public interface RoomService {
    public Room findById(Long roomId);

    public List<Room> findAll();
}
