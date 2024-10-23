package com.example.chatmessages.service;

import com.example.chatmessages.dto.request.RoomExitRequest;
import com.example.chatmessages.dto.response.RoomExitResponse;
import com.example.chatmessages.entity.Room;
import com.example.chatmessages.entity.RoomExit;
import com.example.chatmessages.entity.RoomExitId;
import com.example.chatmessages.entity.User;
import com.example.chatmessages.mapper.RoomExitMapper;
import com.example.chatmessages.repository.RoomExitRepository;
import com.example.chatmessages.repository.RoomRepository;
import com.example.chatmessages.repository.UserRepository; // Import UserRepository
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomExitService {

    private final RoomExitRepository roomExitRepository;
    private final RoomExitMapper roomExitMapper;
    private final UserRepository userRepository; // Inject UserRepository
    private final RoomRepository roomRepository; // Add RoomRepository

    // Tạo mới RoomExit
    public RoomExitResponse exitRoom(RoomExitRequest request) {
        RoomExitId roomExitId = new RoomExitId(request.getUserId(), request.getRoomId());

        // Fetch the user and room
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found"));

        RoomExit roomExit = RoomExit.builder()
                .id(roomExitId)
                .user(user)
                .room(room)
                .build();

        roomExit = roomExitRepository.save(roomExit);
        return roomExitMapper.toResponse(roomExit);
    }

    // Lấy tất cả RoomExit
    public List<RoomExitResponse> getAllExits() {
        return roomExitRepository.findAll().stream()
                .map(roomExitMapper::toResponse)
                .toList();
    }

    // Lấy thông tin RoomExit theo ID
    public Optional<RoomExitResponse> getExitById(RoomExitId id) { // Change parameter type to RoomExitId
        return roomExitRepository.findById(id)
                .map(roomExitMapper::toResponse);
    }

    // Cập nhật RoomExit
    public RoomExitResponse updateExit(RoomExitId id, RoomExitRequest request) {
        RoomExit roomExit = roomExitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("RoomExit not found"));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found"));

        roomExit.setUser(user);
        roomExit.setRoom(room);
        roomExit = roomExitRepository.save(roomExit);
        return roomExitMapper.toResponse(roomExit);
    }

    // Xóa RoomExit
    public void removeExit(RoomExitId id) { // Change parameter type to RoomExitId
        RoomExit roomExit = roomExitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("RoomExit not found"));
        roomExitRepository.delete(roomExit);
    }
}
