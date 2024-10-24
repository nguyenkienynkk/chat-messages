package com.example.chatmessages.service.impl;

import com.example.chatmessages.common.PageResponse;
import com.example.chatmessages.constant.ErrorCode;
import com.example.chatmessages.dto.request.PrivateMessageRequest;
import com.example.chatmessages.dto.response.ChatPartnerResponse;
import com.example.chatmessages.dto.response.MessageSenderAndReceiveResponse;
import com.example.chatmessages.dto.response.PrivateMessageResponse;
import com.example.chatmessages.dto.response.RoomResponse;
import com.example.chatmessages.dto.response.UserResponse;
import com.example.chatmessages.entity.PrivateMessage;
import com.example.chatmessages.entity.Room;
import com.example.chatmessages.entity.User;
import com.example.chatmessages.enums.SortType;
import com.example.chatmessages.exception.NotFoundException;
import com.example.chatmessages.mapper.PrivateMessageMapper;
import com.example.chatmessages.mapper.RoomMapper;
import com.example.chatmessages.mapper.UserMapper;
import com.example.chatmessages.repository.PrivateMessageRepository;
import com.example.chatmessages.repository.RoomMemberRepository;
import com.example.chatmessages.repository.UserRepository;
import com.example.chatmessages.service.PrivateMessageService;
import com.example.chatmessages.utils.PageUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PrivateMessageServiceImpl implements PrivateMessageService {

    PrivateMessageRepository privateMessageRepository;
    PrivateMessageMapper privateMessageMapper;
    UserRepository userRepository;
    UserMapper userMapper;
    RoomMemberRepository roomMemberRepository;
    RoomMapper roomMapper;

    @Override
    public PrivateMessageResponse createPrivateMessage(PrivateMessageRequest privateMessageRequest) {
        User sender = userRepository.findById(privateMessageRequest.getSenderId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.SENDER_NOT_FOUND.getMessage()));
        User receiver = userRepository.findById(privateMessageRequest.getReceiverId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.RECEIVE_NOT_FOUND.getMessage()));

        PrivateMessage privateMessage = privateMessageMapper.toEntity(privateMessageRequest);
        privateMessage.setSender(sender);
        privateMessage.setReceiver(receiver);

        PrivateMessage savedMessage = privateMessageRepository.save(privateMessage);

        return privateMessageMapper.toResponseDTO(savedMessage);
    }

    @Override
    public PageResponse<List<PrivateMessageResponse>> getAllPrivateMessages(int pageNo, int pageSize) {
        Pageable pageable = PageUtils.createPageable(pageNo, pageSize, "sentAt", SortType.DESC.getValue());
        Page<PrivateMessage> messagePage = privateMessageRepository.findAll(pageable);

        List<PrivateMessageResponse> responseList = messagePage.getContent().stream()
                .map(privateMessageMapper::toResponseDTO)
                .toList();

        return PageResponse.<List<PrivateMessageResponse>>builder()
                .page(messagePage.getNumber())
                .size(messagePage.getSize())
                .totalPage(messagePage.getTotalPages())
                .items(responseList)
                .build();
    }

    @Override
    public PrivateMessageResponse getPrivateMessageById(Integer id) {
        return privateMessageRepository.findById(id)
                .map(privateMessageMapper::toResponseDTO)
                .orElseThrow(() -> new NotFoundException(ErrorCode.PRIVATE_MESSAGE_NOT_FOUND.getMessage()));
    }
    @Override
    public PageResponse<List<MessageSenderAndReceiveResponse>> getMessagesBySenderAndReceiver(
            Integer senderId, Integer receiverId, int pageNo, int pageSize) {

        Pageable pageable = PageUtils.createPageable(pageNo, pageSize, "sentAt", SortType.ASC.getValue());

        Page<PrivateMessage> messages = privateMessageRepository.findMessagesBetween(senderId, receiverId, pageable);

        List<MessageSenderAndReceiveResponse> messageResponses = messages.getContent().stream()
                .map(privateMessageMapper::toSenderAndReceive)
                .toList();

        return PageResponse.<List<MessageSenderAndReceiveResponse>>builder()
                .page(pageNo)
                .size(pageSize)
                .totalPage(messages.getTotalPages())
                .items(messageResponses)
                .build();
    }

    @Override
    public PrivateMessageResponse updatePrivateMessage(Integer id, PrivateMessageRequest requestDTO) {
        return privateMessageRepository.findById(id).map(privateMessage -> {
            privateMessageMapper.toEntity(requestDTO);
            var updatedMessage = privateMessageRepository.save(privateMessage);
            return privateMessageMapper.toResponseDTO(updatedMessage);
        }).orElseThrow(() -> new NotFoundException(ErrorCode.PRIVATE_MESSAGE_NOT_FOUND.getMessage()));
    }
    @Override
    public void deletePrivateMessage(Integer id) {
        privateMessageRepository.deleteById(id);
    }
    @Override
    public ChatPartnerResponse getChatPartnersAndRooms(Integer userId) {
        List<Room> joinedRooms = roomMemberRepository.findRoomsByUserId(userId);
        List<User> chatPartners = privateMessageRepository.findChatPartnersByUserId(userId);
        List<RoomResponse> roomResponses = joinedRooms.stream()
                .map(roomMapper::toResponseDTO)
                .toList();
        List<UserResponse> userResponses = chatPartners.stream()
                .map(userMapper::toResponseDTO)
                .toList();
        return new ChatPartnerResponse(userResponses, roomResponses);
    }
}