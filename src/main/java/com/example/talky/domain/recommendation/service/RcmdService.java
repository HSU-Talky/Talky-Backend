package com.example.talky.domain.recommendation.service;

import com.example.talky.global.ai.dto.AiRcmdRes;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.multipart.MultipartFile;

public interface RcmdService {

    AiRcmdRes getAiRcmd(MultipartFile file, String metadataJson, Long normalId) throws JsonProcessingException;
}
