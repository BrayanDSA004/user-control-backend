package com.example.user_control.dto.response;

public record AuthResponse(
        String token,
        String username,
        String role
) {}