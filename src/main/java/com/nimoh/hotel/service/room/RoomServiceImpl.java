package com.nimoh.hotel.service.room;

import com.nimoh.hotel.data.entity.Room;
import com.nimoh.hotel.data.dto.room.RoomDetailResponse;
import com.nimoh.hotel.commons.room.RoomErrorResult;
import com.nimoh.hotel.commons.room.RoomException;
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
            throw new RoomException(RoomErrorResult.ROOM_NOT_FOUND);
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
        return getRoomDetailResponses(findRooms);
    }

    public List<RoomDetailResponse> findByMaxPeople(String maxPeople) {
        List<Room> findRooms = roomRepository.findByMaxPeople(Integer.parseInt(maxPeople));
        if (findRooms.isEmpty()) {
            throw new RoomException(RoomErrorResult.ROOM_NOT_FOUND);
        }
        return getRoomDetailResponses(findRooms);
    }

    public List<RoomDetailResponse> findByStandardPeople(String standardPeople) {
        List<Room> findRooms = roomRepository.findByStandardPeople(Integer.parseInt(standardPeople));
        if (findRooms.isEmpty()) {
            throw new RoomException(RoomErrorResult.ROOM_NOT_FOUND);
        }
        return getRoomDetailResponses(findRooms);
    }

    private static List<RoomDetailResponse> getRoomDetailResponses(List<Room> findRooms) {
        return findRooms.stream().map(
                v -> RoomDetailResponse.builder()
                        .name(v.getName())
                        .countOfRooms(v.getCountOfRooms())
                        .standardPeople(v.getStandardPeople())
                        .maxPeople(v.getMaxPeople())
                        .description(v.getDescription())
                        .build()
        ).collect(Collectors.toList());
    }

    @Override
    public List<RoomDetailResponse> findAll() {
        List<Room> findRooms = roomRepository.findAll();
        if(findRooms.isEmpty()){
            throw new RoomException(RoomErrorResult.ROOM_NOT_FOUND);
        }
        return getRoomDetailResponses(findRooms);
    }
}