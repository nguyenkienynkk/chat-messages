package com.example.chatmessages.service.impl;

import com.example.chatmessages.common.PageResponse;
import com.example.chatmessages.constant.ErrorCode;
import com.example.chatmessages.dto.request.LoginRequest;
import com.example.chatmessages.dto.request.UserFullRequest;
import com.example.chatmessages.dto.request.UserRequest;
import com.example.chatmessages.dto.response.UserResponse;
import com.example.chatmessages.entity.User;
import com.example.chatmessages.enums.SortType;
import com.example.chatmessages.exception.AppException;
import com.example.chatmessages.exception.NotExistsException;
import com.example.chatmessages.exception.NotFoundException;
import com.example.chatmessages.mapper.UserMapper;
import com.example.chatmessages.repository.UserRepository;
import com.example.chatmessages.service.UserService;
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
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    UserMapper userMapper;

    @Override
    public UserResponse createUser(UserRequest requestDTO) {
        if (userRepository.existsByEmailAndIdNot(requestDTO.getEmail(), null))
            throw new NotExistsException(ErrorCode.EMAIL_ALREADY_EXISTS.getMessage());
        User user = userMapper.toEntity(requestDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepository.save(user);
        return userMapper.toResponseDTO(user);
    }

    @Override
    public UserResponse updateUser(Integer id, UserFullRequest requestDTO) {
        Optional<User> existingUserOptional = userRepository.findById(id);

        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();

            if (requestDTO.getEmail() != null && !requestDTO.getEmail().equals(existingUser.getEmail())) {
                if (userRepository.existsByEmailAndIdNot(requestDTO.getEmail(), id))
                    throw new AppException(ErrorCode.EMAIL_ALREADY_EXISTS);
            }
            userMapper.updateUserFromDTO(requestDTO, existingUser);
            if (requestDTO.getPassword() != null && !requestDTO.getPassword().isEmpty())
                existingUser.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
            existingUser = userRepository.save(existingUser);
            return userMapper.toResponseDTO(existingUser);
        }
        throw new NotFoundException(ErrorCode.USER_NOT_FOUND.getMessage());
    }

    @Override
    public UserResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND.getMessage()));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()))
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        return userMapper.toResponseDTO(user);
    }

    @Override
    public UserResponse getUserById(Integer id) {
        return userRepository.findById(id)
                .map(userMapper::toResponseDTO)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND.getMessage()));
    }

    @Override
    public List<UserResponse> searchUsersByUsername(String username) {
        List<User> users = userRepository.findByUsernameContainingIgnoreCase(username);
        return users.stream()
                .map(userMapper::toResponseDTO)
                .toList();
    }

    @Override
    public PageResponse<List<UserResponse>> getAllUsers(int pageNo, int pageSize) {
        Pageable pageable = PageUtils.createPageable(pageNo, pageSize, "username", SortType.ASC.getValue());
        Page<User> userPage = userRepository.findAll(pageable);

        List<UserResponse> responseList = userPage.getContent().stream()
                .map(userMapper::toResponseDTO)
                .toList();

        return PageResponse.<List<UserResponse>>builder()
                .page(userPage.getNumber())
                .size(userPage.getSize())
                .totalPage(userPage.getTotalPages())
                .items(responseList)
                .build();
    }

    @Override
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
}
