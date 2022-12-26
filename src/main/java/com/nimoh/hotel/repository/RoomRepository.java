package com.nimoh.hotel.repository;


import com.nimoh.hotel.domain.Room;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository {

    Room get(int roomIdx);

    List<Room> getList();
}
