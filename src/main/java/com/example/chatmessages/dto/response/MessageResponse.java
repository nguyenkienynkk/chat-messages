package com.example.chatmessages.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class MessageResponse {
    private Integer id;
    private Integer roomId;
    private Integer senderId;
    private String message;
    private String messageType;
    private String attachment;
    private Instant sentAt;

}
