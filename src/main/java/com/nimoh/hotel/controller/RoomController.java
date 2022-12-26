package com.nimoh.hotel.controller;

import com.nimoh.hotel.domain.Room;
import com.nimoh.hotel.service.room.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/{roomIdx}")
    public Room get(@PathVariable int roomIdx) {
        return roomService.get(roomIdx);
    }

    @GetMapping("")
    public List<Room> getList() {
        return roomService.getList();
    }
}
