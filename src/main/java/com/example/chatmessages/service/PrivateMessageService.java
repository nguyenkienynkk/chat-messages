package com.example.chatmessages.service;

import com.example.chatmessages.common.PageResponse;
import com.example.chatmessages.dto.request.PrivateMessageRequest;
import com.example.chatmessages.dto.response.ChatPartnerResponse;
import com.example.chatmessages.dto.response.MessageSenderAndReceiveResponse;
import com.example.chatmessages.dto.response.PrivateMessageResponse;
import com.example.chatmessages.dto.response.UserResponse;

import java.util.List;

public interface PrivateMessageService {
    PrivateMessageResponse createPrivateMessage(PrivateMessageRequest privateMessageRequest);

    PageResponse<List<PrivateMessageResponse>> getAllPrivateMessages(int pageNo, int pageSize);

    PrivateMessageResponse getPrivateMessageById(Integer id);

    PageResponse<List<MessageSenderAndReceiveResponse>> getMessagesBySenderAndReceiver(Integer senderId, Integer receiverId, int pageNo, int pageSize);

    PrivateMessageResponse updatePrivateMessage(Integer id, PrivateMessageRequest requestDTO);

    void deletePrivateMessage(Integer id);

    ChatPartnerResponse getChatPartnersAndRooms(Integer userId);
}
