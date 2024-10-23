package com.example.chatmessages.mapper;

import com.example.chatmessages.dto.response.RoomExitResponse;
import com.example.chatmessages.entity.RoomExit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoomExitMapper {
    RoomExitResponse toResponse(RoomExit roomExit);
    RoomExit toEntity(RoomExitResponse roomExitResponse);
}
