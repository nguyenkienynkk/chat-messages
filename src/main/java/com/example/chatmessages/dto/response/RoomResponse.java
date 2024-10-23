package com.example.chatmessages.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class RoomResponse {
    private Integer id;
    private String name;
    private String description;
    private Boolean isPrivate;
    private Integer createdBy;
    private Instant createdAt;
}
