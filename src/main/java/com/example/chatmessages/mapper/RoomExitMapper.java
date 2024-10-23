package com.example.chatmessages.mapper;

import com.example.chatmessages.dto.response.RoomExitResponse;
import com.example.chatmessages.entity.RoomExit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoomExitMapper {
    @Mapping(target = "userId",source = "user.id")
    @Mapping(target = "roomId",source = "room.id")
    RoomExitResponse toResponse(RoomExit roomExit);
    RoomExit toEntity(RoomExitResponse roomExitResponse);
}
