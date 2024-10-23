package com.example.chatmessages.mapper;

import com.example.chatmessages.dto.request.RoomMemberRequest;
import com.example.chatmessages.dto.response.RoomMemberResponse;
import com.example.chatmessages.entity.RoomMember;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoomMemberMapper {
    RoomMember toEntity(RoomMemberRequest request);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "room.id", target = "roomId")
    RoomMemberResponse toResponseDTO(RoomMember roomMember);
}
