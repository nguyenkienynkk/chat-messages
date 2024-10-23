package com.example.chatmessages.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class RoomMemberResponse {
    private Integer userId;
    private Integer roomId;
    private String role;
    private Instant joinedAt;
}
