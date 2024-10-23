package com.example.chatmessages.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponseDTO {
    Integer id;
    String username;
    String email;
    String avatar;
    String status;
    Instant createdAt;
}
