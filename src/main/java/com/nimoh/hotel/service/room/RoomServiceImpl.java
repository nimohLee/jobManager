package com.nimoh.hotel.service.room;

import com.nimoh.hotel.domain.Room;
import com.nimoh.hotel.errors.RoomErrorResult;
import com.nimoh.hotel.errors.RoomException;
import com.nimoh.hotel.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService{

    private final RoomRepository roomRepository;

    @Autowired
    public RoomServiceImpl(RoomRepository roomRepository){
        this.roomRepository = roomRepository;
    }

    @Override
    public Room findById(Long roomId)
    {
        Optional<Room> findRooms = roomRepository.findById(roomId);
        if(findRooms.isEmpty()){
            throw new RoomException(RoomErrorResult.REQUEST_VALUE_INVALID);
        }
        return null;
    }

    @Override
    public List<Room> findAll() {
        return null;
    }
}