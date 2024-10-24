package com.example.chatmessages.dto.request;

import com.example.chatmessages.constant.MessageType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageRequest {
    @NotNull(message = "Room ID is required")
    Integer roomId;

    @NotNull(message = "Sender ID is required")
    Integer senderId;

    @NotBlank(message = "Message cannot be blank")
    String message;

    MessageType messageType;

    String attachment;
}
