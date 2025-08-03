package com.example.talky.domain.recommendation.web.controller;

import com.example.talky.domain.recommendation.service.RcmdService;
import com.example.talky.domain.recommendation.web.dto.GetContextReq;
import com.example.talky.global.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/recommendations")
@RequiredArgsConstructor
public class RcmdController {

    private final RcmdService rcmdService;

    @PostMapping("/context")
    public ResponseEntity<SuccessResponse<?>> getAiSentence(@Validated @RequestBody GetContextReq req) {
        log.info(req.toString());
        // TODO
        // 서비스 계층 호출
        return rcmdService.getAiRcmd(req);
    }
}
