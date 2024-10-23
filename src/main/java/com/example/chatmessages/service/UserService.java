package com.example.chatmessages.service;

import com.example.chatmessages.dto.request.LoginRequestDTO;
import com.example.chatmessages.dto.request.UserFullRequest;
import com.example.chatmessages.dto.request.UserRequestDTO;
import com.example.chatmessages.dto.response.UserResponseDTO;
import com.example.chatmessages.entity.User;
import com.example.chatmessages.mapper.UserMapper;
import com.example.chatmessages.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    UserMapper userMapper;

    // Tạo mới người dùng
    public UserResponseDTO createUser(UserRequestDTO requestDTO) {
        User user = userMapper.toEntity(requestDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepository.save(user);
        return userMapper.toResponseDTO(user);
    }
    public UserResponseDTO updateUser(Integer id, UserFullRequest requestDTO) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            userMapper.updateUserFromDTO(requestDTO, user);
            if (requestDTO.getPassword() != null && !requestDTO.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
            }
            user = userRepository.save(user);
            return userMapper.toResponseDTO(user);
        }
        throw new RuntimeException("User not found");
    }
    public UserResponseDTO login(LoginRequestDTO loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return userMapper.toResponseDTO(user);
    }


    // Lấy thông tin người dùng theo ID
    public UserResponseDTO getUserById(Integer id) {
        return userRepository.findById(id)
                .map(userMapper::toResponseDTO)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Lấy tất cả người dùng
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // Xoá người dùng
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
}
