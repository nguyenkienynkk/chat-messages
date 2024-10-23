package com.example.chatmessages.service;

import com.example.chatmessages.common.PageResponse;
import com.example.chatmessages.dto.request.LoginRequest;
import com.example.chatmessages.dto.request.UserFullRequest;
import com.example.chatmessages.dto.request.UserRequest;
import com.example.chatmessages.dto.response.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse createUser(UserRequest requestDTO);

    UserResponse updateUser(Integer id, UserFullRequest requestDTO);

    UserResponse login(LoginRequest loginRequest);

    UserResponse getUserById(Integer id);

    List<UserResponse> searchUsersByUsername(String username);

    PageResponse<List<UserResponse>> getAllUsers(int pageNo, int pageSize);

    void deleteUser(Integer id);
}
