package com.example.talky.domain.user.web.controller;

import com.example.talky.domain.user.service.UserService;
import com.example.talky.global.response.SuccessResponse;
import com.example.talky.global.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/me")
@RequiredArgsConstructor
public class UserController {
    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<SuccessResponse<?>> getProfile(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Object profile = userService.getUserProfile(userDetails.getUser().getId());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(SuccessResponse.ok(profile));
    }
}
