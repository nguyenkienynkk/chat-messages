package com.example.chatmessages.service;

import com.example.chatmessages.dto.request.RoomRequest;
import com.example.chatmessages.dto.response.RoomResponse;
import com.example.chatmessages.entity.Room;
import com.example.chatmessages.entity.User;
import com.example.chatmessages.mapper.RoomMapper;
import com.example.chatmessages.repository.RoomRepository;
import com.example.chatmessages.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;
    private final UserRepository userRepository;

    public RoomResponse createRoom(RoomRequest roomRequest) {
        User createdBy = userRepository.findById(roomRequest.getCreatedBy())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Room room = roomMapper.toEntity(roomRequest);
        room.setCreatedBy(createdBy); // Set the creator's User object

        Room savedRoom = roomRepository.save(room);
        return roomMapper.toResponseDTO(savedRoom);
    }

    public List<RoomResponse> getAllRooms() {
        return roomRepository.findAll().stream()
                .map(roomMapper::toResponseDTO)
                .toList();
    }

    public RoomResponse getRoomById(Integer id) {
        return roomRepository.findById(id)
                .map(roomMapper::toResponseDTO)
                .orElseThrow(() -> new RuntimeException("Room not found"));
    }

    public RoomResponse updateRoom(Integer id, RoomRequest roomRequest) {
        return roomRepository.findById(id).map(room -> {
            room.setName(roomRequest.getName());
            room.setDescription(roomRequest.getDescription());
            room.setIsPrivate(roomRequest.getIsPrivate());
            return roomMapper.toResponseDTO(roomRepository.save(room));
        }).orElseThrow(() -> new RuntimeException("Room not found"));
    }

    public void deleteRoom(Integer id) {
        roomRepository.deleteById(id);
    }
}
