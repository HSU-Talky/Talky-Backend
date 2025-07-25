package com.example.talky.domain.user.web.controller;

import com.example.talky.domain.user.service.UserService;
import com.example.talky.domain.user.web.dto.TtsUpdateReq;
import com.example.talky.domain.user.web.dto.UsernameUpdateReq;
import com.example.talky.global.response.SuccessResponse;
import com.example.talky.global.security.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/me")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<SuccessResponse<?>> getProfile(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Object profile = userService.getUserProfile(userDetails.getUser().getId());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(SuccessResponse.ok(profile));
    }

    // 사용자 이름 수정
    @PutMapping("/username")
    public ResponseEntity<SuccessResponse<?>> updateUserName(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                             @Valid @RequestBody UsernameUpdateReq usernameUpdateReq) {
        userService.updateUsername(userDetails.getUser().getId(), usernameUpdateReq);
        return ResponseEntity
                .ok(SuccessResponse.empty());

    }

    @PutMapping("/tts")
    public ResponseEntity<SuccessResponse<?>> updateTts(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                        @RequestBody @Valid TtsUpdateReq ttsUpdateReq) {
        userService.updateTts(userDetails.getUser().getId(), ttsUpdateReq);
        return ResponseEntity
                .ok(SuccessResponse.empty());
    }
}
