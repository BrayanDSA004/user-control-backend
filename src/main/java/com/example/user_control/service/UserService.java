package com.example.user_control.service;

import com.example.user_control.dto.request.UserCreateRequest;
import com.example.user_control.dto.request.UserUpdateRequest;
import com.example.user_control.dto.response.UserResponse;
import com.example.user_control.entity.Role;
import com.example.user_control.entity.User;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserCreateRequest request);

    UserResponse getUserById(Long id);

    List<UserResponse> getAllUsers();

    UserResponse updateUser(Long id, UserUpdateRequest request);

    void deleteUser(long id);

    UserResponse changeRole(Long userId, Role role);
}
