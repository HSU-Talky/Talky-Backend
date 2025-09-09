package com.example.talky.domain.recommendation.web.controller;

import com.example.talky.domain.recommendation.service.RcmdService;
import com.example.talky.global.ai.dto.AiRcmdRes;
import com.example.talky.global.response.SuccessResponse;
import com.example.talky.global.security.CustomUserDetails;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/recommendations")
@RequiredArgsConstructor
public class RcmdController {

    private final RcmdService rcmdService;

    @PostMapping("/context")
    public ResponseEntity<SuccessResponse<?>> getAiSentence(
            @RequestParam(value = "file", required = false) MultipartFile file, // 사용자의 음성 파일 (MP3 등)
            @RequestParam("metadata") String metadataJson, // 메타데이터 (JSON 문자열)
            @AuthenticationPrincipal CustomUserDetails userDetails) throws JsonProcessingException {
        
        Long normalId = userDetails.getNormalUser().getId();

        // 서비스 계층 호출: 클라이언트로부터 받은 파일과 메타데이터를 서비스로 전달
        // sttMessage는 더 이상 클라이언트에서 직접 받지 않음 (AI 서버에서 처리)
        AiRcmdRes response = rcmdService.getAiRcmd(file, metadataJson, normalId);
        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.ok(response));
    }

}
