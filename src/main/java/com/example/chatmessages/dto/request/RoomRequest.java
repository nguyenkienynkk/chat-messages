package com.example.chatmessages.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoomRequest {
    @NotBlank(message = "Room name is required")
    String name;

    String description;

    Boolean isPrivate;

    @NotNull(message = "Created by user ID is required")
    Integer createdBy;
}
