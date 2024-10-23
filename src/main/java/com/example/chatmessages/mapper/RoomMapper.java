package com.example.chatmessages.mapper;

import com.example.chatmessages.dto.request.RoomRequest;
import com.example.chatmessages.dto.request.UserRequestDTO;
import com.example.chatmessages.dto.response.RoomResponse;
import com.example.chatmessages.entity.Room;
import com.example.chatmessages.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface RoomMapper {

    // Map Room to RoomResponse
    @Mapping(target = "createdBy", source = "createdBy.id")
    RoomResponse toResponseDTO(Room room);

    // Map RoomRequest to Room
    @Mapping(target = "createdBy", source = "createdBy", qualifiedByName = "mapToUser")
    Room toEntity(RoomRequest request);

    // Reverse mapping from Integer to User
    @Named("mapToUser")
    default User mapToUser(Integer id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }

    // User entity mapping
    User toEntity(UserRequestDTO userRequestDTO);
}
