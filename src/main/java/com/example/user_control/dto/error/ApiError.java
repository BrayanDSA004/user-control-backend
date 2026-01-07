package com.example.user_control.dto.error;

import java.time.LocalDateTime;
import java.util.Map;

public record ApiError(
        int status,
        String message,
        Map<String, String> errors,
        LocalDateTime timestamp
) {}