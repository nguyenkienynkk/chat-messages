package com.example.chatmessages.mapper;

import com.example.chatmessages.dto.request.MessageRequest;
import com.example.chatmessages.dto.response.MessageResponse;
import com.example.chatmessages.entity.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    Message toEntity(MessageRequest request);

    @Mapping(source = "room.id", target = "roomId")
    @Mapping(source = "sender.id", target = "senderId")
    @Mapping(source = "messageType", target = "messageType")
    MessageResponse toResponseDTO(Message message);
}
