package com.example.chatmessages.service;

import com.example.chatmessages.common.PageResponse;
import com.example.chatmessages.constant.ErrorCode;
import com.example.chatmessages.dto.request.PrivateMessageRequest;
import com.example.chatmessages.dto.response.PrivateMessageResponse;
import com.example.chatmessages.entity.PrivateMessage;
import com.example.chatmessages.entity.User;
import com.example.chatmessages.enums.SortType;
import com.example.chatmessages.exception.AppException;
import com.example.chatmessages.exception.NotFoundException;
import com.example.chatmessages.mapper.PrivateMessageMapper;
import com.example.chatmessages.repository.PrivateMessageRepository;
import com.example.chatmessages.repository.UserRepository;
import com.example.chatmessages.utils.PageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class PrivateMessageService {

    private final PrivateMessageRepository privateMessageRepository;
    private final PrivateMessageMapper privateMessageMapper;
    private final UserRepository userRepository;

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


    public PrivateMessageResponse getPrivateMessageById(Integer id) {
        return privateMessageRepository.findById(id)
                .map(privateMessageMapper::toResponseDTO)
                .orElseThrow(() -> new NotFoundException(ErrorCode.PRIVATE_MESSAGE_NOT_FOUND.getMessage()));
    }

    public PrivateMessageResponse updatePrivateMessage(Integer id, PrivateMessageRequest requestDTO) {
        return privateMessageRepository.findById(id).map(privateMessage -> {
            privateMessageMapper.toEntity(requestDTO);
            var updatedMessage = privateMessageRepository.save(privateMessage);
            return privateMessageMapper.toResponseDTO(updatedMessage);
        }).orElseThrow(() -> new NotFoundException(ErrorCode.PRIVATE_MESSAGE_NOT_FOUND.getMessage()));
    }

    public void deletePrivateMessage(Integer id) {
        privateMessageRepository.deleteById(id);
    }
}
