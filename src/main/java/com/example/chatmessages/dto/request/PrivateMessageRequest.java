package com.example.chatmessages.dto.request;

import com.example.chatmessages.constant.MessageType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PrivateMessageRequest {
    @NotBlank(message = "Message cannot be blank")
    String message;

    MessageType messageType;

    String attachment;

    Instant sentAt;

    @NotNull(message = "Sender ID is required")
    Integer senderId;

    @NotNull(message = "Receiver ID is required")
    private Integer receiverId;
}
