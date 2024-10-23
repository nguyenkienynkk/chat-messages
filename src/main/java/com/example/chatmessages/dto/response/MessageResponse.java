package com.example.chatmessages.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageResponse {
    Integer id;
    Integer roomId;
    Integer senderId;
    String message;
    String messageType;
    String attachment;
    Instant sentAt;

}
