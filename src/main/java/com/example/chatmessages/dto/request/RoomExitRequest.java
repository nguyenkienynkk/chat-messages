package com.example.chatmessages.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoomExitRequest {
    @NotNull(message = "User ID is required")
    Integer userId;

    @NotNull(message = "Room ID is required")
    Integer roomId;
}
