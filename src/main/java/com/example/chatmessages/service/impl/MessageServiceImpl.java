package com.example.chatmessages.service.impl;

import com.example.chatmessages.common.PageResponse;
import com.example.chatmessages.dto.request.MessageRequest;
import com.example.chatmessages.dto.response.MessageResponse;
import com.example.chatmessages.entity.Message;
import com.example.chatmessages.entity.Room;
import com.example.chatmessages.entity.User;
import com.example.chatmessages.enums.SortType;
import com.example.chatmessages.mapper.MessageMapper;
import com.example.chatmessages.repository.MessageRepository;
import com.example.chatmessages.repository.RoomRepository;
import com.example.chatmessages.repository.UserRepository;
import com.example.chatmessages.service.MessageService;
import com.example.chatmessages.utils.PageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    @Override
    public MessageResponse createMessage(MessageRequest messageRequest) {
        Room room = roomRepository.findById(messageRequest.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found"));

        User sender = userRepository.findById(messageRequest.getSenderId())
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        Message message = messageMapper.toEntity(messageRequest);

        message.setRoom(room);
        message.setSender(sender);
        message.setSentAt(Instant.now());

        Message savedMessage = messageRepository.save(message);

        return messageMapper.toResponseDTO(savedMessage);
    }
    @Override
    public List<MessageResponse> getMessagesByRoomId(Integer roomId) {
        List<Message> messages = messageRepository.findByRoomId(roomId);
        return messages.stream()
                .map(MessageResponse::new)
                .toList();
    }

    @Override
    public PageResponse<List<MessageResponse>> getAllMessages(int pageNo, int pageSize) {
        Pageable pageable = PageUtils.createPageable(pageNo, pageSize, "sentAt", SortType.DESC.getValue());
        Page<Message> messagePage = messageRepository.findAll(pageable);

        List<MessageResponse> responseList = messagePage.getContent().stream().map(messageMapper::toResponseDTO)
                .toList();

        return PageResponse.<List<MessageResponse>>builder()
                .page(messagePage.getNumber())
                .size(messagePage.getSize())
                .totalPage(messagePage.getTotalPages())
                .items(responseList)
                .build();
    }

    @Override
    public MessageResponse getMessageById(Integer id) {
        return messageRepository.findById(id)
                .map(messageMapper::toResponseDTO)
                .orElseThrow(() -> new RuntimeException("Message not found"));
    }

    @Override
    public MessageResponse updateMessage(Integer id, MessageRequest messageRequest) {
        return messageRepository.findById(id).map(message -> {
            message.setMessage(messageRequest.getMessage());
            message.setMessageType(messageRequest.getMessageType());
            message.setAttachment(messageRequest.getAttachment());
            return messageMapper.toResponseDTO(messageRepository.save(message));
        }).orElseThrow(() -> new RuntimeException("Message not found"));
    }

    @Override
    public void deleteMessage(Integer id) {
        messageRepository.deleteById(id);
    }
}
