package com.example.chatmessages.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageSenderAndReceiveResponse {
    Integer id;
    String message;
    Instant sentAt;
    Integer senderId;
    Integer receiverId;
}
