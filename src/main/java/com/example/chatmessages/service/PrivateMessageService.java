package com.example.chatmessages.service;

import com.example.chatmessages.dto.request.PrivateMessageRequest;
import com.example.chatmessages.dto.response.PrivateMessageResponse;
import com.example.chatmessages.entity.PrivateMessage;
import com.example.chatmessages.entity.User;
import com.example.chatmessages.mapper.PrivateMessageMapper;
import com.example.chatmessages.repository.PrivateMessageRepository;
import com.example.chatmessages.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class PrivateMessageService {

    private final PrivateMessageRepository privateMessageRepository;
    private final PrivateMessageMapper privateMessageMapper;
    private final UserRepository userRepository;

    public PrivateMessageResponse createPrivateMessage(PrivateMessageRequest privateMessageRequest) {
        // Fetch the sender and receiver from the database using IDs
        User sender = userRepository.findById(privateMessageRequest.getSenderId())
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        User receiver = userRepository.findById(privateMessageRequest.getReceiverId())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        // Create a new PrivateMessage entity using the mapper
        PrivateMessage privateMessage = privateMessageMapper.toEntity(privateMessageRequest);
        privateMessage.setSender(sender);  // Set sender
        privateMessage.setReceiver(receiver);  // Set receiver

        // Save the PrivateMessage entity to the database
        PrivateMessage savedMessage = privateMessageRepository.save(privateMessage);

        // Convert to PrivateMessageResponse DTO using the mapper
        return privateMessageMapper.toResponseDTO(savedMessage);
    }

    public List<PrivateMessageResponse> getAllPrivateMessages() {
        return privateMessageRepository.findAll().stream()
                .map(privateMessageMapper::toResponseDTO)
                .toList();
    }

    public PrivateMessageResponse getPrivateMessageById(Integer id) {
        return privateMessageRepository.findById(id)
                .map(privateMessageMapper::toResponseDTO)
                .orElseThrow(() -> new RuntimeException("PrivateMessage not found"));
    }

    public PrivateMessageResponse updatePrivateMessage(Integer id, PrivateMessageRequest requestDTO) {
        return privateMessageRepository.findById(id).map(privateMessage -> {
            privateMessageMapper.toEntity(requestDTO);
            var updatedMessage = privateMessageRepository.save(privateMessage);
            return privateMessageMapper.toResponseDTO(updatedMessage);
        }).orElseThrow(() -> new RuntimeException("PrivateMessage not found"));
    }

    public void deletePrivateMessage(Integer id) {
        privateMessageRepository.deleteById(id);
    }
}
