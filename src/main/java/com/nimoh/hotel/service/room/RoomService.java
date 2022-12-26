package com.nimoh.hotel.service.room;

import com.nimoh.hotel.domain.Room;

import java.util.List;

public interface RoomService {
    public Room get(int roomIdx);

    public List<Room> getList();
}
