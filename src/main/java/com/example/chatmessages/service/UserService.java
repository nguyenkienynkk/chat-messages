package com.example.chatmessages.service;

import com.example.chatmessages.common.PageResponse;
import com.example.chatmessages.constant.ErrorCode;
import com.example.chatmessages.dto.request.LoginRequestDTO;
import com.example.chatmessages.dto.request.UserFullRequest;
import com.example.chatmessages.dto.request.UserRequestDTO;
import com.example.chatmessages.dto.response.UserResponseDTO;
import com.example.chatmessages.entity.User;
import com.example.chatmessages.enums.SortType;
import com.example.chatmessages.exception.AppException;
import com.example.chatmessages.exception.NotExistsException;
import com.example.chatmessages.exception.NotFoundException;
import com.example.chatmessages.mapper.UserMapper;
import com.example.chatmessages.repository.UserRepository;
import com.example.chatmessages.utils.PageUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    UserMapper userMapper;

    public UserResponseDTO createUser(UserRequestDTO requestDTO) {
        if (userRepository.existsByEmailAndIdNot(requestDTO.getEmail(), null))
            throw new NotExistsException(ErrorCode.EMAIL_ALREADY_EXISTS.getMessage());

        User user = userMapper.toEntity(requestDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepository.save(user);
        return userMapper.toResponseDTO(user);
    }

    public UserResponseDTO updateUser(Integer id, UserFullRequest requestDTO) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            if (requestDTO.getEmail() != null && !requestDTO.getEmail().equals(existingUser.get().getEmail())) {
                if (userRepository.existsByEmailAndIdNot(requestDTO.getEmail(), id)) {
                    throw new AppException(ErrorCode.EMAIL_ALREADY_EXISTS);
                }
            }

            User user = existingUser.get();
            userMapper.updateUserFromDTO(requestDTO, user);
            if (requestDTO.getPassword() != null && !requestDTO.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
            }
            user = userRepository.save(user);
            return userMapper.toResponseDTO(user);
        }
        throw new NotFoundException(ErrorCode.USER_NOT_FOUND.getMessage());
    }

    public UserResponseDTO login(LoginRequestDTO loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND.getMessage()));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        return userMapper.toResponseDTO(user);
    }


    public UserResponseDTO getUserById(Integer id) {
        return userRepository.findById(id)
                .map(userMapper::toResponseDTO)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND.getMessage()));
    }

    public PageResponse<List<UserResponseDTO>> getAllUsers(int pageNo, int pageSize) {
        Pageable pageable = PageUtils.createPageable(pageNo, pageSize, "username", SortType.ASC.getValue());
        Page<User> userPage = userRepository.findAll(pageable);

        List<UserResponseDTO> responseList = userPage.getContent().stream()
                .map(userMapper::toResponseDTO)
                .toList();

        return PageResponse.<List<UserResponseDTO>>builder()
                .page(userPage.getNumber())
                .size(userPage.getSize())
                .totalPage(userPage.getTotalPages())
                .items(responseList)
                .build();
    }
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
}
