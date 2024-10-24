package com.example.chatmessages.dto.response;

import com.example.chatmessages.constant.MessageType;
import com.example.chatmessages.entity.Message;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageResponse {
    Integer id;
    Integer roomId;
    Integer senderId;
    String message;
    MessageType messageType;
    String attachment;
    Instant sentAt;
    public MessageResponse(Message message) {
        this.id = message.getId();
        this.roomId = message.getRoom().getId();
        this.senderId = message.getSender().getId();
        this.message = message.getMessage();
        this.messageType = MessageType.fromString(String.valueOf(message.getMessageType()));
        this.attachment = message.getAttachment();
        this.sentAt = message.getSentAt();
    }
}
