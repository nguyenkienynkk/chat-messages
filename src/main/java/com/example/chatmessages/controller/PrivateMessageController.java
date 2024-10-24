package com.example.chatmessages.controller;

import com.example.chatmessages.common.PageResponse;
import com.example.chatmessages.dto.ApiResponse;
import com.example.chatmessages.dto.request.PrivateMessageRequest;
import com.example.chatmessages.dto.response.ChatPartnerResponse;
import com.example.chatmessages.dto.response.PrivateMessageResponse;
import com.example.chatmessages.dto.response.UserResponse;
import com.example.chatmessages.service.PrivateMessageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
@RestController
@RequestMapping("/private-messages")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PrivateMessageController {

    PrivateMessageService privateMessageService;

    @Operation(summary = "Create a new private message")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Private message created successfully")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input data")
    @PostMapping
    public ApiResponse<PrivateMessageResponse> createPrivateMessage(@RequestBody PrivateMessageRequest requestDTO) {
        PrivateMessageResponse createdMessage = privateMessageService.createPrivateMessage(requestDTO);
        return new ApiResponse<>(HttpStatus.CREATED.value(), "Private message created successfully", createdMessage);
    }

    @Operation(summary = "Retrieve all private messages with pagination")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Private messages retrieved successfully")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid pagination parameters")
    @GetMapping
    public ApiResponse<PageResponse<List<PrivateMessageResponse>>> getAllPrivateMessages(
            @RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") int pageSize) {

        PageResponse<List<PrivateMessageResponse>> response = privateMessageService.getAllPrivateMessages(pageNo, pageSize);
        return new ApiResponse<>(HttpStatus.OK.value(), "Private messages retrieved successfully", response);
    }

    @Operation(summary = "Get private messages between a specific sender and receiver with pagination")

    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Messages retrieved successfully")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Messages not found between sender and receiver")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid request parameters")
    @GetMapping("/between/{senderId}/{receiverId}")
    public ApiResponse<PageResponse<List<PrivateMessageResponse>>> getMessagesBetween(
            @PathVariable Integer senderId,
            @PathVariable Integer receiverId,
            @RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") int pageSize) {

        PageResponse<List<PrivateMessageResponse>> messages = privateMessageService.getMessagesBySenderAndReceiver(senderId, receiverId, pageNo, pageSize);
        return new ApiResponse<>(HttpStatus.OK.value(), "Messages retrieved successfully", messages);
    }

    @Operation(summary = "Retrieve a private message by its ID")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Private message retrieved successfully")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Private message not found")
    @GetMapping("/{id}")
    public ApiResponse<PrivateMessageResponse> getPrivateMessageById(@PathVariable Integer id) {
        PrivateMessageResponse message = privateMessageService.getPrivateMessageById(id);
        return new ApiResponse<>(HttpStatus.OK.value(), "Private message retrieved successfully", message);
    }

    @Operation(summary = "Update a private message by its ID")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Private message updated successfully")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Private message not found")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input data")
    @PutMapping("/{id}")
    public ApiResponse<PrivateMessageResponse> updatePrivateMessage(@PathVariable Integer id,
                                                                    @RequestBody PrivateMessageRequest requestDTO) {
        PrivateMessageResponse updatedMessage = privateMessageService.updatePrivateMessage(id, requestDTO);
        return new ApiResponse<>(HttpStatus.OK.value(), "Private message updated successfully", updatedMessage);
    }

    @Operation(summary = "Delete a private message by its ID")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Private message deleted successfully")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Private message not found")
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deletePrivateMessage(@PathVariable Integer id) {
        privateMessageService.deletePrivateMessage(id);
        return new ApiResponse<>(HttpStatus.NO_CONTENT.value(), "Private message deleted successfully", null);
    }

    @Operation(summary = "Retrieve chat partners for a specific user")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Chat partners retrieved successfully")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "User not found")
    @GetMapping("/chat-partners/{userId}")
    public ApiResponse<ChatPartnerResponse> getChatPartners(@PathVariable Integer userId) {
        ChatPartnerResponse response = privateMessageService.getChatPartnersAndRooms(userId);
        return new ApiResponse<>(HttpStatus.OK.value(), "Chat partners and rooms retrieved successfully", response);
    }
}
