package com.example.chatmessages.mapper;

import com.example.chatmessages.dto.request.PrivateMessageRequest;
import com.example.chatmessages.dto.response.PrivateMessageResponse;
import com.example.chatmessages.entity.PrivateMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring")
public interface PrivateMessageMapper {

    @Mapping(source = "sender.id", target = "senderId")
    @Mapping(source = "receiver.id", target = "receiverId")
    PrivateMessageResponse toResponseDTO(PrivateMessage privateMessage);

    PrivateMessage toEntity(PrivateMessageRequest privateMessageRequest);
}
