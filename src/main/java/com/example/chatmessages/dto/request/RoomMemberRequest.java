package com.example.chatmessages.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomMemberRequest {
    private Integer userId;
    private Integer roomId;
    private String role;
}
