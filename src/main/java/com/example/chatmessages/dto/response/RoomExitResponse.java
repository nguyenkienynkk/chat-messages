package com.example.chatmessages.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class RoomExitResponse {
    private Integer userId;
    private Integer roomId;
    private Instant exitedAt;
}
