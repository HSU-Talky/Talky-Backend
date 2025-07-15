package com.example.talky.domain.recommendation.web.controller;

import com.example.talky.domain.recommendation.service.RcmdService;
import com.example.talky.domain.recommendation.web.dto.GetContextReq;
import com.example.talky.global.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/recommendations")
@RequiredArgsConstructor
public class RcmdController {

    private final RcmdService rcmdService;

    @PostMapping("/context")
    public ResponseEntity<SuccessResponse<?>> getAiSentence(GetContextReq req) {
        // JWT 기반 사용자 정보를 같이 넘겨주어야 하는지 확인 필요
        // TODO
        // 서비스 계층 호출
        return rcmdService.getAiRcmd(req);
    }
}
