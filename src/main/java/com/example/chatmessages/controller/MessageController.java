package com.example.chatmessages.controller;

import com.example.chatmessages.dto.ApiResponse;
import com.example.chatmessages.dto.request.MessageRequest;
import com.example.chatmessages.dto.response.MessageResponse;
import com.example.chatmessages.service.MessageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MessageController {

    MessageService messageService;

    @PostMapping
    public ApiResponse<MessageResponse> createMessage(@RequestBody MessageRequest request) {
        MessageResponse createdMessage = messageService.createMessage(request);
        return new ApiResponse<>(HttpStatus.CREATED.value(), "Message created successfully", createdMessage);
    }

    @GetMapping
    public ApiResponse<List<MessageResponse>> getAllMessages() {
        List<MessageResponse> messages = messageService.getAllMessages();
        return new ApiResponse<>(HttpStatus.OK.value(), "Messages retrieved successfully", messages);
    }

    @GetMapping("/{id}")
    public ApiResponse<MessageResponse> getMessageById(@PathVariable Integer id) {
        MessageResponse message = messageService.getMessageById(id);
        return new ApiResponse<>(HttpStatus.OK.value(), "Message retrieved successfully", message);
    }

    @PutMapping("/{id}")
    public ApiResponse<MessageResponse> updateMessage(@PathVariable Integer id, @RequestBody MessageRequest request) {
        MessageResponse updatedMessage = messageService.updateMessage(id, request);
        return new ApiResponse<>(HttpStatus.OK.value(), "Message updated successfully", updatedMessage);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteMessage(@PathVariable Integer id) {
        messageService.deleteMessage(id);
        return new ApiResponse<>(HttpStatus.NO_CONTENT.value(), "Message deleted successfully", null);
    }
}
