package com.example.user_control.dto.response;

public record UserResponse(
        Long id,
        String username,
        String email,
        String role,
        boolean active
) {}