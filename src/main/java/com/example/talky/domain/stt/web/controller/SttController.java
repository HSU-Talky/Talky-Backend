package com.example.talky.domain.stt.web.controller;

import com.example.talky.domain.stt.service.SttService;
import com.example.talky.global.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/stt")
@RequiredArgsConstructor
public class SttController {

    private final SttService sttService;

    @PostMapping
    public ResponseEntity<SuccessResponse<?>> convertSpeechToText(@RequestParam("file") MultipartFile file) {

        String transcribe = sttService.transcribe(file);

        return ResponseEntity.ok(SuccessResponse.ok(transcribe));
    }
}
