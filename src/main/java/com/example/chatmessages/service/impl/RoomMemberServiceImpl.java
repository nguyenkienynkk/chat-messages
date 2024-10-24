package com.example.chatmessages.service.impl;

import com.example.chatmessages.common.PageResponse;
import com.example.chatmessages.constant.ErrorCode;
import com.example.chatmessages.dto.request.RoomMemberRequest;
import com.example.chatmessages.dto.response.RoomMemberResponse;
import com.example.chatmessages.entity.Room;
import com.example.chatmessages.entity.RoomMember;
import com.example.chatmessages.entity.RoomMemberId;
import com.example.chatmessages.entity.User;
import com.example.chatmessages.enums.SortType;
import com.example.chatmessages.exception.AlreadyExistsException;
import com.example.chatmessages.exception.AppException;
import com.example.chatmessages.exception.NotFoundException;
import com.example.chatmessages.mapper.RoomMemberMapper;
import com.example.chatmessages.repository.RoomMemberRepository;
import com.example.chatmessages.repository.RoomRepository;
import com.example.chatmessages.repository.UserRepository;
import com.example.chatmessages.service.RoomMemberService;
import com.example.chatmessages.utils.PageUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoomMemberServiceImpl implements RoomMemberService {

    RoomMemberRepository roomMemberRepository;
    RoomMemberMapper roomMemberMapper;
    UserRepository userRepository;
    RoomRepository roomRepository;

    @Override
    public RoomMemberResponse addMember(RoomMemberRequest request) {
        RoomMemberId roomMemberId = RoomMemberId.builder()
                .userId(request.getUserId())
                .roomId(request.getRoomId())
                .build();

        if (roomMemberRepository.existsById(roomMemberId))
            throw new AlreadyExistsException("User is already a member of this room.");

        RoomMember roomMember = new RoomMember();
        roomMember.setId(roomMemberId);
        roomMember.setRole(request.getRole());

        roomMember.setUser(userRepository.findById(request.getUserId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_EXISTED.getMessage())));
        roomMember.setRoom(roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.ROOM_NOT_FOUND.getMessage())));
        roomMember.setJoinedAt(Instant.now());

        RoomMember savedRoomMember = roomMemberRepository.save(roomMember);
        return roomMemberMapper.toResponseDTO(savedRoomMember);
    }
    @Override
    public List<User> getMembersByRoomId(Integer roomId) {
        return roomMemberRepository.findMembersByRoomId(roomId);
    }

    @Override
    public RoomMemberResponse addMemberByUsername(String username, Integer roomId, String role) {
        // Tìm user theo username
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User with username " + username + " not found"));

        // Kiểm tra xem user đã có trong room chưa
        RoomMemberId roomMemberId = RoomMemberId.builder()
                .userId(user.getId())
                .roomId(roomId)
                .build();

        if (roomMemberRepository.existsById(roomMemberId)) {
            throw new AlreadyExistsException("User is already a member of this room.");
        }

        // Tạo RoomMember mới
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new NotFoundException("Room not found"));

        RoomMember roomMember = new RoomMember();
        roomMember.setId(roomMemberId);
        roomMember.setUser(user);
        roomMember.setRoom(room);
        roomMember.setRole(role);
        roomMember.setJoinedAt(Instant.now());

        RoomMember savedRoomMember = roomMemberRepository.save(roomMember);
        return roomMemberMapper.toResponseDTO(savedRoomMember);
    }


    @Override
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

    @Override
    public RoomMemberResponse getMemberByUserIdAndRoomId(Integer userId, Integer roomId) {
        RoomMember roomMember = roomMemberRepository.findByUserIdAndRoomId(userId, roomId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.ROOM_MEMBER_NOT_FOUND.getMessage()));
        return roomMemberMapper.toResponseDTO(roomMember);
    }

    @Override
    public RoomMemberResponse updateMember(Integer userId, Integer roomId, RoomMemberRequest request) {
        RoomMember roomMember = roomMemberRepository.findByUserIdAndRoomId(userId, roomId)
                .orElseThrow(() -> new AppException(ErrorCode.ROOM_MEMBER_NOT_FOUND));

        roomMember.setRole(request.getRole());
        RoomMember updatedRoomMember = roomMemberRepository.save(roomMember);
        return roomMemberMapper.toResponseDTO(updatedRoomMember);
    }

    @Override
    public void removeMember(Integer userId, Integer roomId) {
        RoomMember roomMember = roomMemberRepository.findByUserIdAndRoomId(userId, roomId)
                .orElseThrow(() -> new AppException(ErrorCode.ROOM_MEMBER_NOT_FOUND));
        roomMemberRepository.delete(roomMember);
    }
    public List<User> getUsersByRoomId(Integer roomId) {
        List<RoomMember> roomMembers = roomMemberRepository.findByRoomId(roomId);
        if (roomMembers.isEmpty()) {
            throw new NotFoundException(ErrorCode.ROOM_MEMBER_NOT_FOUND.getMessage());
        }
        return roomMembers.stream()
                .map(RoomMember::getUser)
                .toList();
    }
}
