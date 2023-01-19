package com.nimoh.hotel.controller;

import com.nimoh.hotel.data.dto.room.RoomDetailResponse;
import com.nimoh.hotel.commons.room.RoomException;
import com.nimoh.hotel.service.room.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

/**
 * 방 컨트롤러
 * @author nimoh
 */
@RestController
@RequestMapping("/api/v1/room")
public class RoomController {

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<RoomDetailResponse> get(
            @PathVariable Long roomId
    ) {
        try{
            RoomDetailResponse result = roomService.findById(roomId);
            return ResponseEntity.ok(result);
        }catch (RoomException e){
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("")
    public ResponseEntity<List<RoomDetailResponse>> search(
            @RequestParam("search") String search,
            @RequestParam("value") String value
    ) {
        List<RoomDetailResponse> result;
        try{
            switch (search){
                case "name":
                    result = roomService.findByName(value);
                    break;
                case "standardPeople":
                    result = roomService.findByStandardPeople(value);
                    break;
                case "maxPeople":
                    result = roomService.findByMaxPeople(value);
                    break;
                default:
                    return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(result);
        }catch (RoomException e1){
            return ResponseEntity.noContent().build();
        }catch (Exception e2){
            return ResponseEntity.internalServerError().build();
        }
    }
}
