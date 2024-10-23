package com.example.chatmessages.controller;

import com.example.chatmessages.dto.request.RoomMemberRequest;
import com.example.chatmessages.dto.response.RoomMemberResponse;
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

    @GetMapping
    public ResponseEntity<List<RoomMemberResponse>> getAllMembers() {
        List<RoomMemberResponse> members = roomMemberService.getAllMembers();
        return ResponseEntity.ok(members);
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
