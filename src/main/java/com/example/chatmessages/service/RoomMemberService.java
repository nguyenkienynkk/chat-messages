package com.example.chatmessages.service;

import com.example.chatmessages.dto.request.RoomMemberRequest;
import com.example.chatmessages.dto.response.RoomMemberResponse;
import com.example.chatmessages.entity.RoomMember;
import com.example.chatmessages.mapper.RoomMemberMapper;
import com.example.chatmessages.repository.RoomMemberRepository;
import com.example.chatmessages.repository.RoomRepository;
import com.example.chatmessages.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomMemberService {

    private final RoomMemberRepository roomMemberRepository;
    private final RoomMemberMapper roomMemberMapper;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    public RoomMemberResponse addMember(RoomMemberRequest request) {
        RoomMember roomMember = roomMemberMapper.toEntity(request);

        // Set the user and room references using the IDs from the request
        roomMember.setUser(userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found")));
        roomMember.setRoom(roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found")));

        RoomMember savedRoomMember = roomMemberRepository.save(roomMember);
        return roomMemberMapper.toResponseDTO(savedRoomMember);
    }

    public List<RoomMemberResponse> getAllMembers() {
        return roomMemberRepository.findAll().stream()
                .map(roomMemberMapper::toResponseDTO)
                .toList();
    }

    public RoomMemberResponse getMemberByUserIdAndRoomId(Integer userId, Integer roomId) {
        RoomMember roomMember = roomMemberRepository.findByUserIdAndRoomId(userId, roomId)
                .orElseThrow(() -> new RuntimeException("Room member not found"));
        return roomMemberMapper.toResponseDTO(roomMember);
    }

    public RoomMemberResponse updateMember(Integer userId, Integer roomId, RoomMemberRequest request) {
        RoomMember roomMember = roomMemberRepository.findByUserIdAndRoomId(userId, roomId)
                .orElseThrow(() -> new RuntimeException("Room member not found"));

        roomMember.setRole(request.getRole());
        RoomMember updatedRoomMember = roomMemberRepository.save(roomMember);
        return roomMemberMapper.toResponseDTO(updatedRoomMember);
    }

    public void removeMember(Integer userId, Integer roomId) {
        RoomMember roomMember = roomMemberRepository.findByUserIdAndRoomId(userId, roomId)
                .orElseThrow(() -> new RuntimeException("Room member not found"));
        roomMemberRepository.delete(roomMember);
    }
}
