package com.nimoh.hotel.service.room;

import com.nimoh.hotel.domain.Room;
import com.nimoh.hotel.dto.room.RoomDetailResponse;
import com.nimoh.hotel.errors.RoomErrorResult;
import com.nimoh.hotel.errors.RoomException;
import com.nimoh.hotel.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService{

    private final RoomRepository roomRepository;

    @Autowired
    public RoomServiceImpl(RoomRepository roomRepository){
        this.roomRepository = roomRepository;
    }

    @Override
    public RoomDetailResponse findById(Long roomId) {
        Optional<Room> findRooms = roomRepository.findById(roomId);
        if(findRooms.isEmpty()){
            throw new RoomException(RoomErrorResult.REQUEST_VALUE_INVALID);
        }
        return RoomDetailResponse.builder()
                .name(findRooms.get().getName())
                .standardPeople(findRooms.get().getStandardPeople())
                .maxPeople(findRooms.get().getMaxPeople())
                .countOfRooms(findRooms.get().getCountOfRooms())
                .description(findRooms.get().getDescription())
                .build();
    }

    @Override
    public List<RoomDetailResponse> findByName(String roomName) {
        List<Room> findRooms = roomRepository.findByNameContainingIgnoreCase(roomName);
        if(findRooms.isEmpty()){
            throw new RoomException(RoomErrorResult.ROOM_NOT_FOUND);
        }
        return findRooms.stream().map(
                v -> RoomDetailResponse.builder()
                        .name(v.getName())
                        .standardPeople(v.getStandardPeople())
                        .maxPeople(v.getMaxPeople())
                        .description(v.getDescription())
                        .countOfRooms(v.getCountOfRooms())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<Room> findAll() {
        return null;
    }
}