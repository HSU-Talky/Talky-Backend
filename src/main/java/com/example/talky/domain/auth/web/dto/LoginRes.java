package com.example.talky.domain.auth.web.dto;

public record LoginRes(
        Long userId,
        String userType,
        String username,
        String token
) {
}
