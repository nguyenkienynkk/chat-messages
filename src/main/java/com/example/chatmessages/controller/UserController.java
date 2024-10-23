package com.example.chatmessages.controller;

import com.example.chatmessages.dto.ApiResponse;
import com.example.chatmessages.dto.request.LoginRequestDTO;
import com.example.chatmessages.dto.request.UserFullRequest;
import com.example.chatmessages.dto.request.UserRequestDTO;
import com.example.chatmessages.dto.response.UserResponseDTO;
import com.example.chatmessages.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;

    // Tạo mới người dùng
    @PostMapping
    public ApiResponse<UserResponseDTO> createUser(@RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO createdUser = userService.createUser(userRequestDTO);
        return new ApiResponse<>(HttpStatus.CREATED.value(), "User created successfully", createdUser);
    }

    // Lấy tất cả người dùng
    @GetMapping
    public ApiResponse<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userService.getAllUsers();
        return new ApiResponse<>(HttpStatus.OK.value(), "Users retrieved successfully", users);
    }

    // Lấy thông tin người dùng theo ID
    @GetMapping("/{id}")
    public ApiResponse<UserResponseDTO> getUserById(@PathVariable Integer id) {
        UserResponseDTO userResponseDTO = userService.getUserById(id);
        return new ApiResponse<>(HttpStatus.OK.value(), "User retrieved successfully", userResponseDTO);
    }

    // Cập nhật người dùng
    @PutMapping("/{id}")
    public ApiResponse<UserResponseDTO> updateUser(@PathVariable Integer id,
                                                   @RequestBody UserFullRequest userRequestDTO) {
        UserResponseDTO updatedUser = userService.updateUser(id, userRequestDTO);
        return new ApiResponse<>(HttpStatus.OK.value(), "User updated successfully", updatedUser);
    }

    // Xoá người dùng
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return new ApiResponse<>(HttpStatus.NO_CONTENT.value(), "User deleted successfully", null);
    }
    @PostMapping("/login")
    public ApiResponse<UserResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        UserResponseDTO user = userService.login(loginRequest);
        return new ApiResponse<>(HttpStatus.OK.value(), "Login successful", user);
    }
}
