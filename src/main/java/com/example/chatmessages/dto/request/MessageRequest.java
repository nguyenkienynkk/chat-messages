package com.example.chatmessages.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageRequest {
    private Integer roomId;       // ID của phòng chat
    private Integer senderId;     // ID của người gửi
    private String message;        // Nội dung tin nhắn
    private String messageType;    // Loại tin nhắn (text, image, file, v.v.)
    private String attachment;      // Đường dẫn đến file đính kèm (nếu có)
}
