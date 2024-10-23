package com.example.chatmessages.service;

import com.example.chatmessages.common.PageResponse;
import com.example.chatmessages.constant.ErrorCode;
import com.example.chatmessages.dto.request.RoomMemberRequest;
import com.example.chatmessages.dto.response.RoomMemberResponse;
import com.example.chatmessages.entity.RoomMember;
import com.example.chatmessages.enums.SortType;
import com.example.chatmessages.exception.AppException;
import com.example.chatmessages.exception.NotFoundException;
import com.example.chatmessages.mapper.RoomMemberMapper;
import com.example.chatmessages.repository.RoomMemberRepository;
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
public class RoomMemberService {

    RoomMemberRepository roomMemberRepository;
    RoomMemberMapper roomMemberMapper;
    UserRepository userRepository;
    RoomRepository roomRepository;

    public RoomMemberResponse addMember(RoomMemberRequest request) {
        RoomMember roomMember = roomMemberMapper.toEntity(request);

        roomMember.setUser(userRepository.findById(request.getUserId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_EXISTED.getMessage())));
        roomMember.setRoom(roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.ROOM_NOT_FOUND.getMessage())));

        RoomMember savedRoomMember = roomMemberRepository.save(roomMember);
        return roomMemberMapper.toResponseDTO(savedRoomMember);
    }

    public PageResponse<List<RoomMemberResponse>> getAllMembers(int pageNo, int pageSize) {
        Pageable pageable = PageUtils.createPageable(pageNo, pageSize, "joinedAt", SortType.DESC.getValue());

        Page<RoomMember> memberPage = roomMemberRepository.findAll(pageable);

        List<RoomMemberResponse> responseList = memberPage.getContent().stream()
                .map(roomMemberMapper::toResponseDTO)
                .toList();

        return PageResponse.<List<RoomMemberResponse>>builder()
                .page(memberPage.getNumber())
                .size(memberPage.getSize())
                .totalPage(memberPage.getTotalPages())
                .items(responseList)
                .build();
    }
    public RoomMemberResponse getMemberByUserIdAndRoomId(Integer userId, Integer roomId) {
        RoomMember roomMember = roomMemberRepository.findByUserIdAndRoomId(userId, roomId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.ROOM_MEMBER_NOT_FOUND.getMessage()));
        return roomMemberMapper.toResponseDTO(roomMember);
    }

    public RoomMemberResponse updateMember(Integer userId, Integer roomId, RoomMemberRequest request) {
        RoomMember roomMember = roomMemberRepository.findByUserIdAndRoomId(userId, roomId)
                .orElseThrow(() -> new AppException(ErrorCode.ROOM_MEMBER_NOT_FOUND));

        roomMember.setRole(request.getRole());
        RoomMember updatedRoomMember = roomMemberRepository.save(roomMember);
        return roomMemberMapper.toResponseDTO(updatedRoomMember);
    }

    public void removeMember(Integer userId, Integer roomId) {
        RoomMember roomMember = roomMemberRepository.findByUserIdAndRoomId(userId, roomId)
                .orElseThrow(() -> new AppException(ErrorCode.ROOM_MEMBER_NOT_FOUND));
        roomMemberRepository.delete(roomMember);
    }
}
