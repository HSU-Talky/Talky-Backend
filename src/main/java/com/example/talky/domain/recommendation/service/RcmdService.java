package com.example.talky.domain.recommendation.service;

import com.example.talky.domain.recommendation.web.dto.GetContextReq;
import com.example.talky.global.ai.dto.AiRcmdRes;
import com.example.talky.global.response.SuccessResponse;
import org.springframework.http.ResponseEntity;

public interface RcmdService {
    AiRcmdRes getAiRcmd(GetContextReq req, Long normalId);
}
