package com.example.chatmessages.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class PrivateMessageRequest {
    private String message;
    private String messageType;
    private String attachment;
    private Instant sentAt;
    private Integer senderId;
    private Integer receiverId;
}