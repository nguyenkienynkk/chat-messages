package com.example.chatmessages.controller;

import com.example.chatmessages.dto.ApiResponse;
import com.example.chatmessages.dto.request.RoomRequest;
import com.example.chatmessages.dto.response.RoomResponse;
import com.example.chatmessages.service.RoomService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoomController {

    RoomService roomService;

    @PostMapping
    public ApiResponse<RoomResponse> createRoom(@RequestBody RoomRequest roomRequest) {
        RoomResponse createdRoom = roomService.createRoom(roomRequest);
        return new ApiResponse<>(HttpStatus.CREATED.value(), "Room created successfully", createdRoom);
    }

    @GetMapping
    public ApiResponse<List<RoomResponse>> getAllRooms() {
        List<RoomResponse> rooms = roomService.getAllRooms();
        return new ApiResponse<>(HttpStatus.OK.value(), "Rooms retrieved successfully", rooms);
    }

    @GetMapping("/{id}")
    public ApiResponse<RoomResponse> getRoomById(@PathVariable Integer id) {
        RoomResponse room = roomService.getRoomById(id);
        return new ApiResponse<>(HttpStatus.OK.value(), "Room retrieved successfully", room);
    }

    @PutMapping("/{id}")
    public ApiResponse<RoomResponse> updateRoom(@PathVariable Integer id, @RequestBody RoomRequest roomRequest) {
        RoomResponse updatedRoom = roomService.updateRoom(id, roomRequest);
        return new ApiResponse<>(HttpStatus.OK.value(), "Room updated successfully", updatedRoom);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteRoom(@PathVariable Integer id) {
        roomService.deleteRoom(id);
        return new ApiResponse<>(HttpStatus.NO_CONTENT.value(), "Room deleted successfully", null);
    }
}
