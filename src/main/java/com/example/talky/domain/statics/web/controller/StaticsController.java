package com.example.talky.domain.statics.web.controller;

import com.example.talky.domain.statics.service.StaticsService;
import com.example.talky.domain.statics.web.dto.StaticsRes;
import com.example.talky.global.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/guardians/me/statics")
@RequiredArgsConstructor
public class StaticsController {

    private final StaticsService staticsService;

    @GetMapping("/{normalId}")
    public ResponseEntity<SuccessResponse<?>> getNormalUsersStatics(
            @PathVariable Long normalId
    ) {
        StaticsRes response = staticsService.getNormalUsersStatics(normalId);
        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.ok(response));

    }
}
