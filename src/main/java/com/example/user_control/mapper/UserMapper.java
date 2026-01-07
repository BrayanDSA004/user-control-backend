package com.example.user_control.mapper;

import com.example.user_control.dto.request.RegisterRequest;
import com.example.user_control.dto.request.UserCreateRequest;
import com.example.user_control.dto.response.UserResponse;
import com.example.user_control.entity.Role;
import com.example.user_control.entity.User;

public class UserMapper {

    public static UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole().name(),
                user.getDeletedAt() == null
        );
    }

    public static User fromRegister(RegisterRequest request) {
        User user = new User();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPassword(request.password());
        user.setRole(Role.USER);
        return user;
    }

    public static User fromUserCreate(UserCreateRequest request) {
        User user = new User();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPassword(request.password());
        user.setRole(
                request.role() != null
                        ? Role.valueOf(request.role().replace("ROLE_", ""))
                        : Role.USER
        );
        return user;
    }
}
