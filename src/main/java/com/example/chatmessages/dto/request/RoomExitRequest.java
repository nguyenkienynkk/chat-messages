package com.example.chatmessages.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomExitRequest {
    private Integer userId;
    private Integer roomId;
}
