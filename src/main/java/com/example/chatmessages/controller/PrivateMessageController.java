package com.example.chatmessages.controller;

import com.example.chatmessages.common.PageResponse;
import com.example.chatmessages.dto.ApiResponse;
import com.example.chatmessages.dto.request.PrivateMessageRequest;
import com.example.chatmessages.dto.response.PrivateMessageResponse;
import com.example.chatmessages.service.PrivateMessageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/private-messages")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PrivateMessageController {

    PrivateMessageService privateMessageService;

    @PostMapping
    public ApiResponse<PrivateMessageResponse> createPrivateMessage(@RequestBody PrivateMessageRequest requestDTO) {
        PrivateMessageResponse createdMessage = privateMessageService.createPrivateMessage(requestDTO);
        return new ApiResponse<>(HttpStatus.CREATED.value(), "Private message created successfully", createdMessage);
    }

    // Lấy tất cả tin nhắn riêng tư
    @GetMapping
    public ApiResponse<PageResponse<List<PrivateMessageResponse>>> getAllPrivateMessages(
            @RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") int pageSize) {

        PageResponse<List<PrivateMessageResponse>> response = privateMessageService.getAllPrivateMessages(pageNo, pageSize);
        return new ApiResponse<>(HttpStatus.OK.value(), "Private messages retrieved successfully", response);
    }
    @GetMapping("/between/{senderId}/{receiverId}")
    public ApiResponse<List<PrivateMessageResponse>> getMessagesBetween(
            @PathVariable Integer senderId,
            @PathVariable Integer receiverId) {
        List<PrivateMessageResponse> messages = privateMessageService.getMessagesBySenderAndReceiver(senderId, receiverId);
        return new ApiResponse<>(HttpStatus.OK.value(), "Messages retrieved successfully", messages);
    }
    // Lấy tin nhắn riêng tư theo ID
    @GetMapping("/{id}")
    public ApiResponse<PrivateMessageResponse> getPrivateMessageById(@PathVariable Integer id) {
        PrivateMessageResponse message = privateMessageService.getPrivateMessageById(id);
        return new ApiResponse<>(HttpStatus.OK.value(), "Private message retrieved successfully", message);
    }

    // Cập nhật tin nhắn riêng tư
    @PutMapping("/{id}")
    public ApiResponse<PrivateMessageResponse> updatePrivateMessage(@PathVariable Integer id,
                                                                       @RequestBody PrivateMessageRequest requestDTO) {
        PrivateMessageResponse updatedMessage = privateMessageService.updatePrivateMessage(id, requestDTO);
        return new ApiResponse<>(HttpStatus.OK.value(), "Private message updated successfully", updatedMessage);
    }

    // Xoá tin nhắn riêng tư
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deletePrivateMessage(@PathVariable Integer id) {
        privateMessageService.deletePrivateMessage(id);
        return new ApiResponse<>(HttpStatus.NO_CONTENT.value(), "Private message deleted successfully", null);
    }
}
