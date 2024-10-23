package com.example.chatmessages.service;

import com.example.chatmessages.common.PageResponse;
import com.example.chatmessages.dto.request.PrivateMessageRequest;
import com.example.chatmessages.dto.response.PrivateMessageResponse;

import java.util.List;

public interface PrivateMessageService {
    PrivateMessageResponse createPrivateMessage(PrivateMessageRequest privateMessageRequest);

    PageResponse<List<PrivateMessageResponse>> getAllPrivateMessages(int pageNo, int pageSize);

    PrivateMessageResponse getPrivateMessageById(Integer id);

    List<PrivateMessageResponse> getMessagesBySenderAndReceiver(Integer senderId, Integer receiverId);

    PrivateMessageResponse updatePrivateMessage(Integer id, PrivateMessageRequest requestDTO);

    void deletePrivateMessage(Integer id);
}
