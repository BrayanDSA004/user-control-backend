package com.example.user_control.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;

public record RegisterRequest(
        @NotBlank String username,
        @Email String email,
        @NotBlank String password
) {}