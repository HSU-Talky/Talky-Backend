package com.example.talky.adapter.ai;

import com.example.talky.adapter.ai.response.RecommendationResponse;
import com.example.talky.global.ai.dto.AiRcmdRes;
import com.example.talky.global.ai.dto.ToAiReq;
import com.example.talky.global.response.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface AiServerClient {
    // Object -> SuccessResponse<?>
    AiRcmdRes getAiRcmdSentences(ToAiReq req);

    // mp3 -> text
    String transcribe(MultipartFile audioFile);
}
