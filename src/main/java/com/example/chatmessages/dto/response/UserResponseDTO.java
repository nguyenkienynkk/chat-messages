package com.example.chatmessages.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class UserResponseDTO {
    private Integer id;
    private String username;
    private String email;
    private String avatar;
    private String status;
    private Instant createdAt;
}
