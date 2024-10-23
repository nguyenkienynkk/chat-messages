package com.example.chatmessages.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
@Getter
@Setter
public class UserFullRequest {
    private String username;
    private String email;
    private String password;
    private String avatar;
    private String status;
    private Instant createdAt;
}
