package com.example.chatmessages.controller;

import com.example.chatmessages.common.PageResponse;
import com.example.chatmessages.dto.ApiResponse;
import com.example.chatmessages.dto.request.LoginRequest;
import com.example.chatmessages.dto.request.UserFullRequest;
import com.example.chatmessages.dto.request.UserRequest;
import com.example.chatmessages.dto.response.UserResponse;
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

    @PostMapping
    public ApiResponse<UserResponse> createUser(@RequestBody UserRequest userRequest) {
        UserResponse createdUser = userService.createUser(userRequest);
        return new ApiResponse<>(HttpStatus.CREATED.value(), "User created successfully", createdUser);
    }

    @GetMapping
    public ApiResponse<PageResponse<List<UserResponse>>> getAllUsers(
            @RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") int pageSize) {

        PageResponse<List<UserResponse>> response = userService.getAllUsers(pageNo, pageSize);
        return new ApiResponse<>(HttpStatus.OK.value(), "Users retrieved successfully", response);
    }

    @GetMapping("/search")
    public ApiResponse<List<UserResponse>> searchUsers(
            @RequestParam(value = "username") String username) {
        List<UserResponse> users = userService.searchUsersByUsername(username);
        return new ApiResponse<>(HttpStatus.OK.value(), "Users retrieved successfully", users);
    }


    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getUserById(@PathVariable Integer id) {
        UserResponse userResponse = userService.getUserById(id);
        return new ApiResponse<>(HttpStatus.OK.value(), "User retrieved successfully", userResponse);
    }

    @PutMapping("/{id}")
    public ApiResponse<UserResponse> updateUser(@PathVariable Integer id,
                                                @RequestBody UserFullRequest userRequestDTO) {
        UserResponse updatedUser = userService.updateUser(id, userRequestDTO);
        return new ApiResponse<>(HttpStatus.OK.value(), "User updated successfully", updatedUser);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return new ApiResponse<>(HttpStatus.NO_CONTENT.value(), "User deleted successfully", null);
    }
    @PostMapping("/login")
    public ApiResponse<UserResponse> login(@RequestBody LoginRequest loginRequest) {
        UserResponse user = userService.login(loginRequest);
        return new ApiResponse<>(HttpStatus.OK.value(), "Login successful", user);
    }
}
