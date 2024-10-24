package com.example.chatmessages.service;

import com.example.chatmessages.common.PageResponse;
import com.example.chatmessages.dto.request.RoomMemberRequest;
import com.example.chatmessages.dto.response.RoomMemberResponse;
import com.example.chatmessages.entity.User;

import java.util.List;

public interface RoomMemberService {

    RoomMemberResponse addMember(RoomMemberRequest request);

    PageResponse<List<RoomMemberResponse>> getAllMembers(int pageNo, int pageSize);

    RoomMemberResponse getMemberByUserIdAndRoomId(Integer userId, Integer roomId);

    RoomMemberResponse updateMember(Integer userId, Integer roomId, RoomMemberRequest request);

    void removeMember(Integer userId, Integer roomId);
    List<User> getMembersByRoomId(Integer roomId);
    RoomMemberResponse addMemberByUsername(String username, Integer roomId, String role);
}
