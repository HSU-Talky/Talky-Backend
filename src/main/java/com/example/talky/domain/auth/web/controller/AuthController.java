package com.example.talky.domain.auth.web.controller;

import com.example.talky.domain.auth.service.AuthService;
import com.example.talky.domain.auth.web.dto.SignUpReq;
import com.example.talky.domain.auth.web.dto.SignUpRes;
import com.example.talky.global.response.SuccessResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signUp")
    public ResponseEntity<SuccessResponse<?>> signUp(@RequestBody @Valid SignUpReq signUpReq) {
        SignUpRes signUpRes = authService.signUp(signUpReq);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(SuccessResponse.created(signUpRes));

    }
}