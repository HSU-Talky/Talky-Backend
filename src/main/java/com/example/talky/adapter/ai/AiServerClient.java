package com.example.talky.adapter.ai;

import com.example.talky.global.ai.dto.ToAiReq;
import com.example.talky.global.response.SuccessResponse;
import org.springframework.http.ResponseEntity;

public interface AiServerClient {
    ResponseEntity<SuccessResponse<?>> callAiServer(ToAiReq req);
}
