package com.example.chatmessages.service;

import com.example.chatmessages.common.PageResponse;
import com.example.chatmessages.dto.request.RoomExitRequest;
import com.example.chatmessages.dto.response.RoomExitResponse;
import com.example.chatmessages.entity.RoomExitId;

import java.util.List;
import java.util.Optional;

public interface RoomExitService {

    RoomExitResponse exitRoom(RoomExitRequest request);

    PageResponse<List<RoomExitResponse>> getAllExits(int pageNo, int pageSize);

    Optional<RoomExitResponse> getExitById(RoomExitId id);

    RoomExitResponse updateExit(RoomExitId id, RoomExitRequest request);

    void removeExit(RoomExitId id);
}
