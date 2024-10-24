package com.example.chatmessages.controller;

import com.example.chatmessages.common.PageResponse;
import com.example.chatmessages.dto.ApiResponse;
import com.example.chatmessages.dto.request.RoomMemberRequest;
import com.example.chatmessages.dto.response.RoomMemberResponse;
import com.example.chatmessages.entity.User;
import com.example.chatmessages.service.RoomMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room-members")
@RequiredArgsConstructor
public class RoomMemberController {

    private final RoomMemberService roomMemberService;

    @PostMapping
    public ResponseEntity<RoomMemberResponse> addMember(@RequestBody RoomMemberRequest request) {
        RoomMemberResponse response = roomMemberService.addMember(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping("/{id}/members")
    public ApiResponse<List<User>> getMembersByRoomId(@PathVariable Integer id) {
        List<User> members = roomMemberService.getMembersByRoomId(id);
        return new ApiResponse<>(HttpStatus.OK.value(), "Members retrieved successfully", members);
    }

    @GetMapping
    public ApiResponse<PageResponse<List<RoomMemberResponse>>> getAllMembers(
            @RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") int pageSize) {

        PageResponse<List<RoomMemberResponse>> response = roomMemberService.getAllMembers(pageNo, pageSize);
        return new ApiResponse<>(HttpStatus.OK.value(), "Members retrieved successfully", response);
    }
    @PostMapping("/add-by-username")
    public ResponseEntity<RoomMemberResponse> addMemberByUsername(
            @RequestParam String username,
            @RequestParam Integer roomId,
            @RequestParam String role) {

        RoomMemberResponse response = roomMemberService.addMemberByUsername(username, roomId, role);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{userId}/{roomId}")
    public ResponseEntity<RoomMemberResponse> getMemberById(@PathVariable Integer userId, @PathVariable Integer roomId) {
        RoomMemberResponse member = roomMemberService.getMemberByUserIdAndRoomId(userId, roomId);
        return ResponseEntity.ok(member);
    }

    @PutMapping("/{userId}/{roomId}")
    public ResponseEntity<RoomMemberResponse> updateMember(@PathVariable Integer userId, @PathVariable Integer roomId, @RequestBody RoomMemberRequest request) {
        RoomMemberResponse updatedMember = roomMemberService.updateMember(userId, roomId, request);
        return ResponseEntity.ok(updatedMember);
    }

    @DeleteMapping("/{userId}/{roomId}")
    public ResponseEntity<Void> removeMember(@PathVariable Integer userId, @PathVariable Integer roomId) {
        roomMemberService.removeMember(userId, roomId);
        return ResponseEntity.noContent().build();
    }
}
