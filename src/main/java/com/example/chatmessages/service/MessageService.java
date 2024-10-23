package com.example.chatmessages.service;

import com.example.chatmessages.dto.request.MessageRequest;
import com.example.chatmessages.dto.response.MessageResponse;
import com.example.chatmessages.entity.Message;
import com.example.chatmessages.entity.Room;
import com.example.chatmessages.entity.User;
import com.example.chatmessages.mapper.MessageMapper;
import com.example.chatmessages.repository.MessageRepository;
import com.example.chatmessages.repository.RoomRepository;
import com.example.chatmessages.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    public MessageResponse createMessage(MessageRequest messageRequest) {
        // Tìm kiếm room và sender từ cơ sở dữ liệu
        Room room = roomRepository.findById(messageRequest.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found"));

        User sender = userRepository.findById(messageRequest.getSenderId())
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        // Tạo đối tượng Message từ yêu cầu
        Message message = messageMapper.toEntity(messageRequest);

        // Thiết lập các thuộc tính
        message.setRoom(room);
        message.setSender(sender);
        message.setSentAt(Instant.now()); // Thiết lập thời gian gửi

        // Lưu tin nhắn
        Message savedMessage = messageRepository.save(message);

        // Trả về phản hồi
        return messageMapper.toResponseDTO(savedMessage);
    }


    public List<MessageResponse> getAllMessages() {
        return messageRepository.findAll().stream()
                .map(messageMapper::toResponseDTO)
                .toList();
    }

    public MessageResponse getMessageById(Integer id) {
        return messageRepository.findById(id)
                .map(messageMapper::toResponseDTO)
                .orElseThrow(() -> new RuntimeException("Message not found"));
    }

    public MessageResponse updateMessage(Integer id, MessageRequest messageRequest) {
        return messageRepository.findById(id).map(message -> {
            message.setMessage(messageRequest.getMessage());
            message.setMessageType(messageRequest.getMessageType());
            message.setAttachment(messageRequest.getAttachment());
            return messageMapper.toResponseDTO(messageRepository.save(message));
        }).orElseThrow(() -> new RuntimeException("Message not found"));
    }

    public void deleteMessage(Integer id) {
        messageRepository.deleteById(id);
    }
}
