package com.example.chatmessages.mapper;

import com.example.chatmessages.dto.request.UserRequestDTO;
import com.example.chatmessages.dto.request.UserFullRequest;
import com.example.chatmessages.dto.response.UserResponseDTO;
import com.example.chatmessages.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDTO toResponseDTO(User user);
    User toEntity(UserRequestDTO userRequestDTO);

    void updateUserFromDTO(UserFullRequest userRequestDTO, @MappingTarget User user);
}
