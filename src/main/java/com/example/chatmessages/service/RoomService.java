package com.example.chatmessages.service;

import com.example.chatmessages.common.PageResponse;
import com.example.chatmessages.constant.ErrorCode;
import com.example.chatmessages.dto.request.RoomRequest;
import com.example.chatmessages.dto.response.RoomResponse;
import com.example.chatmessages.entity.Room;
import com.example.chatmessages.entity.User;
import com.example.chatmessages.enums.SortType;
import com.example.chatmessages.exception.NotFoundException;
import com.example.chatmessages.mapper.RoomMapper;
import com.example.chatmessages.repository.RoomRepository;
import com.example.chatmessages.repository.UserRepository;
import com.example.chatmessages.utils.PageUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class RoomService {

    RoomRepository roomRepository;
    RoomMapper roomMapper;
    UserRepository userRepository;

    public RoomResponse createRoom(RoomRequest roomRequest) {
        User createdBy = userRepository.findById(roomRequest.getCreatedBy())
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND.getMessage()));

        Room room = roomMapper.toEntity(roomRequest);
        room.setCreatedBy(createdBy);

        Room savedRoom = roomRepository.save(room);
        return roomMapper.toResponseDTO(savedRoom);
    }

    public PageResponse<List<RoomResponse>> getAllRooms(int pageNo, int pageSize) {
        Pageable pageable = PageUtils.createPageable(pageNo, pageSize, "name", SortType.ASC.getValue());
        Page<Room> roomPage = roomRepository.findAll(pageable);

        List<RoomResponse> responseList = roomPage.getContent().stream()
                .map(roomMapper::toResponseDTO)
                .toList();

        return PageResponse.<List<RoomResponse>>builder()
                .page(roomPage.getNumber())
                .size(roomPage.getSize())
                .totalPage(roomPage.getTotalPages())
                .items(responseList)
                .build();
    }


    public RoomResponse getRoomById(Integer id) {
        return roomRepository.findById(id)
                .map(roomMapper::toResponseDTO)
                .orElseThrow(() -> new NotFoundException(ErrorCode.ROOM_NOT_FOUND.getMessage()));
    }

    public RoomResponse updateRoom(Integer id, RoomRequest roomRequest) {
        return roomRepository.findById(id).map(room -> {
            room.setName(roomRequest.getName());
            room.setDescription(roomRequest.getDescription());
            room.setIsPrivate(roomRequest.getIsPrivate());
            return roomMapper.toResponseDTO(roomRepository.save(room));
        }).orElseThrow(() -> new NotFoundException(ErrorCode.ROOM_NOT_FOUND.getMessage()));
    }

    public void deleteRoom(Integer id) {
        roomRepository.deleteById(id);
    }
}
