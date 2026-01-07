package com.example.user_control.service;

import com.example.user_control.dto.request.UserCreateRequest;
import com.example.user_control.dto.request.UserUpdateRequest;
import com.example.user_control.dto.response.UserResponse;
import com.example.user_control.entity.Role;
import com.example.user_control.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserCreateRequest request);

    UserResponse getUserById(Long id);

    Page<UserResponse> getAllUsers(Pageable pageable);

    UserResponse updateUser(Long id, UserUpdateRequest request);

    void deleteUser(long id);

    UserResponse changeRole(Long userId, Role role);
}
