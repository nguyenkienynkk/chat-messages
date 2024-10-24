package com.example.chatmessages.service.impl;

import com.example.chatmessages.common.PageResponse;
import com.example.chatmessages.constant.ErrorCode;
import com.example.chatmessages.dto.request.RoomExitRequest;
import com.example.chatmessages.dto.response.RoomExitResponse;
import com.example.chatmessages.entity.Room;
import com.example.chatmessages.entity.RoomExit;
import com.example.chatmessages.entity.RoomExitId;
import com.example.chatmessages.entity.User;
import com.example.chatmessages.enums.SortType;
import com.example.chatmessages.exception.NotFoundException;
import com.example.chatmessages.mapper.RoomExitMapper;
import com.example.chatmessages.repository.RoomExitRepository;
import com.example.chatmessages.repository.RoomRepository;
import com.example.chatmessages.repository.UserRepository;
import com.example.chatmessages.service.RoomExitService;
import com.example.chatmessages.utils.PageUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class RoomExitServiceImpl implements RoomExitService {

    RoomExitRepository roomExitRepository;
    RoomExitMapper roomExitMapper;
    UserRepository userRepository;
    RoomRepository roomRepository;

    @Override
    public RoomExitResponse exitRoom(RoomExitRequest request) {
        RoomExitId roomExitId = new RoomExitId(request.getUserId(), request.getRoomId());

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND.getMessage()));

        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.ROOM_NOT_FOUND.getMessage()));

        RoomExit roomExit = RoomExit.builder()
                .id(roomExitId)
                .user(user)
                .room(room)
                .build();

        roomExit = roomExitRepository.save(roomExit);
        return roomExitMapper.toResponse(roomExit);
    }

    @Override
    public PageResponse<List<RoomExitResponse>> getAllExits(int pageNo, int pageSize) {
        Pageable pageable = PageUtils.createPageable(pageNo, pageSize, "exitedAt", SortType.DESC.getValue());
        Page<RoomExit> exitPage = roomExitRepository.findAll(pageable);

        List<RoomExitResponse> responseList = exitPage.getContent().stream()
                .map(roomExitMapper::toResponse)
                .toList();

        return PageResponse.<List<RoomExitResponse>>builder()
                .page(exitPage.getNumber())
                .size(exitPage.getSize())
                .totalPage(exitPage.getTotalPages())
                .items(responseList)
                .build();
    }

    @Override
    public Optional<RoomExitResponse> getExitById(RoomExitId id) {
        return roomExitRepository.findById(id)
                .map(roomExitMapper::toResponse);
    }

    @Override
    public RoomExitResponse updateExit(RoomExitId id, RoomExitRequest request) {
        RoomExit roomExit = roomExitRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.ROOM_EXIT_NOT_FOUND.getMessage()));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND.getMessage()));

        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.ROOM_NOT_FOUND.getMessage()));

        roomExit.setUser(user);
        roomExit.setRoom(room);
        roomExit = roomExitRepository.save(roomExit);
        return roomExitMapper.toResponse(roomExit);
    }

    @Override
    public void removeExit(RoomExitId id) {
        RoomExit roomExit = roomExitRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.ROOM_EXIT_NOT_FOUND.getMessage()));
        roomExitRepository.delete(roomExit);
    }
}
