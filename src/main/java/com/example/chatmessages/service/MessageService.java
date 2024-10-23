package com.example.chatmessages.service;

import com.example.chatmessages.common.PageResponse;
import com.example.chatmessages.dto.request.MessageRequest;
import com.example.chatmessages.dto.response.MessageResponse;

import java.util.List;

public interface MessageService {
    MessageResponse createMessage(MessageRequest messageRequest);

    PageResponse<List<MessageResponse>> getAllMessages(int pageNo, int pageSize);

    MessageResponse getMessageById(Integer id);

    MessageResponse updateMessage(Integer id, MessageRequest messageRequest);

    void deleteMessage(Integer id);
    List<MessageResponse> getMessagesByRoomId(Integer roomId);
}
