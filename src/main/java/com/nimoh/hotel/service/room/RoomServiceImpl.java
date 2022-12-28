package com.nimoh.hotel.service.room;

import com.nimoh.hotel.domain.Room;
import com.nimoh.hotel.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService{

    private final RoomRepository roomRepository;

    @Autowired
    public RoomServiceImpl(RoomRepository roomRepository){
        this.roomRepository = roomRepository;
    }

    @Override
    public Room findById(int roomIdx) {
        return null;
    }

    @Override
    public List<Room> findAll() {
        return null;
    }
}