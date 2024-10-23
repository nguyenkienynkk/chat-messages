package com.example.chatmessages.service;

import com.example.chatmessages.common.PageResponse;
import com.example.chatmessages.dto.request.RoomRequest;
import com.example.chatmessages.dto.response.RoomResponse;

import java.util.List;

public interface RoomService {

    RoomResponse createRoom(RoomRequest roomRequest);

    PageResponse<List<RoomResponse>> getAllRooms(int pageNo, int pageSize);

    RoomResponse getRoomById(Integer id);

    RoomResponse updateRoom(Integer id, RoomRequest roomRequest);

    void deleteRoom(Integer id);
}
