package com.example.chatmessages.controller;

import com.example.chatmessages.common.PageResponse;
import com.example.chatmessages.dto.ApiResponse;
import com.example.chatmessages.dto.request.RoomExitRequest;
import com.example.chatmessages.dto.response.RoomExitResponse;
import com.example.chatmessages.entity.RoomExitId;
import com.example.chatmessages.service.RoomExitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room-exits")
@RequiredArgsConstructor
public class RoomExitController {

    private final RoomExitService roomExitService;
    @PostMapping
    public ApiResponse<RoomExitResponse> exitRoom(@RequestBody RoomExitRequest request) {
        RoomExitResponse roomExitResponse = roomExitService.exitRoom(request);
        return new ApiResponse<>(HttpStatus.CREATED.value(), "User exited the room successfully", roomExitResponse);
    }
    @GetMapping
    public ApiResponse<PageResponse<List<RoomExitResponse>>> getAllExits(
            @RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") int pageSize) {

        PageResponse<List<RoomExitResponse>> response = roomExitService.getAllExits(pageNo, pageSize);
        return new ApiResponse<>(HttpStatus.OK.value(), "Exits retrieved successfully", response);
    }

    @GetMapping("/{userId}/{roomId}")
    public ApiResponse<RoomExitResponse> getExitById(@PathVariable Integer userId, @PathVariable Integer roomId) {
        RoomExitId roomExitId = new RoomExitId(userId, roomId);
        RoomExitResponse roomExitResponse = roomExitService.getExitById(roomExitId)
                .orElseThrow(() -> new RuntimeException("RoomExit not found"));
        return new ApiResponse<>(HttpStatus.OK.value(), "RoomExit retrieved successfully", roomExitResponse);
    }
    @PutMapping("/{userId}/{roomId}")
    public ApiResponse<RoomExitResponse> updateExit(@PathVariable Integer userId, @PathVariable Integer roomId, @RequestBody RoomExitRequest request) {
        RoomExitId roomExitId = new RoomExitId(userId, roomId);
        RoomExitResponse updatedRoomExit = roomExitService.updateExit(roomExitId, request);
        return new ApiResponse<>(HttpStatus.OK.value(), "RoomExit updated successfully", updatedRoomExit);
    }
    @DeleteMapping("/{userId}/{roomId}")
    public ApiResponse<Void> removeExit(@PathVariable Integer userId, @PathVariable Integer roomId) {
        RoomExitId roomExitId = new RoomExitId(userId, roomId);
        roomExitService.removeExit(roomExitId);
        return new ApiResponse<>(HttpStatus.NO_CONTENT.value(), "RoomExit deleted successfully", null);
    }
}
