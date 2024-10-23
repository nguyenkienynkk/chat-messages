package com.example.chatmessages.mapper;

import com.example.chatmessages.dto.request.UserRequest;
import com.example.chatmessages.dto.request.UserFullRequest;
import com.example.chatmessages.dto.response.UserResponse;
import com.example.chatmessages.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toResponseDTO(User user);
    User toEntity(UserRequest userRequest);

    void updateUserFromDTO(UserFullRequest userRequestDTO, @MappingTarget User user);
}
